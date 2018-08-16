package cn.qhb.haiv.core.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;


public class DataEncryptUtil {

	
	private static final String SECRET_KEY = "HTMD5KYE";
	
	private static final String SECRET_KEY_SPEC = "DES";
	
	private static final String CIPHER_KEY = "DES/CBC/PKCS5Padding";

	
	private static Key getKeyByPlainText(String plainKey) {
		try {
			return new SecretKeySpec(plainKey.getBytes(), SECRET_KEY_SPEC);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	
	private static String byte2Hex(byte abyte0[]) {
		StringBuffer stringbuffer = new StringBuffer();
		for (byte anAbyte0 : abyte0) {
			String s = Integer.toHexString(anAbyte0 & 0xff);
			if (s.length() != 2)
				stringbuffer.append('0').append(s);
			else
				stringbuffer.append(s);
		}

		return new String(stringbuffer);
	}

	
	private static byte[] hex2Byte(String s) {
		int i = s.length() / 2;
		byte abyte0[] = new byte[i];
		for (int j = 0; j < i; j++) {
			String s1 = s.substring(j * 2, j * 2 + 2);
			abyte0[j] = (byte) Integer.parseInt(s1, 16);
		}

		return abyte0;
	}

	
	private static byte[] doEncrypt(Key key, byte[] data) {
		try {
			// Get a cipher object
			Cipher cipher = Cipher.getInstance(CIPHER_KEY);
			IvParameterSpec iv = new IvParameterSpec(
					SECRET_KEY.getBytes("UTF-8"));
			// Encrypt
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			// byte[] stringBytes = amalgam.getBytes("UTF8");
			byte[] raw = cipher.doFinal(data);
			// BASE64Encoder encoder = new BASE64Encoder();
			// String base64 = encoder.encode(raw);
			return raw;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	private static byte[] doDecrypt(Key key, byte[] raw) {
		try {
			// Get a cipher object
			Cipher cipher = Cipher.getInstance(CIPHER_KEY);
			IvParameterSpec iv = new IvParameterSpec(
					SECRET_KEY.getBytes("UTF-8"));
			// Decrypt
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			// BASE64Decoder decoder = new BASE64Decoder();
			// byte[] raw = decoder.decodeBuffer(data);
			byte[] data = cipher.doFinal(raw);
			// String result = new String(stringBytes, "UTF8");
			// System.out.println("the decrypted data is: " + result);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public static synchronized String desEncrypt(String data) {

		Key key = getKeyByPlainText(SECRET_KEY);
		String thetext = "";
		try {
			byte[] orgData = data.getBytes("UTF8");
			byte[] raw = doEncrypt(key, orgData);
			thetext = byte2Hex(raw).toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return thetext;
	}

	
	public static synchronized String desDecrypt(String hexData) {

		String encryptedHexString = hexData; // 16进制编码的加密数据
		String theText = "";
		try {
			Key key = getKeyByPlainText(SECRET_KEY);

			byte[] data = doDecrypt(key, hex2Byte(encryptedHexString));

			theText = new String(data, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theText;
	}

	
	public static synchronized String encryptSha256(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			byte digest[] = md.digest(inputStr.getBytes("UTF-8"));

			return new String(Base64.encodeBase64(digest));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static synchronized String encryptMd5(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputStr.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(Integer.toHexString((int) (b & 0xff)));
			}

			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	// 测试
	public static void main(String[] args) throws Exception {

		String data = "123456";
		String a = DataEncryptUtil.encryptSha256(data);
		//String b = DataEncryptUtil.decryptSha256("pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=");
		System.out.println(data + "加密后的数据：" + a);
		//System.out.println(data + "解密后的数据：" + b);
		//System.out.println(DataEncryptUtil.desDecrypt("pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM="));
	}
}
