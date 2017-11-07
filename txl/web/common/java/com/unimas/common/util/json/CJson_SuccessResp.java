package com.unimas.common.util.json;

/**
 * 成功回应的json类
 * @author cj
 *
 */
public class CJson_SuccessResp extends CJson {
	public CJson_SuccessResp(){
		this.add("success", true);
	}
}
