package com.spring_web_flux.tutorial.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.time.Duration;

@RestController
public class SampleController {
    @GetMapping("/sample1")
    Mono<String> sample1() {
        return Mono.just("Hello world");
    }

    @GetMapping(value = "/sample2", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    Flux<Integer> sample2() {
        return Flux.just(1,2,3,4,5,6,7,8).delayElements(Duration.ofSeconds(10)).log();
    }
}
