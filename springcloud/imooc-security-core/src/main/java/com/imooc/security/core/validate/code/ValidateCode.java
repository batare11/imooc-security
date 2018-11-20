package com.imooc.security.core.validate.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ValidateCode {
    
	private String code;//随机数存到session中
	
	private LocalDateTime expireTime;//有效时间

	public ValidateCode (String code,
			int expireIn) {
		this.code=code;
		this.expireTime=LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public ValidateCode (BufferedImage image,String code,
			LocalDateTime expireTime) {
		this.code=code;
		this.expireTime=expireTime;
	}

	public ValidateCode(String code, LocalDateTime expireTime) {
		this.code=code;
		this.expireTime=expireTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}

	public boolean isExpried() {
		return LocalDateTime.now().isAfter(expireTime);
	}
	
}
