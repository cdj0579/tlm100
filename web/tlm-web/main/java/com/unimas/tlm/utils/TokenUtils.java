package com.unimas.tlm.utils;

import java.util.Date;

import com.unimas.common.date.TimeUtils;
import com.unimas.common.md5.MD5Factory;

/**
 * @author hxs
 * 票据工具类
 *
 */
public class TokenUtils {
	
	/**
	 * 创建票据
	 * @param flag          标识
	 * @param timestamp     时间戳，票据的创建时间
	 * @return
	 */
	public static String createToken(String flag, long timestamp){
		return MD5Factory.hashCode(timestamp+"_"+flag);
	}
	
	/**
	 * 验证票据
	 * @param token         票据信息
	 * @param flag          标识
	 * @param timestamp     时间戳
	 * @param outtime       超时时间
	 * @return
	 */
	public static boolean valiate(String token, String flag, long timestamp, long outtime){
		if(token != null){
			if(!TimeUtils.timeout(new Date(timestamp), outtime)){ //判断票据是否超时
				if(token.equals(createToken(flag, timestamp))){ //验证票据
					return true;
				}
			}
		}
		return false;
	}

}
