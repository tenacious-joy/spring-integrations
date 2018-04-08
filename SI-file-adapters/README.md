The example focuses on sending files from a producer to consumer. Spring integration provides a lot of out-of-the-box adapters. We could leverage file adapter from SI components.

Adapters act as a bridge between spring integration and outside applications / services.
It provides separation of concerns wherever it's necessary. it abstracts away the JMS / JDBC implementations.

Some of the adapters are:
1. Stream Adapters (Refer SI-stream-adapters example)
2. File Adapters
3. JMS Adapters
4. JDBC Adapters
5. Mongo DB Adapters

Let's discuss how can we configure file adapters. Every adapter has inbound and outbound adapters. Inbound adapters are used to send information to channels and outbound adapters take information out of the channel and send it to external world (Databases, queues, files, console etc..)

**Write contents from console (standard-input-stream) to a file**

A. Add the following gradle dependency to download file-adapter jars.

`compile ('org.springframework.integration:spring-integration-file')`

All other gradle configurations are similar to SI-stream-adapter example.

B. Include int-file spring integration namespace to bean xml configuration. And also specify bean locations as xsd.

`xmlns:int-file="http://www.springframework.org/schema/integration/file"
xsi:schemaLocation="http://www.springframework.org/schema/integration/file http://www.springframework.org/schema/integration/file/spring-integration-file.xsd"`

C. Include outbound adapter in your configuration as below:

`<int-file:outbound-channel-adapter id="consumer-file-adapter" channel="messageChannel"
                                        auto-create-directory="true" directory="file:${user.dir}/output"/>` 
                                        
auto-create-directory property would create directory if the mentioned directory is not found.${user.dir} would point to the root directory of the gradle project. Here it's SI-file-adapters.

D. You could include stdin-stream-adapter as an inbound adapter. Add necessary poller and channel bean configuration as well.

E. Run the application. Write anything to the console. A directory called output will be created from the root of the project. Inside it, you could find a msg file. Just open it to view the contents that you had written to the console.

**Send files from producer to consumer**
 Replace int-stream:stdin-channel-adapter with the inbound-file-adapter as below:
 
 `<int-file:inbound-channel-adapter directory="file:${user.dir}/input" auto-create-directory="true"
                                        id="producer-file-adapter" channel="messageChannel" prevent-duplicates="true">`
                                        
Create any file inside the mentioned directory and restart the application. You could find the same file inside your output directory.

For every 2 seconds, inbound adapter polls input file directory for new files and send those to the channel. 

