package com.unimas.tlm.service.hyd.aspect.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface HydPointcut {
	
	public enum HydRule {
		
		/**
		 * 登录
		 */
		LOGIN("login"),
		/**
		 * 领取任务
		 */
		LQRW("lqrw"),
		/**
		 * 完成任务
		 */
		WCRW("wcrw"),
		/**
		 * 添加知识点
		 */
		ADD_ZSD("add-zsd"),
		/**
		 * 编辑知识点
		 */
		EDIT_ZSD("edit-zsd"),
		/**
		 * 添加知识点内容
		 */
		ADD_ZSD_CONTENT("add-zsd-content"),
		/**
		 * 编辑知识点内容
		 */
		EDIT_ZSD_CONTENT("edit-zsd-content"),
		/**
		 * 添加专题内容
		 */
		ADD_ZT_CONTENT("add-zt-content"),
		/**
		 * 编辑专题内容
		 */
		EDIT_ZT_CONTENT("edit-zt-content"),
		/**
		 * 添加习题
		 */
		ADD_XT("add-xt"),
		/**
		 * 编辑习题
		 */
		EDIT_XT("edit-xt"),
		/**
		 * 添加教案
		 */
		ADD_JA("add-ja"),
		/**
		 * 编辑教案
		 */
		EDIT_JA("edit-ja"),
		/**
		 * 添加教案模板
		 */
		ADD_JAMB("add-jamb"),
		/**
		 * 编辑教案模板
		 */
		EDIT_JAMB("edit-jamb");
		
		private String value;
		private HydRule(String value){
			this.value = value;
		}
		public String value(){
			return this.value;
		}

	}
	
	HydRule type();
	
}
