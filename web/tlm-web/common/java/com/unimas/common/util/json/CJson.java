package com.unimas.common.util.json;

/**
 * 一个轻量级的Json类，只支持对String,String[],int,int[]类型操作
 * 
 * @author cj
 * 
 * 使用：
 * CJson json = new CJson();
 * json.add("key1","value1");
 * json.add("key2",{"v1","v2"});
 * CJson json2 = new CJson();
 * json2.add("key11","value11");
 * json.add("jkey",json2);
 * json.get();
 * 返回如下
 * {
 *     "key1":"value1",
 *     "key2":["v1","v2"],
 *     "jkey":{
 *         "key11":"value11"
 *     }
 * }
 * 
 * 数组形式：
 * CJson json = new CJson(true);
 * json.add("v1");
 * json.add("v2");
 * json.add("v3");
 * json.get();//返回["v1","v2","v3"]
 * 
 * CJson json2 = new CJson(true);
 * json2.add(json);
 * json2.add(json);
 * json2.get();//返回[["v1","v2","v3"],["v1","v2","v3"]]
 * 
 * 两者结合
	CJson json = new CJson();
	CJson array = new CJson(true);
	array.add("v1");
	array.add("v2");
	array.add("v3");
	json.add("key",array);
	json.get();//返回 {"key":["v1","v2","v3"]}
 */
public class CJson{
	String json ;
	private String START;
	private String END;
	private boolean array = false;
	boolean firstAdd = true;
	
	public CJson(){
		START="{";
		END="}";
		json = START;
	}
	
	public CJson(boolean isArray){
		if(isArray){
			array = true;
			START="[";
			END="]";
		}else {
			START="{";
			END="}";
		}
		json = START;
	}
	
	public void add(String value){
		//以后需要加这个判断
		assert(array== true);
		//if(!array)throw new CJsonOperationIllegalException();
		if(!firstAdd){
			json+= ",";
		}
		json+= "\""+value+"\"";
		firstAdd = false;
	}
	
	public void add(int value){
		//以后需要加这个判断
		assert(array== true);
		//if(!array)throw new CJsonOperationIllegalException();
		if(!firstAdd){
			json+= ",";
		}
		json+= value;
		firstAdd = false;
	}
	
	public void add(String[] values){
		if(!firstAdd){
			json+= ",";
		}
		String arrStr = _getArrStr(values);
		json+= arrStr;
		firstAdd = false;
	}
	
	public void add(int[] values){
		if(!firstAdd){
			json+= ",";
		}
		String arrStr = _getArrStr(values);
		json+= arrStr;
		firstAdd = false;
	}
	
	public void add(CJson cjson){
		if(!firstAdd){
			json+= ",";
		}
		json+= cjson.get();
		firstAdd = false;
	}
	
	private String _getArrStr(String[] values){
		String arrStr = "[";
		for(int i= 0 ;i< values.length;i++){
			if(i!=0)arrStr+= ",";
			arrStr+= "\""+values[i]+"\"";
		}
		arrStr+= "]";
		return arrStr;
	}
	
	private String _getArrStr(int[] values){
		String arrStr = "[";
		for(int i= 0 ;i< values.length;i++){
			if(i!=0)arrStr+= ",";
			arrStr+= values[i];
		}
		arrStr+= "]";
		return arrStr;
	}
	
	public void add(String key,String value){
		if(!firstAdd){
			json+= ",";
		}
		json+= "\""+key+"\":\""+value+"\"";
		firstAdd = false;
	}
	
	public void add(String key,Object value){
		if(!firstAdd){
			json+= ",";
		}
		json+= "\""+key+"\":"+value.toString()+"";
		firstAdd = false;
	}
	
	public void add(String key,int value){
		if(!firstAdd){
			json+= ",";
		}
		json+= "\""+key+"\":"+value;
		firstAdd = false;
	}
	
	public void add(String key,String[] values){
		if(!firstAdd){
			json+= ",";
		}
		String arrStr = _getArrStr(values);
		json+= "\""+key+"\":"+arrStr;
	}
	
	public void add(String key,int[] values){
		if(!firstAdd){
			json+= ",";
		}
		String arrStr = _getArrStr(values);
		json+= "\""+key+"\":"+arrStr;
	}
	
	public void add(String key,CJson cjson){
		if(!firstAdd){
			json+= ",";
		}
		json+= "\""+key+"\":"+cjson.get();
		firstAdd = false;
	}
	
	public String get(){
		json+= END;
		return json;
	}
	
	public static void main(String[] args) {
		CJson json = new CJson();
		CJson array = new CJson(true);
		array.add(1);
		array.add(2);
		array.add(3);
		json.add("key",array);
		json.add("key2",123);
		System.out.println(json.get());
		
	}
}
