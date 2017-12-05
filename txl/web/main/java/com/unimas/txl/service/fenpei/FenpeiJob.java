package com.unimas.txl.service.fenpei;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.unimas.common.date.TimeUtils;
import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.schedule.Job;
import com.unimas.schedule.SProcess;
import com.unimas.schedule.ScheduleManager;
import com.unimas.schedule.trigger.STrigger;
import com.unimas.schedule.trigger.STrigger.Type;
import com.unimas.txl.bean.LxrBean;
import com.unimas.txl.bean.SyzBean;
import com.unimas.txl.bean.fenpei.FenpeiBean;
import com.unimas.txl.bean.fenpei.FenpeiSyzBean;
import com.unimas.txl.service.LxrService;
import com.unimas.txl.service.SyzService;

public class FenpeiJob extends SProcess {
	
	private static final Logger logger = Logger.getLogger(FenpeiJob.class);
	
	private static final int BASE_VALUE = 60*60*24;
	
	private FpgzService service = new FpgzService();
	private LxrService lxrService = new LxrService();
	private SyzService syzService = new SyzService();
	private int jigouId;
	
	public FenpeiJob(int jigouId, int zhouqi)
			throws Exception {
		super(getRunningJobKey(jigouId), new STrigger("0,"+(zhouqi*BASE_VALUE), Type.INTERVAL));
		this.jigouId = jigouId;
	}
	
	@Override
	protected void run() {
		fenpei();
	}
	
	public static void startJob(int jigouId, int zhouqi) throws Exception {
		StartJob fenpeiJob = new StartJob(jigouId, zhouqi);
		String jobKey = fenpeiJob.getJobKey();
		ScheduleManager schedule = ScheduleManager.getInstance();
		if(!schedule.isJobClosed(jobKey)){
			schedule.stopJob(jobKey);
		}
		if(!schedule.isJobClosed(getRunningJobKey(jigouId))){
			schedule.stopJob(jobKey);
		}
		Job job = new Job(fenpeiJob);
		ScheduleManager.getInstance().startJob(jobKey, job);
	}
	
	public static void startGuanzhuMonitorJob(int jigouId) throws Exception {
		GuanzhuMonitorJob monitorJob = new GuanzhuMonitorJob(jigouId);
		String jobKey = monitorJob.getJobKey();
		ScheduleManager schedule = ScheduleManager.getInstance();
		if(schedule.isJobClosed(jobKey)){
			Job job = new Job(monitorJob);
			ScheduleManager.getInstance().startJob(jobKey, job);
		}
	}
	
	public static void startAll() throws Exception {
		Map<Integer, Integer> map = FpgzService.getAllZhouqi();
		if(map != null){
			for(int jigouId : map.keySet()){
				int zhouqi = map.get(jigouId);
				if(jigouId > 0){
					startJob(jigouId, zhouqi);
					startGuanzhuMonitorJob(jigouId);
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		/*int jigouId = 1;
		startJob(jigouId, 5);
		//new FenpeiJob(1, new STrigger("d,11:27:00", Type.DAY)).fenpei();
		
		Thread.sleep(1000*127);
		startJob(jigouId, 10);*/
		//startGuanzhuMonitorJob(1);
		//startAll();
		new FenpeiJob(1, 7).fenpei();
	}
	
	public void fenpei(){
		//1、释放已分配的联系人（已关注和已签约的联系人不释放）
		//2、加载所有分配规则
		//3、遍历规则并按规则进行分配。
		try {
			service.clearLxrFenpei(jigouId);
			List<FenpeiBean> list = service.query(jigouId);
			if(list != null){
				for(FenpeiBean bean : list){
					fenpei(bean);
				}
			}
		} catch (Exception e) {
			logger.error("分配任务执行失败！", e);
		}
	}
	
	private void fenpeiSyz(Connection conn, List<LxrBean> lxres, SyzBean b) throws Exception {
		if(lxres == null || b == null) return;
		int size = lxres.size();
		if(size > 0){
			int index = (int)(Math.random()*size);
			LxrBean lxrBean = lxres.get(index);
			logger.debug(b.getName() + " ----> "+lxrBean.getLianxiren()+"("+lxrBean.getId()+")");
			service.saveLxrFenpeiInfo(conn, jigouId, lxrBean.getId(), b.getId());
			lxres.remove(lxrBean);
		}
	}
	
	private void fenpei(FenpeiBean bean) throws Exception {
		//1、加载待分配联系人(未被分配、未被关注、未被签约且符合指定的条件（学校、年级、班级）)
		String lxrDqId = bean.getLxrDqId();
		String xuexiaoId = bean.getXuexiaoId();
		String nj = bean.getNj();
		String bj = bean.getBj();
		int lryId = bean.getLryId();
		List<LxrBean> lxres = Lists.newLinkedList(lxrService.queryNofp(jigouId, xuexiaoId, lxrDqId, nj, bj, lryId));
		
		//2、加载待分配使用者
		String dqId = bean.getDqId();
		List<SyzBean> syzes = null;
		if(StringUtils.isNotEmpty(dqId)){
			syzes = syzService.query(jigouId, dqId, null);
		} else {
			List<FenpeiSyzBean> syzList = service.getSyzList(bean.getId());
			List<Integer> syzIds = Lists.newArrayList();
			if(syzList != null){
				for(FenpeiSyzBean b : syzList){
					syzIds.add(b.getSyzId());
				}
			}
			syzes = syzService.query(jigouId, syzIds);
		}
		//3、分配（计算单量，够则按单量分配，不够重新计算单量）
		if(lxres != null && lxres.size() > 0 && syzes != null && syzes.size() > 0){
			Connection conn = null;
			try {
				conn = DBFactory.getConn();
				conn.setAutoCommit(false);
				
				logger.info("联系人："+lxres.size()+" -- 使用者："+syzes.size());
				int danliang = bean.getDanliang();
				if(lxres.size()/syzes.size() < danliang){ //不够分配，重新计算单量
					danliang = lxres.size()/syzes.size();
				}
				if(danliang > 0){
					for(SyzBean b : syzes){
						for(int i=0;i<danliang;i++){
							fenpeiSyz(conn, lxres, b);
						}
					}
					if(lxres.size() > 0){  //未分配完
						for(SyzBean b : syzes){
							fenpeiSyz(conn, lxres, b);
						}
					}
				} else {
					for(SyzBean b : syzes){
						fenpeiSyz(conn, lxres, b);
					}
				}
				
				conn.commit();
			} catch(Exception e) {
				if(conn != null){
					try{
						conn.rollback();
					} catch(Exception e1){}
				}
				throw e;
			} finally {
				DBFactory.close(conn, null, null);
			}
		}
	}
	
	private static String getStartJobKey(int jigouId){
		return "fenpei-start-"+jigouId;
	}
	
	private static String getRunningJobKey(int jigouId){
		return "fenpei-running-"+jigouId;
	}
	
	static class GuanzhuMonitorJob extends SProcess {
		
		private FpgzService service = new FpgzService();
		private int jigouId;

		public GuanzhuMonitorJob(int jigouId) throws Exception {
			super("guanzhu-monitor-"+jigouId, new STrigger("0 5 * * * ?", Type.CUSTOM));
			this.jigouId = jigouId;
		}

		@Override
		protected void run() {
			try {
				service.clearLxrGuanzhu(jigouId);
			} catch (Exception e) {
				logger.error("关注监控任务执行失败！", e);
			}
		}
		
	}
	
	static class StartJob extends SProcess {
		
		private int jigouId;
		private int zhouqi;

		public StartJob(int jigouId, int zhouqi) throws Exception {
			super(getStartJobKey(jigouId), new STrigger("d,00:30:00", Type.DAY));
			//super(getStartJobKey(jigouId), new STrigger("25 23 * * * ?", Type.CUSTOM));
			this.jigouId = jigouId;
			this.zhouqi = zhouqi;
		}

		@Override
		protected void run() {
			System.out.println("定时调度(周期["+zhouqi+"])："+ TimeUtils.getTime());
			ScheduleManager schedule = ScheduleManager.getInstance();
			try {
				schedule.stopJob(jobKey);
			} catch (Exception e) {
				logger.error("停止任务失败！", e);
			}
			try {
				FenpeiJob fenpeiJob = new FenpeiJob(jigouId, zhouqi);
				String jobKey = fenpeiJob.getJobKey();
				if(!schedule.isJobClosed(jobKey)){
					schedule.stopJob(jobKey);
				}
				Job job = new Job(fenpeiJob);
				ScheduleManager.getInstance().startJob(jobKey, job);
			} catch (Exception e) {
				logger.error("循环调度任务失败！", e);
			}
		}
		
	}

}
