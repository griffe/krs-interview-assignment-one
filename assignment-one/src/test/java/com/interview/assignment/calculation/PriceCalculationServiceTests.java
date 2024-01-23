package com.interview.assignment.calculation;

import com.interview.assignment.product.Product;
import com.interview.assignment.product.ProductRepository;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
@Import(PriceCalculationServiceTests.ProductRepositoryTestConfig.class)
@ActiveProfiles("stub")
class PriceCalculationServiceTests {


    @Autowired
    PriceCalculationService priceCalculationService;

    @Test
    void validatesAmount() {
        Either<CalculationFailure, CalculatedPrice> result = priceCalculationService.calculatePrice(
                new PriceCalculationDto(UUID.randomUUID().toString(), "FAIL", Optional.of(DiscountCriteria.PERCENTAGE), Optional.of("1")));

        Assertions.assertInstanceOf(CalculationFailure.class, result.getLeft());
    }

    @TestConfiguration
    public static class ProductRepositoryTestConfig {
        @Bean
        @Profile("stub")
        ProductRepository repository() {
            return productId -> Optional.of(new Product(productId, new BigDecimal("543")));
        }
    }
}

