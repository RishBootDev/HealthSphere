package org.rishbootdev.healthsphere.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public ChatClient getChatClient(ChatClient.Builder builder){
        return builder.build();
    }


    @Bean
    public <T>Logger getLogger(Class<T> clazz){
        return LoggerFactory.getLogger(clazz);
    }
}
