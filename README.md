# Kit
工具包
public class QRCodeUtils {
	private QRCodeUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * 生成二维码图片的宽度
	 */
	public static final int QR_WIDTH = 300;
	/**
	 * 生成二维码图片的高度
	 */
	public static final int QR_HEIGHT = 300;

	/**
	 * 生成QR图
	 *
	 * @param path
	 * @param whith 是否黑白色
	 * @return
	 */
	public static Bitmap createQrBitmap(String path, boolean whith) {
		try {
			if (TextUtils.isEmpty(path)) {
				return null;
			}
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new QRCodeWriter().encode(path,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						if (whith) {
							pixels[y * QR_WIDTH + x] = 0xff000000;

						} else {
							if (x < QR_WIDTH / 2 && y < QR_HEIGHT / 2) {
								pixels[y * QR_WIDTH + x] = 0xFF0094FF;// 蓝色
								Integer.toHexString(new Random().nextInt());
							} else if (x < QR_WIDTH / 2 && y > QR_HEIGHT / 2) {
								pixels[y * QR_WIDTH + x] = 0xFFff3c25;// 黄色
							} else if (x > QR_WIDTH / 2 && y > QR_HEIGHT / 2) {
								pixels[y * QR_WIDTH + x] = 0xFF5ACF00;// 绿色
							} else {
								pixels[y * QR_WIDTH + x] = 0xFF000000;// 黑色
							}
						}

					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;// 白色
					}

				}
			}

			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);

			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			return bitmap;
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param path
	 * @param whith
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap createQrBitmap(String path, boolean whith, int w, int h) {
		try {
			if (TextUtils.isEmpty(path)) {
				return null;
			}
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new QRCodeWriter().encode(path,
					BarcodeFormat.QR_CODE, w, h, hints);
			int[] pixels = new int[w * h];
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					if (bitMatrix.get(x, y)) {
						if (whith) {
							pixels[y * w + x] = 0xff000000;

						} else {
							if (x < w / 2 && y < h / 2) {
								pixels[y * w + x] = 0xFF0094FF;// 蓝色
								Integer.toHexString(new Random().nextInt());
							} else if (x < w / 2 && y > h / 2) {
								pixels[y * w + x] = 0xFFff3c25;// 黄色
							} else if (x > w / 2 && y > h / 2) {
								pixels[y * w + x] = 0xFF5ACF00;// 绿色
							} else {
								pixels[y * w + x] = 0xFF000000;// 黑色
							}
						}

					} else {
						pixels[y * w + x] = 0xffffffff;// 白色
					}

				}
			}

			Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

			bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
			return bitmap;
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 传入字符串生成条形码
	 *
	 * @param str
	 * @param w,
	 * @param h
	 * @return
	 * @throws WriterException
	 */
	public static Bitmap Create1DCode(String str, int w, int h) throws WriterException {
		// 生成矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODABAR, w, h);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 矩阵转为一维像素数组,也就是一直横着排了
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap,具体参考api
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * 传入字符串生成二维码
	 *
	 * @param str
	 * @return
	 * @throws WriterException
	 */
	public static Bitmap Create2DCode(String str) throws WriterException {
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 400, 400);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap,具体参考api
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}
}
