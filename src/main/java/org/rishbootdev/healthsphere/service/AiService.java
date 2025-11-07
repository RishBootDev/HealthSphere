package org.rishbootdev.healthsphere.service;


import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AiService {

    private ChatClient chatClient;

    public Flux<String> getResponseForPatient(String query){
        String prompt = "You are a highly experienced Medicine and HealthCare specialist who have the complete knowledge of each and every diseases. "
                + "I have a query: " + query
                + " Provide relevant details regarding type of probem or disease i am facing, care and medicines i need to take and create a local prescription, "
                + "with dosages, time should be given to rest ,etc and other related stuffs.Output should be text only and not more than 200 words";

        return chatClient.prompt(prompt)
                .stream()
                .content()
                .flatMap(str -> Flux.fromIterable(()->str.chars()
                        .mapToObj(c->String.valueOf((char)c))
                        .iterator()))
                .concatMap(ch -> Mono.just(ch)
                        .delayElement(Duration.ofMillis(10)));

    }

    public Flux<String> getResponseForPharmaCist(String query){
        String prompt = "You are a highly experienced Medicine and HealthCare specialist who have the complete knowledge of each and every diseases and every medicine associated with the cure "
                + "I have a query: " + query
                + " Provide relevant details regarding type of medications, care and pharma related stuffs.Output should be text only and not more than 200 words";

        return chatClient.prompt(prompt)
                .stream()
                .content()
                .flatMap(str -> Flux.fromIterable(()->str.chars()
                        .mapToObj(c->String.valueOf((char)c))
                        .iterator()))
                .concatMap(ch -> Mono.just(ch)
                        .delayElement(Duration.ofMillis(10)));
    }
}
