package com.bootcoin.transaction.routes;

import com.bootcoin.transaction.handler.PurchaseTransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouteConfig {

    @Bean
    public RouterFunction<ServerResponse> routes (PurchaseTransactionHandler handler) {
        return route(GET("/purchaseTransaction"), handler::findAll)
                .andRoute(GET("/purchaseTransaction/{id}"), handler::findId)
                .andRoute(POST("/purchaseTransaction"),handler::create)
                .andRoute(POST("/approveTransaction"),handler::approveTransaction);
    }
}
