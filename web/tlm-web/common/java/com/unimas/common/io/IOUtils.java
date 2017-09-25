package com.unimas.common.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 描述: 流操作
 * 
 * 作者:szpShang <br/>
 * 创建时间:2012-3-9<br/>
 * 版本号:1.0<br/>
 * 版权所有:杭州合众信息工程有限公司
 */
public class IOUtils {

	/**
	 * 把输入流转为字节
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] readToByte(InputStream input) throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		try {

			int tmp = 0;
			while ((tmp = input.read()) != -1) {
				bytestream.write(tmp);
			}
			return bytestream.toByteArray();

		} finally {
			if (input != null) {
				input.close();
			}
			if (bytestream != null) {

				bytestream.close();
			}
		}
	}

	/**
	 * 把输入流字符串
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static String readToString(InputStream input) throws IOException {
		return new String(readToByte(input));
	}

	/**
	 * 内容写到流中
	 * 
	 * @param content
	 * @param output
	 * @throws IOException
	 */
	public static void write(byte[] content, OutputStream output) throws IOException {
		try {
			output.write(content);
			output.flush();
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}

	/**
	 * 内容写到流中
	 * 
	 * @param content
	 * @param output
	 * @param encode
	 * @throws IOException
	 */
	public static void write(String content, OutputStream output, String encode) throws IOException {
		write(content.getBytes(encode), output);
	}

	/**
	 * 内容写到流中
	 * 
	 * @param content
	 * @param output
	 * @throws IOException
	 */
	public static void write(String content, OutputStream output) throws IOException {
		write(content.getBytes(), output);
	}

	/**
	 * 将输入流输入到输出流中
	 * 
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	public static void write(InputStream input, OutputStream output) throws IOException {
		try {
			int tmp = 0;
			while ((tmp = input.read()) != -1) {
				output.write(tmp);
			}
		} finally {
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
		}
	}
}