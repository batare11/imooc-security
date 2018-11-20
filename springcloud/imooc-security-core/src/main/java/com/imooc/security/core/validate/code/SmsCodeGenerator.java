package com.imooc.security.core.validate.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;

import com.imooc.security.core.properties.SecurityProperties;

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;
	
	
	@Override
	public ValidateCode createImageCode(HttpServletRequest request) {
		String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
		return new ValidateCode(code,securityProperties.getCode().getSms().getExpireIn());//有效期60秒
	}
		/**
		 * 生成随机背景条纹
		 */

		public SecurityProperties getSecurityProperties() {
			return securityProperties;
		}

		public void setSecurityProperties(SecurityProperties securityProperties) {
			this.securityProperties = securityProperties;
		}
}
