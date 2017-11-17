package com.unimas.web.bean.datamodal;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * <p>描述: datatables组件所需要的数据模型</p>
 * @author hxs
 * @date 2016年12月29日 上午10:59:51
 */
public class DataTableDM extends AjaxDataModal {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据总条数,需要分页显示时才用
	 */
	protected int totalNum;
	/**
	 * 过虑后的条数
	 */
	protected int filteredNum = -1;
	
	/**
	 * @param draw       请求次数计数器，每次发送给服务器后又原封返回
	 * @param success    请求成功或失败的标记
	 */
	public DataTableDM(int draw,boolean success) {
		this(draw, success, 0, -1);
	}
	
	/**
	 * @param draw       请求次数计数器，每次发送给服务器后又原封返回
	 * @param success    请求成功或失败的标记
	 * @param totalNum   即没有过滤的记录数（数据库里总共记录数）
	 */
	public DataTableDM(int draw,boolean success, int totalNum) {
		this(draw, success, totalNum, -1);
	}

	/**
	 * @param draw       请求次数计数器，每次发送给服务器后又原封返回
	 * @param success    请求成功或失败的标记
	 * @param totalNum   即没有过滤的记录数（数据库里总共记录数）
	 * @param filteredNum   过滤后的记录数（如果有接收到前台的过滤条件，则返回的是过滤后的记录数）
	 */
	public DataTableDM(int draw,boolean success, int totalNum, int filteredNum) {
		super(success);
		this.put("draw",draw);
		this.setDatas(null);
	}
	
	public void putDatas(List<?> list){
		this.setDatas(list);
	}
	
	protected void setDatas(List<?> list){
		if(list == null){
			list = Lists.newArrayList();
		}
		this.put("data",list);
		int total = Math.max(this.totalNum,list.size());
		this.put("recordsTotal", total);
		this.put("recordsFiltered", filteredNum>=0?filteredNum:total);
	}

}
