package com.imooc.security.core.properties;

public class BrowserProperties {
	
	//设定默认值如果没有指定loginPage则访问该页面
	private String loginPage = "/imooc-signIn.html";

	private LoginType loginType=LoginType.JSON;//默认返回json
//	private LoginType loginType=LoginType.REDIRECT;
	
	private int rememberMeSeconds = 24*3600;//设置记住我的时间
	
	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}
	
}
