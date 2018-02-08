package com.unimas.tlm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * <p>描述: 系统菜单管理</p>
 * @author hxs
 * @date 2016年12月29日 上午11:29:04
 */
public class MenuManage {
	
	private String basePath;
	
	public MenuManage(String basePath){
		this.basePath = basePath;
	}
	
	public List<Map<String, Object>> getUserMenus() throws Exception{
		List<Map<String, Object>> menus = new ArrayList<Map<String, Object>>();
		Subject subject = SecurityUtils.getSubject();
		if(subject.hasRole("teacher")){
			menus.add(getMenuItem(PageView.USER));
			menus.add(getMenuItem(PageView.GXPHB));
			menus.add(getDivider());
		}
		menus.add(getModalMenuItem("system/updatePwd", "修改密码", "updatePwd", "icon-calendar", "assets/system/js/updatePwd"));
		menus.add(getMenuItem(PageView.LOCK));
		menus.add(getMenuItem(PageView.LOGOUT));
		
		return menus;
	}
	
	public List<Map<String, Object>> getMenus() throws Exception{
		List<Map<String, Object>> menus = new ArrayList<Map<String, Object>>();
		
		//首页
		menus.add(getMenuItem(PageView.HOME));
		
		Subject subject = SecurityUtils.getSubject();
		if(subject.hasRole("teacher")){
			Map<String, Object> jagl = getMenuItem(PageView.JAGL);
			List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
			children.add(getMenuItem(PageView.WDJA));
			children.add(getMenuItem(PageView.JAMB));
			jagl.put("children", children);
			menus.add(jagl);
			
			Map<String, Object> zstx = getMenuItem(PageView.ZSTX);
			menus.add(zstx);
			
			Map<String, Object> zsgl = getMenuItem(PageView.ZSGL);
			List<Map<String, Object>> children2 = new ArrayList<Map<String, Object>>();
			zsgl.put("children", children2);
			menus.add(zsgl);
			Map<String, Object> xtgl = getMenuItem(PageView.XTGL);
			//children = new ArrayList<Map<String, Object>>();
			//children.add(getMenuItem(PageView.xxx));
			//xtgl.put("children", children);
			children2.add(xtgl);
			
			Map<String, Object> ztgl = getMenuItem(PageView.ZT_CONTENT);
			//children = new ArrayList<Map<String, Object>>();
			//children.add(getMenuItem(PageView.XXX));
			//ztgl.put("children", children);
			children2.add(ztgl);
			
			Map<String, Object> zsdgl = getMenuItem(PageView.ZSD_CONTENT);
			//children = new ArrayList<Map<String, Object>>();
			//children.add(getMenuItem(PageView.XXX));
			//zsdgl.put("children", children);
			children2.add(zsdgl);
			
		}
		
		if(subject.hasRole("admin")){
			Map<String, Object> zsgl = getMenuItem(PageView.ZSGL);
			List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
			zsgl.put("children", children);
			menus.add(zsgl);
			children.add(getMenuItem(PageView.ZSTX));
			children.add(getMenuItem(PageView.ZSD));
			children.add(getMenuItem(PageView.ZT));
			
			Map<String, Object> jfrwgl = getMenuItem(PageView.JFRWGL);
			children = new ArrayList<Map<String, Object>>();
			jfrwgl.put("children", children);
			menus.add(jfrwgl);
			
			children.add(getMenuItem(PageView.JFRW_FBRW));
			children.add(getMenuItem(PageView.JFRW_VIEW));
			children.add(getMenuItem(PageView.JFRW_SHRW));
			//children.add(getMenuItem(PageView.JFRW_SET));
			
			Map<String, Object> base = getMenuItem(PageView.JCXXGL);
			children = new ArrayList<Map<String, Object>>();
			base.put("children", children);
			menus.add(base);
			children.add(getMenuItem(PageView.CSTK));
			children.add(getMenuItem(PageView.MBXX));
			children.add(getMenuItem(PageView.XKDW));
			children.add(getMenuItem(PageView.ZJGL));
			children.add(getMenuItem(PageView.JFGZ));
			children.add(getMenuItem(PageView.CONFIG));
			
			Map<String, Object> yhgl = getMenuItem(PageView.YHGL);
			children = new ArrayList<Map<String, Object>>();
			yhgl.put("children", children);
			menus.add(yhgl);
			children.add(getMenuItem(PageView.JSGL));
			children.add(getMenuItem(PageView.JZXSGL));
		}
		
		return menus;
	}
	
	protected Map<String, Object> getDivider(){
		Map<String, Object> menu = new HashMap<String, Object>();
		menu.put("isDivider", true);
		return menu;
	}
	
	protected Map<String, Object> getModalMenuItem(String url, String text, String name, String iconCls, String requried){
		Map<String, Object> item = getMenuItem(url, text, name, iconCls, false);
		item.put("onModal", true);
		item.put("requried", requried);
		return item;
	}
	
	protected Map<String, Object> getMenuItem(String url, String text, String name, String iconCls){
		return getMenuItem(url, text, name, iconCls, false);
	}
	
	private Map<String, Object> getMenuItem(PageView view, boolean isAjax){
		return getMenuItem(view.url(), view.title(), view.name(), view.iconCls(), isAjax);
	}
	
	private Map<String, Object> getMenuItem(PageView view){
		return getMenuItem(view, false);
	}
	
	private String getPath(String url){
		if(url != null && !"".equals(url) && (!url.startsWith("/") || !url.startsWith("http://") || !url.startsWith("https://"))){
			url = basePath + url;
		}
		return url;
	}
	
	private Map<String, Object> getMenuItem(String url, String text, String name, String iconCls, boolean isAjax){
		Map<String, Object> menu = new HashMap<String, Object>();
		if(url != null){
			menu.put("url", getPath(url));
		}
		menu.put("text", text);
		menu.put("name", name);
		menu.put("iconCls", iconCls);
		menu.put("isAjax", isAjax);
		return menu;
	}
	
	public static enum PageView {
		/**
		 * 首页
		 */
		HOME("./", "首&nbsp;页", "fa fa-home", null, null),
		/**
		 * 教案管理
		 */
		JAGL(null, "教案管理", "icon-briefcase", "在此页面中对教案进行管理", Lists.newArrayList(HOME)),
		/**
		 * 创建教案
		 */
		WDJA("jagl", "我的教案", "fa fa-list", "在此页面中对我的教案进行管理", Lists.newArrayList(HOME, JAGL)),
		/**
		 * 编辑教案
		 */
		BJJA("jagl/edit", "编辑教案", "fa fa-edit", "在此页面中对教案进行编辑", Lists.newArrayList(HOME, JAGL, WDJA)),
		/**
		 * 我的模板
		 */
		JAMB("jagl/templetes", "我的模板", "icon-wallet", "在此页面中对我的教案模板进行管理", Lists.newArrayList(HOME, JAGL)),
		/**
		 * 编辑教案
		 */
		BJJAMB("jagl/templetes/edit", "编辑教案模板", "fa fa-edit", "在此页面中对教案模板进行编辑", Lists.newArrayList(HOME, JAGL, JAMB)),
		/**
		 * 习题管理
		 */
		XTGL("zs/xt", "习题管理", "icon-note", "在此页面中对习题进行管理", Lists.newArrayList(HOME)),
		/**
		 * 积分任务
		 */
		JFRWGL(null, "积分任务", "icon-bulb", null, Lists.newArrayList(HOME)),
		/**
		 * 积分规则配置
		 */
		JFGZ("jfgz", "积分规则", null, null, Lists.newArrayList(HOME)),
		/**
		 * 系统配置基础信息配置
		 */
		CONFIG("config", "系统配置", null, null, Lists.newArrayList(HOME)),
		/**
		 * 任务管理
		 */
		JFRW_VIEW("jfrw/view", "任务管理", "icon-bulb", null, Lists.newArrayList(HOME, JFRWGL)),
		/**
		 * 发布任务
		 */
		JFRW_FBRW("jfrw/fbrw", "发布任务", "icon-plus", null, Lists.newArrayList(HOME, JFRW_VIEW)),
		/**
		 * 审核任务
		 */
		JFRW_SHRW("jfrw/shrw", "审核任务", "icon-list", null, Lists.newArrayList(HOME, JFRWGL)),
		/**
		 * 积分规则
		 */
		JFRW_SET("jfrw/set", "积分规则", "icon-puzzle", null, Lists.newArrayList(HOME, JFRWGL)),
		/**
		 * 用户管理
		 */
		YHGL(null, "用户管理", "icon-user", null, Lists.newArrayList(HOME)),
		/**
		 * 教师管理
		 */
		JSGL("user/teacher", "教师管理", "", null, Lists.newArrayList(HOME, YHGL)),
		/**
		 * 家长学生管理
		 */
		JZXSGL("user/student", "家长学生管理", "", null, Lists.newArrayList(HOME, YHGL)),
		/**
		 * 知识管理
		 */
		ZSGL(null, "知识管理", "icon-diamond", null, Lists.newArrayList(HOME)),
		/**
		 * 知识体系
		 */
		ZSTX("zs/zstx", "知识体系", "fa fa-sitemap", null, Lists.newArrayList(HOME)),
		/**
		 * 知识管理
		 */
		ZSD("zs/zsd", "知识点", "icon-diamond", null, Lists.newArrayList(HOME, ZSGL)),
		/**
		 * 知识点内容
		 */
		ZSD_CONTENT("zs/zsd/content", "知识点内容", "icon-diamond",  "在此页面中对知识点内容进行管理", Lists.newArrayList(HOME, ZSGL)),
		/**
		 * 知识管理
		 */
		ZT("zs/zt", "专题", "icon-layers", null, Lists.newArrayList(HOME, ZSGL)),
		/**
		 * 专题内容
		 */
		ZT_CONTENT("zs/zt/content", "专题内容", "icon-layers", "在此页面中对专题内容进行管理", Lists.newArrayList(HOME, ZSGL)),
		/**
		 * 基础信息管理
		 */
		JCXXGL(null, "基础信息管理", "icon-settings", null, Lists.newArrayList(HOME)),
		/**
		 * 测试题库
		 */
		CSTK("base/cstk", "测试题库", "", null, Lists.newArrayList(HOME, JCXXGL)),
		/**
		 * 目标学校
		 */
		MBXX("base/mbxx", "目标学校", "", null, Lists.newArrayList(HOME, JCXXGL)),
		/**
		 * 学科档位
		 */
		XKDW("base/xkdw", "学科档位", "", null, Lists.newArrayList(HOME, JCXXGL)),
		/**
		 * 章节管理
		 */
		ZJGL("base/zj", "章节管理", "", null, Lists.newArrayList(HOME, JCXXGL)),
		/**
		 * 我的信息
		 */
		USER("user/info", "我的信息", "icon-user", null, Lists.newArrayList(HOME)),
		/**
		 * 贡献排行榜
		 */
		GXPHB("user/gxphb", "贡献排行榜", "icon-user", null, Lists.newArrayList(HOME)),
		/**
		 * 锁定屏幕
		 */
		LOCK("system/lock", "锁定屏幕", "icon-lock", null, null),
		/**
		 * 退出登录
		 */
		LOGOUT("logout", "退出登录", "icon-key", null, null),
		/**
		 * 404错误页面
		 */
		ERROR_404("error/404", "页面不存在", "", null, Lists.newArrayList(HOME)),
		/**
		 * 500错误页面
		 */
		ERROR_500("error/500", "系统异常", "", null, Lists.newArrayList(HOME));
		
		private String url;
		private String title;
		private String desc;
		private String iconCls;
		private ArrayList<PageView> navs;
		/**
		 * 页面视图
		 * @param url      页面URL，可以为空
		 * @param title    页面标题，不能为空
		 * @param desc     页面描述，可以带html
		 * @param navs     页面民航
		 */
		private PageView(String url, String title, String iconCls, String desc, ArrayList<PageView> navs){
			this.url = url;
			this.title = title;
			this.iconCls = iconCls;
			this.desc = desc;
			this.navs = navs;
		}
		public String url(){ return this.url; };
		public String title(){ return this.title; };
		public String desc(){ return this.desc; };
		public String iconCls(){ return this.iconCls; };
		public Map<String, Object> map(){
			Map<String, Object> map = Maps.newHashMap();
			map.put("url", url);
			map.put("title", title);
			map.put("iconCls", iconCls);
			map.put("desc", desc);
			return map;
		}
		public List<Map<String, Object>> navs(){ 
			if(this.navs == null){
				return null;
			}
			List<Map<String, Object>> list = Lists.newArrayList();
			for(PageView view : this.navs){
				list.add(view.map());
			}
			return list;
		};
	}

}
