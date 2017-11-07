package com.unimas.common.base;
 
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReflectBean {
	
	private final static Log log = LogFactory.getLog(ReflectBean.class);
	private Class beangetparam [] = null ;
	private int eleindex = 1 ;
	private boolean list_token = false ;	
	private Object bean_obj = null ;
	private StringBuffer beanstr = new StringBuffer(516);
	private StringBuffer liststr = new StringBuffer(1024);	
	private String [] fieldstr = null;
	
	public String parseListToXMLStr(List beanlist,int size){
		
		liststr.delete(0, liststr.length());
		list_token = true;
		Iterator itr = beanlist.iterator();
		while(itr.hasNext()){
			Object obj = itr.next();
			if( obj == null )continue;
			this.parseBeanToXMLStr(obj);
			liststr.append( beanstr.toString() );
		}
		list_token = false;
		this.addListsize( liststr,size);
		this.addListhead( liststr );
		this.addXMLhead( liststr );
		fieldstr = null;
	  return liststr.toString();
	}
	private void addListsize(StringBuffer buffer,int size){
		buffer.insert(0, this.changKeyvalue("total", size+""));
	}
	
	private void addListhead(StringBuffer buffer){
		buffer.insert(0, "<rows>");
		buffer.append("</rows>");
	}
	public String parseBeanToXMLStr(Object bean){		
		this.beanstr.delete(0, beanstr.length());
		this.eleindex = 1;	
		this.bean_obj = bean ;
	    this.beanToXMLbodyStr( bean );
		this.addElementhead();
		//判断是不是由列表调用
		if(!list_token){		  
		  this.addListsize( beanstr , 1 );
		  this.addListhead( beanstr );
		  this.addXMLhead( beanstr );
		 }	  
		return beanstr.toString();
	}
	private void beanToXMLbodyStr(Object bean){
	  Class beanclass = bean.getClass();
	  Field beanfield[] = beanclass.getDeclaredFields();
	  int times = beanfield.length;
	  if( fieldstr != null)
		    times = fieldstr.length;
	  for(int i = 0; i < times; i++){
		 try {
			 String methodname = "";
			 if(fieldstr != null){
				   methodname = this.getMethodname(fieldstr[i]);
			 }else{
				   methodname = this.getMethodname(beanfield[i].getName());
				   beanfield[i].setAccessible(true);
			 }
			
			Method getmethod = beanclass.getDeclaredMethod(methodname , beangetparam);
			if(getmethod == null ) continue;	
			getmethod.setAccessible(true);
			Object retvalue = getmethod.invoke(bean, new Object[0]);
			
			//log.debug("[Field: " + beanfield[i].getName()+", index: "+ eleindex+"]");
			this.addElement( retvalue );
		} catch (Exception e) {				
			//e.printStackTrace();
			log.error(e.getLocalizedMessage());
			continue;	
		}	
	  }
	 Class superclass = beanclass.getSuperclass();
	 if(superclass != null && !superclass.getName().equals(Object.class.getName())){
		try {					
			Object supobj = superclass.newInstance();
			this.copySupclass(supobj , this.bean_obj);
			
			this.beanToXMLbodyStr(supobj);
			
		} catch (InstantiationException e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
		}			
	 }	   
	   
	}
	private void copySupclass(Object supobj,Object subobj){
		Class supclass = supobj.getClass();
		Field beanfield[] = supclass.getDeclaredFields();	
		
		Class subclass = subobj.getClass();		
		for(int i = 0 ; i < beanfield.length;i++){
			 beanfield[i].setAccessible(true);
			try {
			  Method getmethod = subclass.getMethod(this.getMethodname(beanfield[i].getName()), beangetparam);			
			  if(getmethod == null ) continue;
			  getmethod.setAccessible(true);
			  Object retvalue = getmethod.invoke(subobj, new Object[0]);
			  beanfield[i].set(supobj, retvalue);
			} catch (Exception e) {	
				log.error(e.getLocalizedMessage());
				e.printStackTrace();				
			} 
		}
	}
	private void addElementhead(){
		beanstr.insert(0, "<row>");
		beanstr.append("</row>");
	}
	private void addElement( Object value ){	
		beanstr.append("<field" + eleindex +">");
		if(value != null && !value.equals("null")) 
			  beanstr.append("<![CDATA["+value+"]]>");
		beanstr.append("</field" + eleindex +">");
		eleindex ++ ;
	}
	private String changKeyvalue(String key,Object value){		
		return "<"+key+">"+value+"</"+key+">"	;	
	}
	private void addXMLhead(StringBuffer buffer){
		
		buffer.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");		
	}
    private String getMethodname(String field){    
    	field = "get"+field.substring(0, 1).toUpperCase() + field.substring(1);
       return field ;
    }
	public String[] getFieldstr() {
		return fieldstr;
	}
	public void setFieldstr(String[] fieldstr) {
		this.fieldstr = fieldstr;
	}
}
