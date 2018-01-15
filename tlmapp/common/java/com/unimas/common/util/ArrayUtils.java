package com.unimas.common.util;

public class ArrayUtils {
	/**
	 * 从数组arr中删除第itemDel个元素
	 * @param arr
	 * @param itemDel
	 * @return
	 */
	public static boolean deleteItem(Object[] arr,int itemDel){
	    if(null == arr || arr.length == 0 || itemDel <0 || itemDel >= arr.length)
	          return false;
		if(arr.length == 1){
			arr[0] = null;
			return true;
		}
		for (int i = itemDel;i<arr.length-1;++i){
			arr[i] = arr[i+1];
		}
		arr[arr.length-1] = null;
		return true;
	}
}
