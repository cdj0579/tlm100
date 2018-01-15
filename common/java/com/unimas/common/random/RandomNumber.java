package com.unimas.common.random;

/**
 * 生成随机数
 * @author hxs
 *
 */
public class RandomNumber {

	/**
	 * 生成指定长度的随机数
	 * @param length   随机数长度，最小4位，最大9位
	 * @return
	 */
	public static String getRandomNumber(int length){
		if(length < 4){
			length = 4;
		} else if(length > 9){
			length = 9;
		}
		int num = 0;
		switch (length) {
			case 4: {
				num = random4();
				break;
			}
			case 5: {
				num = random5();
				break;
			}
			case 6: {
				num = random6();
				break;
			}
			case 7: {
				num = random7();
				break;
			}
			case 8: {
				num = random8();
				break;
			}
			case 9: {
				num = random9();
				break;
			}
			default: throw new RuntimeException("invalid length!");
		}
		return String.valueOf(num);
	}
	
	/**
	 * 4位随机数
	 * @return
	 */
	public static int random4(){
		return (int)((Math.random()*9+1)*1000);
	}
	
	/**
	 * 5位随机数
	 * @return
	 */
	public static int random5(){
		return (int)((Math.random()*9+1)*10000);
	}
	
	/**
	 * 6位随机数
	 * @return
	 */
	public static int random6(){
		return (int)((Math.random()*9+1)*100000);
	}
	
	/**
	 * 7位随机数
	 * @return
	 */
	public static int random7(){
		return (int)((Math.random()*9+1)*1000000);
	}
	
	/**
	 * 8位随机数
	 * @return
	 */
	public static int random8(){
		return (int)((Math.random()*9+1)*10000000);
	}
	
	/**
	 * 9位随机数
	 * @return
	 */
	public static int random9(){
		return (int)((Math.random()*9+1)*100000000);
	}
	
	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			System.out.println(RandomNumber.getRandomNumber(10)); 
		}
	}

}
