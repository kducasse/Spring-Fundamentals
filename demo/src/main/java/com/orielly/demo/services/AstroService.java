package com.orielly.demo.services;

import com.orielly.demo.json.AstroResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class AstroService {
    private final RestTemplate template;
    private final WebClient webClient;

    public AstroService(@Qualifier("astroRestTemplate") RestTemplate template) {
        this.template = template;
        this.webClient = WebClient.create("http://api.open-notify.org");
    }

    public String getPeopleInSpace() {
        return this.template.getForObject("/astros.json", String.class);
    }

    public AstroResponse getAstroResponse() {
        return this.template.getForObject("/astros.json", AstroResponse.class);
    }

    public AstroResponse getAstroResponseAsync() {
        return this.webClient.get()
                .uri("/astros.json")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AstroResponse.class)
                .block(Duration.ofSeconds(10));
    }
}
