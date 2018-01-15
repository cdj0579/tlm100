package com.unimas.tlm.service.jfrw.aspect.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface JfPointcut {
	
	public enum JfRule {
		
		/**
		 * 贡献知识点内容
		 */
		DEVOTE_ZSD("devote-zsd"),
		/**
		 * 贡献专题内容
		 */
		DEVOTE_ZT("devote-zt"),
		/**
		 * 贡献习题
		 */
		DEVOTE_XT("devote-xt"),
		/**
		 * 引用知识点内容
		 */
		QUOTE_ZSD("quote-zsd"),
		/**
		 * 引用专题内容
		 */
		QUOTE_ZT("quote-zt"),
		/**
		 * 引用习题
		 */
		QUOTE_XT("quote-xt");
		
		private String value;
		private JfRule(String value){
			this.value = value;
		}
		public String value(){
			return this.value;
		}
	}
	
	JfRule type();
	
}
