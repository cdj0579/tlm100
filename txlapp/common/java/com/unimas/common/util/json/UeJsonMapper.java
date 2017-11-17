package com.unimas.common.util.json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;

public class UeJsonMapper {
	
	private ObjectMapper mapper;
	
	public UeJsonMapper(){
		mapper = new ObjectMapper();
		this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
			.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

	}
	
	public UeJsonMapper configure(Feature f, boolean state){
		mapper.configure(f, state);
		return this;
	}
	
	/**
	 * 将对象转为 json格式数据
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public String beanToJson(Object obj) throws IOException{
		return mapper.writeValueAsString(obj);
	}
	
	/**
	 * 将对象转为json格式数据 并输出到输出流中
	 * @param out
	 * @param obj
	 * @throws IOException
	 */
	public void beanToJsonInStream(OutputStream out,Object obj) throws IOException{
		mapper.writeValue(out,obj);
	}

	/**
	 * 将json数据转为指定对象
	 * @param json
	 * @param clazz	执行对象,可以是数组对象 
	 * @return
	 * @throws IOException
	 */
	public <T>T JsonToBean(String json,Class<T> clazz) throws IOException{
		return mapper.readValue(json, clazz);
	}
	
	/**
	 * 将文件中的json数据转为指定的对象 
	 * @param file		文件
	 * @param clazz		指定对象，可以是数组对象
	 * @return
	 * @throws IOException
	 */
	public <T>T JsonToBean(File file,Class<T> clazz) throws IOException{
		return mapper.readValue(file, clazz);
	}
	
	/**
	 * 将流中的json数据转为指定的对象	
	 * @param input		输入流
	 * @param clazz		指定对象，可以使数组对象
	 * @return
	 * @throws IOException
	 */
	public <T>T JsonToBean(InputStream input,Class<T> clazz) throws IOException{
		return mapper.readValue(input, clazz);
	}
	
	
	/**
	 * 将map对象转为json数据 
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public String mapToJson(Map<?, ?> map) throws IOException{
		return beanToJson(map);
	}
	
	/**
	 * 将map对象转为json数据到输出流中 
	 * @param out
	 * @param map
	 * @throws IOException
	 */
	public void mapToJsonInStream(OutputStream out,Map<?, ?> map) throws IOException{
		beanToJsonInStream(out,map);
	}
	
	/**
	 * 将list数据转为json数据 
	 * @param list
	 * @return
	 * @throws IOException
	 */
	public String listToJson(List<?> list) throws IOException{
		return beanToJson(list);
	}
	
	/**
	 * 将list的数据转为json数据到输出流中 
	 * @param out
	 * @param list
	 * @throws IOException
	 */
	public void listToJsonInStream(OutputStream out,List<?> list) throws IOException{
		beanToJsonInStream(out,list);
	}
	
	/**
	 * 将数组对象转为json数据 
	 * @param o
	 * @return
	 * @throws IOException
	 */
	public String arrayToJson(Object... o) throws IOException{
		return beanToJson(o);
	}
	
	/**
	 * 将数组对象转为 json数据到输出流中  
	 * @param out
	 * @param o
	 * @throws IOException
	 */
	public void arrayToJsonInStream(OutputStream out,Object... o) throws IOException{
		beanToJsonInStream(out,o);
	}
	
	/**
	 * 将json的数据转为map中 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public Map<?, ?> JsonToMap(String json) throws IOException{
		return JsonToBean(json, HashMap.class);
	}
	
	/**
	 * 将json数据转为list数据
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public List<?> JsonToList(String json) throws IOException{
		return JsonToBean(json, ArrayList.class);
	}
	
	/**
	 * 将文件的json数据转为 map数据 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public Map<?, ?> JsonToMap(File json) throws IOException{
		return JsonToBean(json, HashMap.class);
	}
	
	/**
	 * 将文件的 json数据转为list数据 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public List<?> JsonToList(File json) throws IOException{
		return JsonToBean(json, ArrayList.class);
	}
	
	/**
	 * 将输入流的json数据转为map数据 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public Map<?, ?> JsonToMap(InputStream input) throws IOException{
		return JsonToBean(input, HashMap.class);
	}
	
	/**
	 * 将输入流中的json数据转为list的数据 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public List<?> JsonToList(InputStream input) throws IOException{
		return JsonToBean(input, ArrayList.class);
	}

}