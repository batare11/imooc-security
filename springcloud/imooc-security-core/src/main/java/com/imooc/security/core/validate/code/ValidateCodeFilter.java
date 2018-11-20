package com.imooc.security.core.validate.code;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.imooc.security.core.properties.SecurityProperties;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	private Set<String> urls = new HashSet<String>();
	
	@Autowired
	private SecurityProperties securityProperties;
	
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	
	
	@Override
	public void afterPropertiesSet() throws ServletException {
		String [] configUrls = StringUtils.splitByWholeSeparator(securityProperties.getCode().getImage().getUrl(), ",");
		for(String url:configUrls) {
			urls.add(url);
		}
		urls.add("/authentication/form");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		  boolean action = false;
		  logger.info("urls的长度是:"+urls.size()+"它们是："+urls.toString());
		  logger.info("requestUri是:"+request.getRequestURI());
		  for(String url:urls) {
			  if (antPathMatcher.match(url, request.getRequestURI())) {
				action = true;
			}
		  }
		  if (action) {
			  try {
					//校验逻辑
					validate(new ServletWebRequest(request));
				} catch (ValidateCodeException e) {
					authenticationFailureHandler.onAuthenticationFailure(request, response, e);
					return;
				}
		 }
		/*//主干逻辑
		if (StringUtils.equals("/authentication/form", request.getRequestURI())
				 && StringUtils.equalsIgnoreCase(request.getMethod(),"post")) {
			try {
				//校验逻辑
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}*/
		
		filterChain.doFilter(request, response);
	}
    
	//校验逻辑
	private void validate(ServletWebRequest request) throws ServletRequestBindingException {
		
        ImageCode codeInSession = (ImageCode)sessionStrategy.getAttribute(request, 
        		ValidateCodeController.SESSION_KEY);
        
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
        
        if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码的值不能为空,the code must be not null");
		}
        if (codeInSession == null) {
			throw new ValidateCodeException("验证码不存在");
		}
        if (codeInSession.isExpried()) {
        	sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
			throw new ValidateCodeException("验证码已过期");
		}
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}
        
        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
	}

	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public SessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}

	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}

	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
}
