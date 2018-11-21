package com.imooc.miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.imooc.miaosha.controller.DemoController;

//@EnableAutoConfiguration
@SpringBootApplication
//@EnableTransactionManagement
public class MainApplication 
/*extends SpringBootServletInitializer*/{
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

/*	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MainApplication.class);
	}*/
	
}
