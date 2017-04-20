package utils.app.com.commonutlis.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;


public class ThreeDes {
	    private static BASE64Encoder base64 = new BASE64Encoder();
	    private static byte[] myIV = { 50, 51, 52, 53, 54, 55, 56, 57 };
	    public static String desEncrypt(String input,String strkey) throws Exception 
	    {
	        strkey = procKey(strkey);
	        BASE64Decoder base64d = new BASE64Decoder();
	        DESedeKeySpec p8ksp = null;
	        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(strkey));
	        Key key = null;
	        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);
	        
	        input = padding(input);
	        byte[] plainBytes = (byte[])null;
	        Cipher cipher = null;
	        byte[] cipherText = (byte[])null;
	        
	        plainBytes = input.getBytes("UTF8");
	        cipher = Cipher.getInstance("DESede/CBC/NoPadding");
	        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
	        IvParameterSpec ivspec = new IvParameterSpec(myIV);
	        cipher.init(1, myKey, ivspec);
	        cipherText = cipher.doFinal(plainBytes);
	        String regStr = removeBR(base64.encode(cipherText));
	        String rtn = CryptTool.byteArrayToHexString(regStr.getBytes());
	        return rtn;
	    }
	     
	    public static String desDecrypt(String cipherText,String strkey) throws Exception 
	    {
	    	cipherText = new String(CryptTool.hexString2ByteArray(cipherText));
	    	strkey = procKey(strkey);
	        BASE64Decoder base64d = new BASE64Decoder();
	        DESedeKeySpec p8ksp = null;
	        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(strkey));
	        Key key = null;
	        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);
	        
	        Cipher cipher = null;
	        byte[] inPut = base64d.decodeBuffer(cipherText);
	        cipher = Cipher.getInstance("DESede/CBC/NoPadding");
	        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
	        IvParameterSpec ivspec = new IvParameterSpec(myIV);
	        cipher.init(2, myKey, ivspec);
	        byte[] output = removePadding(cipher.doFinal(inPut));

	        return new String(output, "UTF8");
	        
	    } 
	     
	    private static String removeBR(String str) {
	        StringBuffer sf = new StringBuffer(str);

	        for (int i = 0; i < sf.length(); ++i)
	        {
	          if (sf.charAt(i) == '\n')
	          {
	            sf = sf.deleteCharAt(i);
	          }
	        }
	        for (int i = 0; i < sf.length(); ++i)
	          if (sf.charAt(i) == '\r')
	          {
	            sf = sf.deleteCharAt(i);
	          }

	        return sf.toString();
	      }

	      public static String padding(String str)
	      {
	        byte[] oldByteArray;
	        try
	        {
	            oldByteArray = str.getBytes("UTF8");
	            int numberToPad = 8 - oldByteArray.length % 8;
	            byte[] newByteArray = new byte[oldByteArray.length + numberToPad];
	            System.arraycopy(oldByteArray, 0, newByteArray, 0, oldByteArray.length);
	            for (int i = oldByteArray.length; i < newByteArray.length; ++i)
	            {
	                newByteArray[i] = 0;
	            }
	            return new String(newByteArray, "UTF8");
	        }
	        catch (UnsupportedEncodingException e)
	        {
	            System.out.println("Crypter.padding UnsupportedEncodingException"); 
	        }
	        return null;
	      }
	      public static byte[] removePadding(byte[] oldByteArray)
	      {
	        int numberPaded = 0;
	        for (int i = oldByteArray.length; i >= 0; --i)
	        {
	          if (oldByteArray[(i - 1)] != 0)
	          {
	            numberPaded = oldByteArray.length - i;
	            break;
	          }
	        }

	        byte[] newByteArray = new byte[oldByteArray.length - numberPaded];
	        System.arraycopy(oldByteArray, 0, newByteArray, 0, newByteArray.length);

	        return newByteArray;
	      }
	      
	  	/**
	  	 * 把KEY处理成32位，如果不足，在后面补0，如果超出，截取前32位
	  	 * */
	  	private static String procKey(String key) {
	  		if(key.length()<32) {
	  			while(key.length()<32) {
	  				key = key + "0";
	  			}
	  			return key;
	  		}else if(key.length()>32) {
	  			return key.substring(0,32);
	  		}
	  		
	  		return key;
	  	}
	      
	    public static void main(String args[]) throws Exception
	    {
	    	String input = "http://localhost:8080/CARDNO=4518110348880219&VALIDDATE=1012&CVV2=111&IDTYPE=00&CARDUSERID=3101091988888885613&CARDUSERNAME=阮向荣";
	    	String strkey = "143BD50540AA7F879DB5A712600922AB";
//	    	String desstr = ThreeDes.desEncrypt(input, strkey);
	    	String desstr = "594872715464686E75714E66454C5A63315746343377353554686C6B4F774D5A4557374F624D626D6A59345A46667467397533725A4D4451726E627A4D6A4D6C6879574F46496243754E2B35362F6ddB492B66394462724250008624872473452795A524269345A7455336D462F36534264435457472F6472436A74492F766C504A4D4E5255534B6E793972334C6F6156394652767A6E6C4B78586D46506D51556A3637546370643967444B35466A526F5A46706D667157673D3D";
	    	String input1 = ThreeDes.desDecrypt(desstr, strkey);
	    
	    }

}
