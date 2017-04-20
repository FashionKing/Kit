package utils.app.com.commonutlis.utils;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import utils.app.com.commonutlis.base64.EncodeBase64;


/**
 * @Description 封装了一些加密工具方法, 包括 3DES, MD5 等.
 * @author zhangqh
 * @date Apr 8, 2013
 */
public class CryptTool {

	public CryptTool() {
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString().toUpperCase();
	}
	
	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArrayToHexString(byte[] b)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 * @author LiGuoQing
	 */
	public static byte[] hexString2ByteArray(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 生成3DES密钥.
	 * 
	 * @param key_byte
	 *            seed key
	 * @throws Exception
	 * @return javax.crypto.SecretKey Generated DES key
	 */
	public static SecretKey genDESKey(byte[] key_byte)
			throws Exception {

		SecretKey k = null;
		k = new SecretKeySpec(key_byte, "DESede");
		return k;
	}

	public static SecretKey genDESKey() throws Exception {
		String keyStr = "$1#2@f3&4~6%7!a+*cd(e-h)";// 使用固定key
		// System.out.println("DES 加密使用的固定key，keyStr ＝＝ " + keyStr);
		byte key_byte[] = keyStr.getBytes();// 3DES 24 bytes key
		SecretKey k = null;
		k = new SecretKeySpec(key_byte, "DESede");
		return k;
	}

	public static SecretKey genDESKey(String key) throws Exception {
		String keyStr = key;// 使用固定key
		// System.out.println("DES 加密使用的固定key，keyStr ＝＝ " + keyStr);
		byte key_byte[] = keyStr.getBytes();// 3DES 24 bytes key
		SecretKey k = null;
		k = new SecretKeySpec(key_byte, "DESede");
		return k;
	}

	/**
	 * 3DES 解密(byte[]).
	 * 
	 * @param key
	 *            SecretKey
	 * @param crypt
	 *            byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] desDecrypt(SecretKey key, byte[] crypt)
			throws Exception {
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede");
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(crypt);
	}

	/**
	 * 3DES 解密(String).
	 * 
	 * @param key
	 *            SecretKey
	 * @param crypt
	 *            byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public static String desDecrypt(SecretKey key, String crypt)
			throws Exception {
		//return byteArrayToHexString(desDecrypt(key, crypt.getBytes()));
		return new String(desDecrypt(key,hexString2ByteArray(crypt)));
	}
	
	/**
	 * 3DES解密
	 * @param key 解密密钥,密钥要求24位，如果不足24位，补0，多余24位，截取前24位
	 * @param crypt 被加密字符串
	 * */
	public static String desDecrypt(String key,String crypt) {
		//把KEY处理成24位，如果不足，在后面补0，如果超出，截取前八位
		String procKey = procKey(key);
		try {
			return CryptTool.desDecrypt(genDESKey(procKey), crypt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把KEY处理成24位，如果不足，在后面补0，如果超出，截取前八位
	 * */
	private static String procKey(String key) {
		if(key.length()<24) {
			while(key.length()<24) {
				key = key + "0";
			}
			return key;
		}else if(key.length()>24) {
			return key.substring(0,24);
		}
		
		return key;
	}
	
	/**
	 * 3DES加密(byte[]).
	 * 
	 * @param key
	 *            SecretKey
	 * @param src
	 *            byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] desEncrypt(SecretKey key, byte[] src)
			throws Exception {
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede");
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(src);
	}

	/**
	 * 3DES加密(String).
	 * 
	 * @param key
	 *            SecretKey
	 * @param src
	 *            byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public static String desEncrypt(SecretKey key, String src)
			throws Exception {
		return byteArrayToHexString(desEncrypt(key, src.getBytes()));
	}

	/**
	 * 3DES加密
	 * @param key 加密密钥,密钥要求24位，如果不足24位，补0，多余24位，截取前24位
	 * @param //需要被加密的字符串
	 * @return 3DES加密，并且转换成16进制字符串
	 * */
	public static String desEncrypt(String key,String src) {
		String procKey = procKey(key);
		try {
			return desEncrypt(CryptTool.genDESKey(procKey),src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * MD5 摘要计算(byte[]).
	 * 
	 * @param src
	 *            byte[]
	 * @throws Exception
	 * @return byte[] 16 bit digest
	 */
	public static byte[] md5Digest(byte[] src) throws Exception {
		java.security.MessageDigest alg = java.security.MessageDigest
				.getInstance("MD5"); // MD5 is 16 bit message digest

		return alg.digest(src);
	}

	/**
	 * MD5 摘要计算(String).
	 * 
	 * @param src
	 *            String
	 * @throws Exception
	 * @return String
	 */
	public static String md5Digest(String src) throws Exception {
		return byteArrayToHexString(md5Digest(src.getBytes()));
	}

	/**
	 * BASE64 编码.
	 * 
	 * @param src
	 *            String inputed string
	 * @return String returned string
	 */
	public static String base64Encode(String src) {
		EncodeBase64 encoder = new EncodeBase64();

		return encoder.encode(src.getBytes());
	}

	/**
	 * BASE64 编码(byte[]).
	 * 
	 * @param src
	 *            byte[] inputed string
	 * @return String returned string
	 */
	public static String base64Encode(byte[] src) {
		EncodeBase64 encoder = new EncodeBase64();

		return encoder.encode(src);
	}
	/**
	 * 对给定字符进行 URL 编码.
	 * 
	 * @param src
	 *            String
	 * @return String
	 */
	public static String urlEncode(String src) {
		try {
			src = java.net.URLEncoder.encode(src, "UTF-8");

			return src;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return src;
	}

	/**
	 * 对给定字符进行 URL 解码
	 * 
	 * @param value
	 *            解码前的字符串
	 * @return 解码后的字符串
	 */
	public String urlDecode(String value) {
		try {
			return java.net.URLDecoder.decode(value, "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return value;
	}

	

    @SuppressWarnings("restriction")
    public static String getBASE64(String s) { 

        if (s == null) 
            return null; 

        return (new EncodeBase64()).encode( s.getBytes() ); 
    }

	/**
	 * 加密
	 */
	public static String encryptionUrl(String url, String dataJson,boolean  haveauth) {
		try {
			// 获得的明文数据
			String desStr = dataJson;
			// 转成字节数组
			byte src_byte[] = desStr.getBytes();

			// MD5摘要
			byte[] md5Str = WebEncryptionUtil.md5Digest(src_byte);
			// 生成最后的SIGN
			String SING = WebEncryptionUtil.byteArrayToHexString(md5Str);

			desStr = CryptTool.getBASE64(dataJson);
			// http://localhost:8080/order/orderContler/?sing=key&data=密文
			StringBuffer str = new StringBuffer();
			str.append(url);
			str.append("sing=");
			str.append(SING);
			str.append("&data=");
			str.append(desStr);
			if(haveauth) {
			str.append("&auth=all");
			}
			return  str.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	/** Test crypt */
	public static void main(String[] args) throws Exception{
//		try {		
//	        DataObj dataObj = new DataObj();
//	        dataObj.setBusinessId("BD2016050445678945621245");
//	        String desStr = JSONObject.toJSON(dataObj).toString();
//
//			// 使用固定key值
//			String keyStr = "123456";// 使用固定key
//			desStr = desStr + "&KEY=" + keyStr;// 将key值和明文数据组织成一个待加密的串
//			System.out.println("待加密的字符串 desStr ＝＝ " + desStr);
//			// 转成字节数组
//			byte src_byte[] = desStr.getBytes();
//
//			// MD5摘要
//			byte[] md5Str = md5Digest(src_byte);
//			// 生成最后的SIGN
//			String SING = byteArrayToHexString(md5Str);
//			System.out.println("SING == " + SING);
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		System.out.println(md5Digest("REQUESTSEQ=2012062600173481&CUSTCODE=18911206159&TRADETIME=20120626170456&PRTNCODE=0018888888&TXNAMOUNT=30&KEY=G7AXS7874305BV59"));
//		
//	    
//	    http://mall.xylbn.cn:9005/wcm/product/enterIntoProductManager?
//	    sing=e10adc3949ba59abbe56e057f20f883e&&data=ODg4OGVjZDYxNTI4MWNlNDI1YWY1M2NjY2ZkNTQwY2M=
//	        key = 123456
//	     
//	    DataObj dataTmp = new DataObj();
//	    dataTmp.setOrderStatus("1");
//	    dataTmp.setStoreId("BD2016042920160300000028");
//	    dataTmp.setFlag("wl");
//	    dataTmp.setSourceType("all");
//	    String outData = JSONObject.toJSON(dataTmp).toString();
//
//	    
////	    {"flag":"wl","orderStatus":"1","sourceType":"all","storeId":"BD2016042920160300000028"} 
//
//	    
////        String sing = md5Digest("123456");
////        String d1 = desEncrypt(outData,sing);
////        String d2 = desDecrypt(sing,d1);
////        System.out.println(d1);
//////        System.out.println(d2);
//        
//        
////        System.out.println("sing:"+sing);
//        System.out.println("outDate:"+outData);
//        String encData = base64Encode(outData);
//        String decData = base64Decode(encData);
//        System.out.println("encData:"+encData);
//        System.out.println("decData:"+decData);
	    
	}
}
