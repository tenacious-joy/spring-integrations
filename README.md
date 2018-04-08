# spring-integrations
Contains examples of spring integrations

This example describes the use of spring integrations framework and it's components to connect producers and consumers of messaging world.

Producer (Sender) is the system that publishes messages. Consumer (receiver) is the system that receives /listens to messages.Channels can be used to connect producers and consumers.

Messages are delivered to various endpoints.
There are several message end points in the messaging world. End points might be Adapters, filters, transformers,enrichers, service activators, Gateways.

This example focuses on Adapters. Adapters are used to connect channel to other systems.

**Goal:**

Take input from the console and display the same message in the output.

**How are we going to do?**

Leverage spring integration adapters and standard java input and output.

We could achieve this using two types of channeling.
1. Pollable Channel
2. Subscribable Channel

**Using Subscribable channel:**

We need to add two dependencies, org.springframework.integration:spring-integration-stream and org.springframework.integration:spring-integration-core into the build.gradle

Include bean configurations for producer / consumer inside si-components.xml. Let's go over the configuration step-by-step.

A. Bean Definitions for Spring-Integration-core and spring-integration-stream.

`<beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:int="http://www.springframework.org/schema/integration"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:int-stream="http://www.springframework.org/schema/integration/stream"
        xsi:schemaLocation="
 	http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
 	http://www.springframework.org/schema/integration http://www.springframework.org/schema/integration/spring-integration.xsd
 	http://www.springframework.org/schema/integration/stream http://www.springframework.org/schema/integration/stream/spring-integration-stream.xsd">
`

B. stdin-channel adapter wraps the java standard input. It takes any input that you type into console (from java input stream) and convert into the text message and send it to channel. here channel is referenced by message-channel.This is the producer.

`<int-stream:stdin-channel-adapter id="producer"
                                       channel="messageChannel" />`

C. Let's create two consumers. stdout-channel adapter wraps the java standard output. It fetches the message from the channel and send it to java standard output stream (console here). Append-new-line is added as an attribute to print the messages to the next line (As we have more than one consumer output the messages)

`<int-stream:stdout-channel-adapter
             id="consumer1" channel="messageChannel" append-newline="true" />
     <int-stream:stdout-channel-adapter
             id="consumer2" channel="messageChannel" append-newline="true" />`
             
D. Let's add poller channel configuration that is used by std-in adapter used to determine how frequently should it check for new messages in java standard output stream.

`<int:poller id="defaultPoller" default="true"
                 max-messages-per-poll="5" fixed-rate="2000" />`
                 
It polls the channel every 2 seconds here. 

E. Producers and consumers are ready. Let's add the channel configuration which is used to deliver message from producer to consumer. Here's we use Subscribable channel. Subscribable channels do not buffer messages. Instead they deliver to all subscribers (here stdout-channel adapters)
`<int:publish-subscribe-channel id="messageChannel" />`

You could see that channel's id and channel reference in producer and consumer are the same.

F. All the configurations are ready. We need to register these bean definitions in spring. Create a class called StartUp.java and add main method with the following code.

`ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
 				"/META-INF/spring/si-components.xml");`
 				
This initializes the spring application context.

**Using Pollable Channel**

Steps A,B,C,D and F are same. Replace step E with the following code:

`<int:channel id="messageChannel">
         <int:queue capacity="2" />
     </int:channel>`
     
Pollable channel has buffer. It can hold messages. but, the drawback is it can deliver message to only one subscriber. hence it's called point-to-point channel. When more than one subscriber is added to the configuration. It skips everything except the first one.

The last type of channel is direct channel which is the default channel type used for <int:channel>. It's also a one-to-one channel.

`<int:channel id="messageChannel">
     </int:channel>`
     
**Can you view the messages in the buffer?**

Since pollable and direct channels can buffer messages, we could comment out the consumer and producer configurations from si-components.xml
Add the following snippet to your SpringIntegrationDemoApplication.java.

`MessageChannel channel = context.getBean("messageChannel", MessageChannel.class);
 
 		Message<String> message1 = MessageBuilder.withPayload("Hello world 1!").build();
 		Message<String> message2 = MessageBuilder.withPayload("Hello world 2!").build();
 		Message<String> message3 = MessageBuilder.withPayload("Hello world 3!").build();
 
 
 		System.out.println("Sending message 1");
 		channel.send(message1);
 
 		System.out.println("Sending message 2");
 		channel.send(message2);
 
 		System.out.println("Sending message 3");
 		channel.send(message3);
 
 		System.out.println("Sent all the messages");`
You would see the following o/p:

`Sending message 1
 Sending message 2
 Sending message 3`
 
 But not the last message, Sent all the messages. Because the queue capacity is configured to 2. You could play around with the queue capacity by changing it's value.
 
**Run the application:**

Type in to the console "abc" (example). You would see the text turning to green and it's picked by the consumers and print the same text twice one after the other.
**Note:**
If you're unable to type and view messages in console due to the continuous spring debug log messages, please add the following configuration to your build.gradle file:

`configurations.all {
 	exclude group:"ch.qos.logback", module:"logback-classic"
 }`
 
             
   

 
