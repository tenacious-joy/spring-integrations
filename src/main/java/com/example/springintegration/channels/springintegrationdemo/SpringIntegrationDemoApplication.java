package com.example.springintegration.channels.springintegrationdemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

// @SpringBootApplication
public class SpringIntegrationDemoApplication {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"/META-INF/spring/si-components.xml");
		// SpringApplication.run(SpringIntegrationDemoApplication.class, args);
	}
}
