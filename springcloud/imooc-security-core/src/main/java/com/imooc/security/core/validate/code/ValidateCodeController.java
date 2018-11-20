package com.imooc.security.core.validate.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.sms.SmsCodeSender;

@RestController
public class ValidateCodeController {
    
	public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private ValidateCodeGenerator imageCodeGenerator;
	
	@Autowired
	private ValidateCodeGenerator smsCodeGenerator;
	
	@Autowired
	private SmsCodeSender defaultSmsCodeSender;
	
	@GetMapping("/code/image")
	public void createCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		ImageCode imageCode = (ImageCode) imageCodeGenerator.createImageCode(request);//code放到sesison 第二步
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
		//将图片写到响应的接口中 第三步
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
	}
	@GetMapping("/code/sms")
	public void createSms(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletRequestBindingException {
		
		ValidateCode smsCode = smsCodeGenerator.createImageCode(request);//code放到sesison 第二步
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
		String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
		defaultSmsCodeSender.send(mobile, smsCode.getCode());
	}
}
