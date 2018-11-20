package com.imooc.security.browser;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.imooc.security.browser.authentication.ImoocAuthenticationSuccessHandler;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{
   
	@Autowired
	private SecurityProperties securityProperties;
	
	//让系统使用我们自定义 而不是系统默认的配置
	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	
    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	
    @Autowired
    private DataSource dataSource;
    
    @Autowired//从数据库查找token后获取用户信息 调用该类根据获取到的用户登录
    //它的启动类是MyUserDetailsService.java
    private UserDetailsService userDetailsService;
    
	@Bean
	public PasswordEncoder passwordEncoder() {
		//这里如果是自己编写的加密 则调用自己的类 方法有编码和解码验证方法
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		validateCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
		validateCodeFilter.setSecurityProperties(securityProperties);
		validateCodeFilter.afterPropertiesSet();
		
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
		.formLogin()//认证
		.loginPage("/authentication/require")//设置登录页面
		.loginProcessingUrl("/authentication/form")//遇到该请求则进行user password认证
		.successHandler(imoocAuthenticationSuccessHandler)//成功后 使用我们自己的处理器处理
		.failureHandler(imoocAuthenticationFailureHandler)//设置失败处理器
		.and()
		.rememberMe()
		    .tokenRepository(persistentTokenRepository())
		    .tokenValiditySeconds(securityProperties.getBrowsers().getRememberMeSeconds())
		    .userDetailsService(userDetailsService)
//		http.httpBasic()
		.and()
		.authorizeRequests()//授权
		//当访问这个路径的时候不需要身份认证 除了它其他的是需要身份认证
		.antMatchers("/authentication/require"
				,securityProperties.getBrowsers().getLoginPage()
				,"/code/*").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.csrf().disable();
	}
    //记住我功能首先从数据库取
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		//肯定需要数据源的
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true);//启动的时候自动创建表 存放token
		return tokenRepository;
		
	}
}
