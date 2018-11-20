package com.imooc.security.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.imooc.security.core.properties.SecurityProperties;

/**
 * 使我们写的properties生效
 * @author 35-pxiaodong
 *
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
   
}
