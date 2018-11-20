/**
 * 
 */
package com.imooc.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.removeAllMappings;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import com.github.tomakehurst.wiremock.client.WireMock;

/**
 * @author 35-pxiaodong
 *
 */
public class MockServer {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		configureFor(8062);//指定端口
        removeAllMappings();//清除所有配置
        
        mock("/order/1","01");
	}

	private static void mock(String url, String fileName) throws Exception {
		ClassPathResource resource = new ClassPathResource("/mock/response/"+fileName+".txt");
        String content = StringUtils.join(FileUtils.readLines(resource.getFile(),"UTF-8").toArray(),"\n");
        stubFor(get(urlPathEqualTo(url)).willReturn(aResponse().withBody(content).withStatus(200)));
	}

}
