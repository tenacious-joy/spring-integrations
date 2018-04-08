package com.example.springintegration.adpaters.fileadapters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIntegrationFileAdapterApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/si-components.xml");
    }
}
