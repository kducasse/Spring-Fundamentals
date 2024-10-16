package com.orielly.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AppConfig {

    @Bean               //The method is the bean name!
    public RestTemplate astroRestTemplate(RestTemplateBuilder builder,
                                          @Value("${astro.baseUrl}") String baseUrl) { // @Value is used to inject external values into beans.
                                                                                       // Look at /resources/application.properties
        return builder.rootUri("http://api.open-notify.org").build();
    }

    @Bean
    public RestTemplate anotherRestTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("https://other.html").build();
    }

    // Using the AstroInterface, you do not need the AstroService anymore
    // This is the least amount of work
    @Bean
    AstroInterface astroInterface(@Value("${astro.baseUrl}") String baseUrl) {
        WebClient webClient = WebClient.create(baseUrl);
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(AstroInterface.class);
    }
}
