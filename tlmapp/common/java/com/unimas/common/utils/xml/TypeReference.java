package com.unimas.common.utils.xml;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Map;

public abstract class TypeReference<T> {
	
	/**
	 * Map类型
	 */
	public final static TypeReference<Map<String,?>> Map_TypeReference = new TypeReference<Map<String,?>>(){};
	
	private Type tType;
	
	public TypeReference(){
		this.tType = (Type) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public Type getEntityType(){
		return tType;
	}
	
	
	/**
	 * 获取参数化类型上的泛型
	 * @param type
	 * @return
	 */
	public static Type[] getParameterizedType(Type type){
		if(isParameterizedType(type)){
			return ((ParameterizedType)type).getActualTypeArguments();
		} else {
			return null;
		}
	}
	
	/**
	 * 是否参数化类型
	 * @param type
	 * @return
	 */
	public static boolean isParameterizedType(Type type){
		return ParameterizedType.class.isAssignableFrom(type.getClass());
	}
	
	/**
	 * 是否通配符类型
	 * @param type
	 * @return
	 */
	public static boolean isWildcardType(Type type){
		return WildcardType.class.isAssignableFrom(type.getClass());
	}
	
	/**
	 * 获取参数化类型上的泛型
	 * @param type
	 * @return
	 */
	public static Type getGenericArrayType(Type type){
		if(isGenericArrayType(type)){
			return ((GenericArrayType)type).getGenericComponentType();
		} else {
			return null;
		}
	}
	
	/**
	 * 是否数组类型
	 * @param type
	 * @return
	 */
	public static boolean isGenericArrayType(Type type){
		return GenericArrayType.class.isAssignableFrom(type.getClass());
	}
	
	/**
	 * 变量类型
	 * @param type
	 * @return
	 */
	public static boolean isTypeVariable(Type type){
		return TypeVariable.class.isAssignableFrom(type.getClass());
	}
	
	/**
	 * 将Type类型转换为Class类型
	 * @param type
	 * @return
	 */
	public static Class<?> changeClass(Type type) {
		if(isParameterizedType(type)){
			ParameterizedType t = (ParameterizedType)type;
			return (Class<?>)t.getRawType();
		} else {
			return (Class<?>)type;
		}
	}

}
