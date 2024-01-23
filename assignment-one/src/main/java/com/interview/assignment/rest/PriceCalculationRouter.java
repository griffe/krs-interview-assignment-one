package com.interview.assignment.rest;


import com.interview.assignment.calculation.PriceCalculationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class PriceCalculationRouter {

    @Bean
    public RouterFunction<ServerResponse> route(PriceCalculationHandler priceCalculationHandler) {
        return RouterFunctions
                .route(GET("/calculate/{productId}/{amount}/").and(accept(MediaType.APPLICATION_JSON)),
                        priceCalculationHandler::calculate);
    }
}
