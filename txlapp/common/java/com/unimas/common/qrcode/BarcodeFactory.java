package com.unimas.common.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 
 * @author chenjie10812
 *
 */
public class BarcodeFactory {
	// 二维码的补白宽度
	private static final int QRCode_FRAME_WIDTH = 1;
	// 中间LOGO的宽度、高、补白等
	private static final int LOGO_WIDTH = 90;
	private static final int LOGO_HEIGHT = 90;
	private static final int LOGO_HALF_WIDTH = LOGO_WIDTH / 2;
	private static final int LOGO_FRAME_WIDTH = 2;
	
	// 二维码写码器
	private static MultiFormatWriter mutiWriter = new MultiFormatWriter();
	/*
	public static void encode(String content, int width, int height, String srcImagePath, String destImagePath) {
		try {
			ImageIO.write(genBarcode(content, width, height, srcImagePath), "jpg", new File(destImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	*/
	/**
	 * @param content 二维码的内容，比如咱们这是网址
	 * 				  http://112.124.212.23:6003/wexin/tuijian/?tuijian_openid="+tuijian_openid
	 * @param width   宽
	 * @param height  高
	 * @param logo_input_is  logo图输入流
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	public static BufferedImage genBarcode(String content, int width, int height, InputStream logo_input_is)
			throws WriterException, IOException {
		// 读取logo图的数据
		int[][] srcPixels = null;
		if(logo_input_is != null){
			BufferedImage scaleImage = scale(logo_input_is, LOGO_WIDTH, LOGO_HEIGHT, true);
			srcPixels = new int[LOGO_WIDTH][LOGO_HEIGHT];
			for (int i = 0; i < scaleImage.getWidth(); i++) {
				for (int j = 0; j < scaleImage.getHeight(); j++) {
					srcPixels[i][j] = scaleImage.getRGB(i, j);
				}
			}
		}
		

		Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
		hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hint.put(EncodeHintType.MARGIN, QRCode_FRAME_WIDTH);//二维码周围的补白宽度
		// 生成二维码
		BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hint);
		System.out.println("matrix-width:"+matrix.getWidth()+",matrix-height:"+matrix.getHeight());
		// 二维矩阵转为一维像素数组
		int halfW = matrix.getWidth() / 2;
		int halfH = matrix.getHeight() / 2;
		int[] pixels = new int[width * height];

		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				if(srcPixels != null){
					// 中央的位置画logo
					if (x > halfW - LOGO_HALF_WIDTH && x < halfW + LOGO_HALF_WIDTH && y > halfH - LOGO_HALF_WIDTH
							&& y < halfH + LOGO_HALF_WIDTH) {
						pixels[y * width + x] = srcPixels[x - halfW + LOGO_HALF_WIDTH][y - halfH + LOGO_HALF_WIDTH];
					}
					// 在图片四周形成边框
					else if ((x > halfW - LOGO_HALF_WIDTH - LOGO_FRAME_WIDTH && x < halfW - LOGO_HALF_WIDTH + LOGO_FRAME_WIDTH
							&& y > halfH - LOGO_HALF_WIDTH - LOGO_FRAME_WIDTH && y < halfH + LOGO_HALF_WIDTH + LOGO_FRAME_WIDTH)
							|| (x > halfW + LOGO_HALF_WIDTH - LOGO_FRAME_WIDTH && x < halfW + LOGO_HALF_WIDTH + LOGO_FRAME_WIDTH
									&& y > halfH - LOGO_HALF_WIDTH - LOGO_FRAME_WIDTH
									&& y < halfH + LOGO_HALF_WIDTH + LOGO_FRAME_WIDTH)
							|| (x > halfW - LOGO_HALF_WIDTH - LOGO_FRAME_WIDTH && x < halfW + LOGO_HALF_WIDTH + LOGO_FRAME_WIDTH
									&& y > halfH - LOGO_HALF_WIDTH - LOGO_FRAME_WIDTH
									&& y < halfH - LOGO_HALF_WIDTH + LOGO_FRAME_WIDTH)
							|| (x > halfW - LOGO_HALF_WIDTH - LOGO_FRAME_WIDTH && x < halfW + LOGO_HALF_WIDTH + LOGO_FRAME_WIDTH
									&& y > halfH + LOGO_HALF_WIDTH - LOGO_FRAME_WIDTH
									&& y < halfH + LOGO_HALF_WIDTH + LOGO_FRAME_WIDTH)) {
						pixels[y * width + x] = 0xfffffff;
					} else {
						// 其余部分画二维码；
						pixels[y * width + x] = matrix.get(x, y) ? 0xff000000 : 0xfffffff;
					}
				} else {
					pixels[y * width + x] = matrix.get(x, y) ? 0xff000000 : 0xfffffff;
				}
			}
		}

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.getRaster().setDataElements(0, 0, width, height, pixels);
		System.out.println("width:"+image.getWidth()+",height:"+image.getHeight());
		return image;
	}
	
	/**
	 * @param content 二维码的内容，比如咱们这是网址
	 * 				  http://112.124.212.23:6003/wexin/tuijian/?tuijian_openid="+tuijian_openid
	 * @param width   宽
	 * @param height  高
	 * @param logo_input_is  logo图输入流
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	public static BufferedImage genBarcode(String content, int width, int height)
			throws WriterException, IOException {
		return genBarcode(content, width, height, null);
	}

	/**
	 * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
	 * 
	 * @param logo_input_is
	 *            源文件地址
	 * @param height
	 *            目标高度
	 * @param width
	 *            目标宽度
	 * @param hasFiller
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 * @throws IOException
	 */
	private static BufferedImage scale(InputStream logo_input_is, int height, int width, boolean hasFiller)
			throws IOException {
		double ratio = 0.0; // 缩放比例
		//File file = new File(logo_input_is);
		BufferedImage srcImage = ImageIO.read(logo_input_is);
		Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		// 计算比例
		if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
			if (srcImage.getHeight() > srcImage.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / srcImage.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / srcImage.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			destImage = op.filter(srcImage, null);
		}
		if (hasFiller) {// 补白
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic = image.createGraphics();
			graphic.setColor(Color.white);
			graphic.fillRect(0, 0, width, height);
			if (width == destImage.getWidth(null))
				graphic.drawImage(destImage, 0, (height - destImage.getHeight(null)) / 2, destImage.getWidth(null),
						destImage.getHeight(null), Color.white, null);
			else
				graphic.drawImage(destImage, (width - destImage.getWidth(null)) / 2, 0, destImage.getWidth(null),
						destImage.getHeight(null), Color.white, null);
			graphic.dispose();
			destImage = image;
		}
		return (BufferedImage) destImage;
	}

	public static void main(String[] args) {
		//BarcodeFactory.encode("http://coolshell.cn/articles/10590.html", 302, 302, "e:\\cdj_test\\logo.png", "e:\\cdj_test\\barcode.png");
	}
}