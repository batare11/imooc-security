package com.imooc.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	
	private static final String salt = "1a2b3c4d";
  /**
   * 一次md5方法
   * @param src
   * @return
   */
   public static String md5(String src){
	   return DigestUtils.md5Hex(src);
   }
   /**
    * 给pass加盐 然后md5一次
    * @param inputPass
    * @return
    */
   public static String inputPassFormPass(String inputPass){
	   String str = ""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
	   return md5(str);
   }
   /**
    * 保存到数据库中  做第二次md5  记录随机salt
    * @param inputPass
    * @return
    */
   public static String formPassToDBPass(String formPass,String salt){
	   String str = ""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
	   return md5(str);
   }
   
   public static String inputPassToDbPass(String input,String saltDB){
	   String formPass = inputPassFormPass(input);
	   String dbPass = formPassToDBPass(formPass,saltDB);
	   return dbPass;
   }
   public static void main(String[] args) {
	System.out.println(inputPassFormPass("123456"));
//	System.out.println(formPassToDBPass(inputPassFormPass("123456"),"pxd12345"));
//	System.out.println(inputPassToDbPass("123456","pxd12345"));
}
}
