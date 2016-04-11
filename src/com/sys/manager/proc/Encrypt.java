package com.sys.manager.proc;

import java.security.MessageDigest;

/**
 *  对用户密码进行加密
 */
public class Encrypt {
       public static String encrypt(String s)
       {
    	   String input=(s==null ? "" : s);
    		  
    	   char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    	   try {
    	    byte[] temp = input.getBytes();
    	    MessageDigest mdTemp = MessageDigest.getInstance("MD5");
    	    mdTemp.update(temp);
    	    byte[] md = mdTemp.digest();
    	    int j = md.length;
    	    char str[] = new char[j * 2];
    	    int k = 0;
    	    for (int i = 0; i < j; i++) {
    	     byte byte0 = md[i];
    	     str[k++] = hexDigits[byte0 >>> 4 & 0xf];
    	     str[k++] = hexDigits[byte0 & 0xf];
    	    }
    	    return new String(str);
    	   } catch (Exception e) {
    	    return null;
    	   }
       }
}
