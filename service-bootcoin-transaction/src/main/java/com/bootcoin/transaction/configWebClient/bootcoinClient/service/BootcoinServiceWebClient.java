package com.bootcoin.transaction.configWebClient.bootcoinClient.service;

import com.bootcoin.transaction.configWebClient.bootcoinClient.dto.BootcoinClient;
import reactor.core.publisher.Mono;

public interface BootcoinServiceWebClient {

    Mono<BootcoinClient> findById(String id);
}
