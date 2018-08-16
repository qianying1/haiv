package cn.qhb.haiv.core.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * 
 * @author yinls
 * 3DES加密解密
 *
 */
public class TripleDESCrypter {
	
	/**
	 * 3DESECB加密,key必须是长度大于等于 3*8 = 24 位
	 * @param src：数据源
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptThreeDESECB(String src, String key) throws Exception {
		//从原始密匙数据创建一个DESedeKeySpec对象 
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8")); 
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");  
	    SecretKey securekey = keyFactory.generateSecret(dks);  
	    // Cipher对象实际完成解密操作
	    Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");  
	    cipher.init(Cipher.ENCRYPT_MODE, securekey);  
	    byte[] retByte=cipher.doFinal(src.getBytes());
	
		return Base64.encodeBase64String(retByte);
	}
	
	/**
	 * 3DESECB解密,key必须是长度大于等于 3*8 = 24 位
	 * @param src：数据源
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decryptThreeDESECB(String src, String key) throws Exception {
		//--解密的key  
	    DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));  
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");  
	    SecretKey securekey = keyFactory.generateSecret(dks);  
	      
	    //--Chipher对象解密  
	    Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");  
	    cipher.init(Cipher.DECRYPT_MODE, securekey);
	    byte[] retByte = cipher.doFinal(Base64.decodeBase64(src));
	    
	    return new String(retByte);
	}
	
    public static void main(String[] args) throws Exception {
    	System.out.println(TripleDESCrypter.encryptThreeDESECB("111", "nnjs2014qwerasdf1234!@#$"));
    	System.out.println(TripleDESCrypter.decryptThreeDESECB("zcQjNUvaRnU=", "ABCDABCDABCD123412341234"));
    }  
}