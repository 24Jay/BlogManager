package com.jay.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class BlogApplication implements EmbeddedServletContainerCustomizer
{

	public static void main(String[] args)
	{
		SpringApplication.run(BlogApplication.class, args);
		System.out.println(".................Blog...............................................");
		System.out.println(".....................Resource.......................................");
		System.out.println(".............................Manager................................");
		System.out.println("....................................Startup.........................");
		
	}

	/**
	 * 设置sprint boot內置容器端口
	 */
	public void customize(ConfigurableEmbeddedServletContainer container)
	{
		container.setPort(31547);
	}
	
}