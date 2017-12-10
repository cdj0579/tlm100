package com.unimas.web;

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
//			menus.add(getMenuItem(PageView.USER));
//			menus.add(getMenuItem(PageView.GXPHB));
		}
		menus.add(getDivider());
		/*menus.add(getModalMenuItem("system/updatePwd", "修改密码", "updatePwd", "icon-calendar", "assets/system/js/updatePwd"));
		menus.add(getMenuItem(PageView.LOCK));*/
		menus.add(getMenuItem(PageView.LOGOUT));
		
		return menus;
	}
	
	public List<Map<String, Object>> getMenus() throws Exception{
		List<Map<String, Object>> menus = new ArrayList<Map<String, Object>>();
		
		//首页
		//menus.add(getMenuItem(PageView.HOME));
		
		
		
		Subject subject = SecurityUtils.getSubject();
		
		menus.add(getMenuItem(PageView.LIANXIREN));
		
		if(subject.hasRole("admin")){
			menus.add(getMenuItem(PageView.LURUYUAN));
			
			menus.add(getMenuItem(PageView.SHIYONGZHE));
			
			menus.add(getMenuItem(PageView.FENPEIGUIZE));
			
			menus.add(getMenuItem(PageView.XUEXIAO));
			
			menus.add(getMenuItem(PageView.CONFIG));
		} else if(subject.hasRole("lry")){
			menus.add(getMenuItem(PageView.WDFX));
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
		HOME("", "首&nbsp;页", "fa fa-home", null, null),
		/**
		 * 录入员管理
		 */
		LURUYUAN("lrygl", "录入员", "fa fa-user-plus", null, Lists.newArrayList(HOME)),
		/**
		 * 使用者管理
		 */
		SHIYONGZHE("syzgl", "使用者", "fa fa-user", null, Lists.newArrayList(HOME)),
		/**
		 * 分配规则管理
		 */
		FENPEIGUIZE("fpgzgl", "分配规则", "fa fa-edit", null, Lists.newArrayList(HOME)),
		/**
		 
		 * 联系人管理
		 */
		LIANXIREN("lxrgl", "联系人", "fa fa-file-text-o", null, Lists.newArrayList(HOME)),
		/**
		 * 学校管理
		 */
		XUEXIAO("xxgl", "学校管理", "fa fa-building-o", null, Lists.newArrayList(HOME)),
		/**
		 * 系统配置
		 */
		CONFIG("config", "系统配置", "fa fa-cog", null, Lists.newArrayList(HOME)),
		/**
		 * 我的分享
		 */
		WDFX("wdfx", "我的分享", "fa fa-share", null, Lists.newArrayList(HOME)),
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
