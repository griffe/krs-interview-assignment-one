package com.interview.assignment.calculation;

import com.interview.assignment.product.Product;
import com.interview.assignment.product.ProductRepository;
import io.vavr.control.Either;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PriceCalculationService {
    private static final Logger logger = LogManager.getLogger(PriceCalculationService.class);

    private final ProductRepository productRepository;
    private final DiscountPoliciesRegistry discountPoliciesRegistry;

    PriceCalculationService(ProductRepository productRepository, DiscountPoliciesRegistry discountPoliciesRegistry) {
        this.productRepository = productRepository;
        this.discountPoliciesRegistry = discountPoliciesRegistry;
    }

    private static List<String> validateArguments(PriceCalculationDto calculationRequest) {
        List<String> errors = new ArrayList<>();
        if (!calculationRequest.amount().matches("\\d+")) {
            errors.add("Invalid amount");
        }
        if (!calculationRequest.productId().matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            errors.add("Wrong product identifier format");
        }
        return errors;
    }

    private static Either<CalculationFailure, CalculatedPrice> calculateWithDiscount(PriceCalculationDto calculationRequest, DiscountCalculator discountCalculator, Product product) {
        return discountCalculator.calculate(new PriceCalculationData(product.price(), new BigDecimal(calculationRequest.amount())), PriceCalculationData.AdditionalData.from(calculationRequest));
    }

    public Either<CalculationFailure, CalculatedPrice> calculatePrice(PriceCalculationDto calculationRequest) {
        List<String> errors = validateArguments(calculationRequest);
        if (!errors.isEmpty()) {
            return Either.left(new CalculationFailure(errors));
        }

        // TODO question - bad request if wrong data
        return discountPoliciesRegistry.getPolicy(new DiscountCriteria(calculationRequest.discountPolicyType().orElse(DiscountCriteria.UNKNOWN)))
                .map(discountCalculator ->
                        productRepository.findProduct(UUID.fromString(calculationRequest.productId()))
                                .map(product ->
                                        calculateWithDiscount(calculationRequest, discountCalculator, product)
                                )
                                .orElseGet(() -> Either.left(new CalculationFailure("Product not found")))
                ).orElseGet(() -> Either.left(new CalculationFailure("Unrecognized discount policy")));
    }

}