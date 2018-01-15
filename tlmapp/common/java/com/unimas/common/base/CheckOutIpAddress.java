/*
 * 标　　题：信息安全传输平台
 * 说　　明：
 * 公　　司：杭州合众信息工程有限公司
 * 项目名称：Newdrs
 * 包 名 称：com.tiansong.infosafetranflag.safe.impower.bean
 * 创建日期：2006-1-19 
 * 版　　本：V1.0
 */

package com.unimas.common.base;

import java.text.DecimalFormat;

/**
 * 中文类名：检验IP地址类 开发人员：王雷红 创建时间：2006-1-19 19:59:48 功能说明：检验单IP是否合法！
 * <p>
 * <a href="CheckOutIpAddress.java.html"><i>察看源代码</i></a>
 * </p>
 */
public class CheckOutIpAddress {

	/**
	 * 注释：验证单个IP地址是否合法！
	 * 
	 * @param ipAddress
	 * @return
	 * @throws Exception
	 */
	public static boolean checkoutIpAddress(String ipAddress) throws Exception {
		String[] ip = ipAddress.split("\\.");// "."为特殊符号，不能直接使用split(".")来分隔字符串，必须用split("\\.")
		// 判断输入的IP地址是否为4位
		if (ip.length != 4) {
			throw new Exception("输入的IP地址长度有误！");
		}
		String ipAddressOne = ip[0];
		String ipAddressTwo = ip[1];
		String ipAddressThree = ip[2];
		String ipAddressFour = ip[3];
		// 判断是否为数字
		if (!isNumber(ipAddressOne) || !isNumber(ipAddressTwo)
				|| !isNumber(ipAddressThree) || !isNumber(ipAddressFour)) {
			throw new Exception("输入的IP地址不是数字！");
		}
		// 判断IP地址是否规范
		try {
			ipCriterion(ipAddressOne, ipAddressTwo, ipAddressThree,
					ipAddressFour);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

	/**
	 * 注释：判断前者ip（段）是否包含后者ip地址（段） <br>
	 * 包含该ip（段），返回true <br>
	 * 不包含该ip（段），返回false
	 * 
	 * @param ipaddress
	 * @return
	 * @throws Exception
	 */
	public static boolean includeIp(String dbIpaddress, String ipaddress)
			throws Exception {
		String ipaddressOne = null;
		String ipaddressTwo = null;
		String dbIpaddressOne = null;
		String dbIpaddressTwo = null;
		String[] ip = null;
		ip = ipaddress.split("-");
		if (ip.length == 2) {
			ipaddressOne = ip[0];
			ipaddressTwo = ip[1];
		} else {
			ipaddressOne = ipaddress;
		}
		ip = dbIpaddress.split("-");

		// 拆分IP地址段
		if (ip.length == 2) {
			dbIpaddressOne = ip[0];
			dbIpaddressTwo = ip[1];
		} else {
			dbIpaddressOne = dbIpaddress;
		}

		// 判断数据库保存的ip是否包含用户输入的ip
		if (ipaddressOne != null && dbIpaddressOne != null) {
			if (ipaddressTwo == null && dbIpaddressTwo == null) {
				int i = compareTo(ipaddressOne, dbIpaddressOne);
				if (i != 0) {
					return false;
				} else {
					return true;
				}
			} else if (ipaddressTwo != null && dbIpaddressTwo == null) {
				int i = compareTo(ipaddressOne, dbIpaddressOne);
				int j = compareTo(ipaddressTwo, dbIpaddressOne);
				if (i > 0 || j < 0) {
					return false;
				} else {
					return true;
				}
			} else if (ipaddressTwo == null && dbIpaddressTwo != null) {
				int i = compareTo(ipaddressOne, dbIpaddressOne);
				int j = compareTo(ipaddressOne, dbIpaddressTwo);
				if (i < 0 || j > 0) {
					return false;
				} else {
					return true;
				}
			} else {
				int i = compareTo(ipaddressOne, dbIpaddressTwo);
				int j = compareTo(ipaddressTwo, dbIpaddressOne);
				if (i > 0 || j < 0) {
					return false;
				} else {
					return true;
				}
			}
		} else {
			throw new Exception("待检测的IP地址错误！");
		}
	}

	/**
	 * 注释：除去IP地址中多余的零！
	 * 
	 * @param ipaddress
	 * @return
	 * @throws Exception
	 */
	public static String removeZero(String ipaddress) throws Exception {
		int one = 0;
		int two = 0;
		int three = 0;
		int four = 0;
		String[] ip = null;
		try {
			if (ipaddress != null) {
				ip = ipaddress.split("\\.");
				if (ip.length == 4) {
					one = Integer.parseInt(ip[0]);
					two = Integer.parseInt(ip[1]);
					three = Integer.parseInt(ip[2]);
					four = Integer.parseInt(ip[3]);
					return one + "." + two + "." + three + "." + four;
				} else {
					throw new Exception("IP地址无效！");
				}
			} else {
				throw new Exception("Ip地址为空！");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 注释：判断前者Ip是否大于后者ip！ <br>
	 * 大于，返回1 <br>
	 * 等于，返回0 <br>
	 * 小于，返回-1
	 * 
	 * @param ipaddressOne
	 * @param ipaddressTwo
	 * @return
	 * @throws Exception
	 */
	public static int compareTo(String ipaddressOne, String ipaddressTwo)
			throws Exception {
		int oneIpOne = 0;
		int oneIpTwo = 0;
		int oneIpThree = 0;
		int oneIpFour = 0;
		int twoIpOne = 0;
		int twoIpTwo = 0;
		int twoIpThree = 0;
		int twoIpFour = 0;
		String[] ip = null;
		if (ipaddressOne != null && ipaddressTwo != null) {
			ip = ipaddressOne.split("\\.");
			if (ip.length == 4) {
				oneIpOne = Integer.parseInt(ip[0]);
				oneIpTwo = Integer.parseInt(ip[1]);
				oneIpThree = Integer.parseInt(ip[2]);
				oneIpFour = Integer.parseInt(ip[3]);
			} else {
				throw new Exception("第一个的IP地址错误！");
			}
			ip = ipaddressTwo.split("\\.");
			if (ip.length == 4) {
				twoIpOne = Integer.parseInt(ip[0]);
				twoIpTwo = Integer.parseInt(ip[1]);
				twoIpThree = Integer.parseInt(ip[2]);
				twoIpFour = Integer.parseInt(ip[3]);
			} else {
				throw new Exception("第二个的IP地址出错！");
			}

			if (oneIpOne < twoIpOne
					|| (oneIpOne == twoIpOne && oneIpTwo < twoIpTwo)
					|| (oneIpOne == twoIpOne && oneIpTwo == twoIpTwo && oneIpThree < twoIpThree)
					|| (oneIpOne == twoIpOne && oneIpTwo == twoIpTwo
							&& oneIpThree == twoIpThree && oneIpFour < twoIpFour)) {
				// 第一个ip小于第二个ip
				return -1;
			} else if (oneIpOne == twoIpOne && oneIpTwo == twoIpTwo
					&& oneIpThree == twoIpThree && oneIpFour == twoIpFour) {
				// 第一个ip等于第二个ip
				return 0;
			} else {
				// 第一个ip大于第二个ip
				return 1;
			}
		} else {
			throw new Exception("IP地址为空！");
		}
	}

	/**
	 * 注释：验证IP地址(段)是否合法！
	 * 
	 * @param ipaddresses
	 * @return
	 * @throws Exception
	 */
	public static boolean checkoutIpAddressSegment(String ipaddresses)
			throws Exception {
		String firstIp = null;
		String secondIp = null;
		// 检查IP地址是否合法，IP地址段 后IP > 前IP
		String[] ipSegment = ipaddresses.split("-");
		if (ipSegment.length != 2) {
			firstIp = ipaddresses;
			secondIp = null;
		} else {
			firstIp = ipSegment[0];
			secondIp = ipSegment[1];
		}

		try {
			checkoutIpAddress(firstIp);
			if (secondIp != null) {
				checkoutIpAddress(secondIp);
				int num = compareTo(firstIp, secondIp);
				if (num > 0) {
					throw new Exception("起始IP地址大于结束IP地址！");
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

	/**
	 * 注释：判断IP地址是否规范！
	 * 
	 * @param ipAddressOne
	 * @param ipAddressTwo
	 * @param ipAddressThree
	 * @param ipAddressFour
	 * @return
	 */
	private static boolean ipCriterion(String ipAddressOne,
			String ipAddressTwo, String ipAddressThree, String ipAddressFour)
			throws Exception {
		int one = Integer.parseInt(ipAddressOne);
		int two = Integer.parseInt(ipAddressTwo);
		int three = Integer.parseInt(ipAddressThree);
		int four = Integer.parseInt(ipAddressFour);

		// IP地址不能大于0或小于255
		if (one < 0 || one > 255 || two < 0 || two > 255 || three < 0
				|| three > 255 || four < 0 || four > 255) {
			throw new Exception("输入的IP地址数值错误！");
		}
		// IP地址规范
		if (one == 127) {
			throw new Exception(
					"以127起头的IP地址无效，因为它们保留用作环回地址。请在1～223之间选择选择其他有效数字！");
		} else if (one == 0 || (one > 223)) {
			throw new Exception("以" + one + "起头的IP地址无效。请在1～223之间选择其他有效数字！");
		}

		// if (one == 1 && two == 1 && three == 1 && four == 1) {
		// throw new Exception("不能将网络号和主机号的各位均置1。该地址会被解释为网内广播而不是一个主机号！");
		// }
		if (one == 1 && two == 0 && three == 0 && four == 0) {
			throw new Exception("不能将主机号的各位均置0。该地址会被解释为\"就是本网络\"！");
		}
		return true;
	}

	/**
	 * 注释：判断IP地址是否为数字！
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumber(String str) {
		if (str == null || str.length() <= 0) {
			return false;
		}
		int num = str.length();
		for (int i = 0; i < num; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 注释：获取两个IP地址间的差距！ <br>
	 * 计算时，第一个IP地址必须大于等于第二个IP地址； <br>
	 * 例如：192.168.0.2 与 192.168.0.1 的差距为1； 192.168.1.1 与 192.168.0.1 的差距为256；
	 * 
	 * @param bigIp
	 * @param smallIp
	 * @return
	 * @throws Exception
	 */
	public static int subIpAddress(String bigIp, String smallIp)
			throws Exception {
		if (bigIp == null || bigIp.length() <= 0 || smallIp == null
				|| smallIp.length() <= 0) {
			throw new Exception("IP地址为空！");
		}
		// 验证IP地址
		checkoutIpAddress(bigIp);
		checkoutIpAddress(smallIp);
		int n = compareTo(bigIp, smallIp);
		if (n < 0) {
			throw new Exception("起始IP大于结束IP！");
		}

		// 计算差距
		String[] str = bigIp.split("\\.");
		int o_0 = Integer.parseInt(str[0]);
		int o_1 = Integer.parseInt(str[1]);
		int o_2 = Integer.parseInt(str[2]);
		int o_3 = Integer.parseInt(str[3]);
		str = smallIp.split("\\.");
		int t_0 = Integer.parseInt(str[0]);
		int t_1 = Integer.parseInt(str[1]);
		int t_2 = Integer.parseInt(str[2]);
		int t_3 = Integer.parseInt(str[3]);

		int result = 0;

		result = (((o_0 - t_0) * 256 + o_1 - t_1) * 256 + o_2 - t_2) * 256
				+ o_3 - t_3;
		return result;
	}

	/**
	 * 注释：获取两个IP地址间的差距 +1！ <br>
	 * 计算时，第一个IP地址必须大于等于第二个IP地址； <br>
	 * 例如：192.168.0.2 与 192.168.0.1 的差距为2； 192.168.1.1 与 192.168.0.1 的差距为256；
	 * 
	 * @param bigIp
	 * @param smallIp
	 * @return
	 * @throws Exception
	 */
	public static int subIpAddressAddOne(String bigIp, String smallIp)
			throws Exception {
		return subIpAddress(bigIp, smallIp) + 1;
	}

	/**
	 * 注释：根据IP地址，获取增加数字的IP地址！ <br>
	 * 例如：192.168.0.1 增加 1 结果为 192.168.0.2 192.168.0.255 增加1 结果为 192.168.1.0
	 * 
	 * @param ip
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public static String addIpAddress(String ip, int num) throws Exception {
		if (ip == null || ip.length() <= 0) {
			throw new Exception("IP地址为空！");
		}
		checkoutIpAddress(ip);
		if (num == 0) {
			return ip;
		}

		String new_ip = "";
		String[] str = ip.split("\\.");
		int ip_0 = Integer.parseInt(str[0]);
		int ip_1 = Integer.parseInt(str[1]);
		int ip_2 = Integer.parseInt(str[2]);
		int ip_3 = Integer.parseInt(str[3]);
		int up = 0;

		int up_3 = num % 256;
		int up_2 = num / 256 % 256;
		int up_1 = num / 256 / 256 % 256;
		int up_0 = num / 256 / 256 / 256;

		ip_3 = ip_3 + up_3;
		if (ip_3 > 0) {
			up = ip_3 / 256;
			ip_3 = ip_3 % 256;
		} else if (ip_3 < 0) {
			up = -1;
			ip_3 = ip_3 + 256;
		}

		ip_2 = ip_2 + up_2 + up;
		if (ip_2 > 0) {
			up = ip_2 / 256;
			ip_2 = ip_2 % 256;
		} else if (ip_2 < 0) {
			up = -1;
			ip_2 = ip_2 + 256;
		}

		ip_1 = ip_1 + up_1 + up;
		if (ip_1 > 0) {
			up = ip_1 / 256;
			ip_1 = ip_1 % 256;
		} else if (ip_1 < 0) {
			up = -1;
			ip_1 = ip_1 + 256;
		}

		ip_0 = ip_0 + up_0 + up;
		if (ip_0 > 0) {
			up = ip_0 / 256;
			ip_0 = ip_0 % 256;
		} else if (ip_0 < 0) {
			up = -1;
			ip_0 = ip_0 + 256;
		}

		if (up != 0) {
			throw new Exception("增加的数值太大（太小），超出IP地址范围！");
		}
		new_ip = ip_0 + "." + ip_1 + "." + ip_2 + "." + ip_3;
		try {
			checkoutIpAddress(new_ip);
		} catch (Exception e) {
			throw new Exception("增加的数值太大（太小），超出IP地址范围！");
		}

		return new_ip;
	}

	/**
	 * 注释：获取起始IP间的所有IP！
	 * 
	 * @param startIp
	 *            开始IP
	 * @param endIp
	 *            结束IP
	 * @return
	 * @throws Exception
	 */
	public static String getAllIpByTwoIp(String startIp, String endIp,
			int amount) throws Exception {
		String result = "";
		if ((startIp != null && !startIp.equals(""))
				&& (endIp != null && !endIp.equals(""))) {
			result = "'" + startIp + "'";
			for (int i = 1; i < amount; i++) {
				result += ",'" + addIpAddress(startIp, i) + "'";
			}
		}
		return result;
	}

	/**
	 * 注释：验证子网掩码！
	 * 
	 * @param subnetCode
	 * @return 无效，返回false 有效, 返回true
	 * @throws Exception
	 */
	public static boolean checkOutSubnetCode(String subnetCode)
			throws Exception {

		boolean active = true;// 初始为真
		String[] ip = null;

		try {
			if (subnetCode == null || subnetCode.length() <= 0) {
				throw new Exception("子网掩码为空！");
			}

			ip = subnetCode.split("\\.");
			if (ip.length != 4) {
				throw new Exception("子网掩码无效！");
			}
			if (Integer.parseInt(ip[0]) == 0) {
				throw new Exception("子网掩码为无效的地址！");
			}

			for (int i = 0; i < 4; i++) {
				int num = Integer.parseInt(ip[i]);
				// 数值为0
				if (num == 0) {
					if (i == 3) {
						return true;
					}

					active = false;
					continue;
				}

				// 数值为255
				if (num == 255) {
					if (active) {
						if (i == 3) {
							return true;
						}
						continue;
					}
					throw new Exception("子网掩码为无效的地址！");
				}

				// 数值为前1后0
				if (num == 254 || num == 252 || num == 248 || num == 240
						|| num == 224 || num == 192 || num == 128) {
					if (active) {
						if (i == 3) {
							return true;
						}
						active = false;
						continue;
					}
					throw new Exception("子网掩码为无效的地址！");

				} else {
					throw new Exception("子网掩码为无效的地址！");
				}
			}

			if (active) {
				return true;
			} else {
				throw new Exception("子网掩码为无效的地址！");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 注释：将IP地址转换为数值！ 例如：192.168.0.1 转换为 192168000001； 10.118.1.1 转换为
	 * 10118001001
	 * 
	 * @param ipAddress
	 * @return long
	 * @throws Exception
	 */
	public static long toNumber(String ipAddress) throws Exception {
		if (ipAddress == null || ipAddress.length() <= 0) {
			throw new Exception("IP地址为空！");
		}
		// checkoutIpAddress(ipAddress);

		long result = 0;
		int two = 0, three = 0, four = 0;
		String[] value = ipAddress.split("\\.");
		two = Integer.parseInt(value[1]);
		three = Integer.parseInt(value[2]);
		four = Integer.parseInt(value[3]);

		result = Long.parseLong(Integer.valueOf(value[0]).toString()
				+ new DecimalFormat("000").format(two)
				+ new DecimalFormat("000").format(three)
				+ new DecimalFormat("000").format(four));

		return result;
	}

	/**
	 * 检查是否属于同一网段 在同一网段的，返回true 不在同一网段的，返回false
	 * 
	 * @param ip1
	 * @param subNet1
	 * @param ip2
	 * @param subNet2
	 * @return
	 */
	public static boolean checkoutSamenessNet(String ip1, String subNet1,
			String ip2, String subNet2) {

		String[] ipStr1 = ip1.split("\\.");
		String[] ipStr2 = ip2.split("\\.");
		String[] subNetStr1 = subNet1.split("\\.");
		String[] subNetStr2 = subNet2.split("\\.");
		int[] ipV1 = new int[4];
		int[] ipV2 = new int[4];
		int[] subNetV1 = new int[4];
		int[] subNetV2 = new int[4];

		// 赋值
		for (int i = 0; i < 4; i++) {
			ipV1[i] = Integer.parseInt(ipStr1[i]);
			ipV2[i] = Integer.parseInt(ipStr2[i]);
			subNetV1[i] = Integer.parseInt(subNetStr1[i]);
			subNetV2[i] = Integer.parseInt(subNetStr2[i]);
		}

		// 先与子网掩码进行“与”运算
		for (int i = 0; i < 4; i++) {
			ipV1[i] = ipV1[i] & subNetV1[i];
			ipV2[i] = ipV2[i] & subNetV2[i];
		}

		// 如果两者值一样，则在同一网段
		if ((ipV1[0] == ipV2[0]) && (ipV1[1] == ipV2[1])
				&& (ipV1[2] == ipV2[2]) && (ipV1[3] == ipV2[3])) {
			return true;
		}

		return false;
	}

	public static void main(String[] args) {
		try {
			// 验证子网掩码
			// boolean one = checkOutSubnetCode("255.255.0.255");
			// boolean two = checkSubnetCode("255.0.0.0");
			// boolean three = checkSubnetCode("255.0.255.0");
			// System.out.println("255.255.222.0 = " + one);

			// 验证toNumber方法
			/*
			 * long value = toNumber("10.118.1.1"); long value2 =
			 * toNumber("192.1.1.200"); System.out.println("10.118.1.1 = " +
			 * value); System.out.println("192.1.1.200 = " + value2);
			 */
			// 255, 254, 252, 248, 240, 224, 192, 128
			String[] ip = new String[] { "255.255.255.255", "255.255.255.254",
					"255.255.255.252", "255.255.255.248", "255.255.255.224",
					"255.255.255.192", "255.255.255.128", "255.255.255.0",
					"255.255.255.87", "255.255.0.255", "255.255.0.254",
					"255.255.0.252", "255.255.0.248", "255.255.0.224",
					"255.255.0.192", "255.255.0.128", "255.0.255.255",
					"255.0.255.254", "255.0.255.252", "255.0.255.248",
					"255.0.255.224", "255.0.255.192", "255.0.255.128",
					"255.248.255.255", "255.248.255.254", "255.248.255.252",
					"255.248.255.248", "255.248.255.224", "255.248.255.192",
					"255.248.255.128", "255.145.255.255", "255.145.255.254",
					"255.145.255.252", "255.145.255.248", "255.145.255.224",
					"255.145.255.192", "255.145.255.128", "255.255.0.0",
					"255.254.0.0", "255.252.0.0", "255.248.0.0", "255.240.0.0",
					"255.192.0.0", "255.128.0.0", "255.0.255.0", "255.0.254.0",
					"255.0.252.0", "255.0.248.0", "255.0.240.0", "255.0.192.0",
					"255.0.128.0"};

			for (int i = 0; i < ip.length; i++) {
				System.out.print(ip[i] + " is :");
				try {
					System.out.println(checkOutSubnetCode(ip[i]));
				} catch (Exception e) {
					System.out.println(" Error");
				}
			}
			checkOutSubnetCode("255.255.64.0");

			/*
			 * System.out.println(checkoutSamenessNet("192.168.1.100",
			 * "255.255.255.0", "192.168.1.202", "255.255.0.0"));
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
