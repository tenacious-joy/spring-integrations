package com.example.springintegration.channels.springintegrationdemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

// @SpringBootApplication
public class SpringIntegrationDemoApplication {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"/META-INF/spring/si-components.xml");

//		MessageChannel channel = context.getBean("messageChannel", MessageChannel.class);
//
//		Message<String> message1 = MessageBuilder.withPayload("Hello world 1!").build();
//		Message<String> message2 = MessageBuilder.withPayload("Hello world 2!").build();
//		Message<String> message3 = MessageBuilder.withPayload("Hello world 3!").build();
//
//
//		System.out.println("Sending message 1");
//		channel.send(message1);
//
//		System.out.println("Sending message 2");
//		channel.send(message2);
//
//		System.out.println("Sending message 3");
//		channel.send(message3);
//
//		System.out.println("Sent all the messages");

		// SpringApplication.run(SpringIntegrationDemoApplication.class, args);
	}
}
