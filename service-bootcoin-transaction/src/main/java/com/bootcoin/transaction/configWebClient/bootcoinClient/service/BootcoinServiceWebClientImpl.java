package com.bootcoin.transaction.configWebClient.bootcoinClient.service;

import com.bootcoin.transaction.configWebClient.bootcoinClient.dto.BootcoinClient;
import com.bootcoin.transaction.configWebClient.properties.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class BootcoinServiceWebClientImpl implements BootcoinServiceWebClient{

    private final AppConfig properties;
    private final WebClient webClient;

    public BootcoinServiceWebClientImpl(AppConfig properties) {
        this.properties = properties;
        this.webClient = WebClient.create(properties.getUrl());
    }

    @Override
    public Mono<BootcoinClient> findById(String id) {
        log.info("Obteniendo id");
        return webClient.get()
                .uri(properties.getPath().concat("/getById/").concat(id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BootcoinClient.class);
    }
}
