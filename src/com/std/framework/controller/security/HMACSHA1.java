package com.std.framework.controller.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Calendar;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class HMACSHA1 {

	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";
	private static final Long timeDelayLimit = 60l*1000l*5l;

	public static String getSignature(String encryptKey, String encryptText) throws InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] data = encryptKey.getBytes(ENCODING);
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		Mac mac = Mac.getInstance(MAC_NAME);
		mac.init(secretKey);
		byte[] text = encryptText.getBytes(ENCODING);
		byte[] finalText = mac.doFinal(text);
		//TODO  please use sun.misc.BASE64Encoder/BASE64Decoder below jdk1.8
		Encoder base64 = Base64.getEncoder();
		String base64Text = base64.encodeToString(finalText);
		return base64Text;
	}

	
	public static String getUTCTime() {
		final Calendar cal = Calendar.getInstance();
		// 取得时间偏移量：
		final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		// 取得夏令时差：
		final int dstOffset = cal.get(Calendar.DST_OFFSET);
		// 从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return String.valueOf(cal.getTimeInMillis());
	}
	
	
	public static boolean isReplayAttacks(String clientTimestamp) {
		String serverUTCTime = HMACSHA1.getUTCTime();
		Long timeDelay = Math.abs((Long.parseLong(serverUTCTime) - Long.parseLong(clientTimestamp)) / 1000);
		if( timeDelay > timeDelayLimit)
			return true;
		return false;
	}
	
	public static String genereteEncryptText(String uid, String URIResource, String timestamp){
		return "uid=" + uid + "\n" + "URIResource=" + URIResource + "\n" + "timestamp=" + timestamp;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		try {
			String signature = HMACSHA1.getSignature("testkey","name=luoxiaob" );
			System.out.println(signature);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
