/**
 * 
 */
package com.imooc.code;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import com.imooc.security.core.validate.code.ImageCode;
import com.imooc.security.core.validate.code.ValidateCodeGenerator;

/**
 * @author 35-pxiaodong
 *
 */
//配合com.imooc.security.core.validate.code.ValidateCodeBeanConfig中的
//@ConditionalOnMissingBean(name = "imageCodeGenerator")注解使用
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

	/* (non-Javadoc)
	 * @see com.imooc.security.core.validate.code.ValidateCodeGenerator#createImageCode(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ImageCode createImageCode(HttpServletRequest request) {
		System.out.println("更高级的图形验证码生成代码");
		return null;
	}

}
