package com.interview.assignment.calculation;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@Log4j2
public class PriceCalculationHandler {

    public static final String PRODUCT_ID_VARIABLE = "productId";
    public static final String AMOUNT_VARIABLE = "amount";
    public static final String DISCOUNT_TYPE_PARAM = "discountType";
    public static final String DISCOUNT_PERCENTAGE_PARAM = "discountPercentage";

    private final PriceCalculationService priceCalculationService;

    public PriceCalculationHandler(PriceCalculationService priceCalculationService) {
        this.priceCalculationService = priceCalculationService;
    }

    public Mono<ServerResponse> calculate(ServerRequest request) {
        String productId = request.pathVariable(PRODUCT_ID_VARIABLE);
        String amount = request.pathVariable(AMOUNT_VARIABLE);
        Optional<String> discountPolicyType = request.queryParam(DISCOUNT_TYPE_PARAM);
        Optional<String> discountPercentage = request.queryParam(DISCOUNT_PERCENTAGE_PARAM);
        PriceCalculationDto calculationRequest = new PriceCalculationDto(productId, amount, discountPolicyType, discountPercentage);

        Either<CalculationFailure, CalculatedPrice> calculationResult = priceCalculationService.calculatePrice(calculationRequest);

        return calculationResult.map(this::successResponse)
                .getOrElseGet(this::errorResponse);
    }

    private Mono<ServerResponse> successResponse(CalculatedPrice calculatedPrice) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(calculatedPrice));
    }

    private Mono<ServerResponse> errorResponse(CalculationFailure calculationFailure) {
        return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(calculationFailure.getErrors()));
    }
}
