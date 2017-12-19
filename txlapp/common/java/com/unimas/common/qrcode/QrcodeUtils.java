package com.unimas.common.qrcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * 生成推荐下载查询QR码
 *
 */
public class QrcodeUtils {
	
	/**
	 * 生成推荐查询QR码
	 * @param content 链接URL
	 * @param hasLogo 是否添加LOGO
	 * @return
	 * @throws Exception
	 */
	public ByteArrayOutputStream genQrcodeImage(String content, boolean hasLogo) throws Exception {
		int QRCodeWidth = 302;
		int QRCodeHeight = 302;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BufferedImage barcode = null;
		if(hasLogo){
			InputStream logo_input_is = QrcodeUtils.class.getClassLoader().getResourceAsStream("logo.png");
			barcode = BarcodeFactory.genBarcode(content, QRCodeWidth, QRCodeHeight, logo_input_is);
		} else {
			barcode = BarcodeFactory.genBarcode(content, QRCodeWidth, QRCodeHeight);
		}
		ImageIO.write(barcode, "PNG", output);
		return output;
	}
	
	public static void main(String[] args) throws Exception {
		String url = "http://www.baidu.com";
		OutputStream out = new FileOutputStream(new File("D://test.png"));
		out.write(new QrcodeUtils().genQrcodeImage(url, false).toByteArray());
		out.close();
		
		OutputStream out2 = new FileOutputStream(new File("D://test2.png"));
		out2.write(new QrcodeUtils().genQrcodeImage(url, true).toByteArray());
		out2.close();
	}

}
