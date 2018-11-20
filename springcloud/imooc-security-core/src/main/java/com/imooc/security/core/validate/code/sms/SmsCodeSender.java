/**
 * 
 */
package com.imooc.security.core.validate.code.sms;

/**
 * @author 35-pxiaodong
 *
 */
public interface SmsCodeSender {
   
	void send(String mobile,String code);
	
}
