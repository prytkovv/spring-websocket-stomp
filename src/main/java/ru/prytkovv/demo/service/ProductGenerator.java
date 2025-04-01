package ru.prytkovv.demo.service;

import org.springframework.stereotype.Service;
import ru.prytkovv.demo.model.Product;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@Service
public class ProductGenerator {

    private static final List<Product> PRODUCTS;
    static {
        PRODUCTS = List.of(
                new Product(UUID.fromString("11111111-1111-1111-1111-111111111111"), "/images/product1.jpg", "1234567890123", 1, 10,
                        OffsetDateTime.of(2024, 1, 10, 12, 0, 0, 0, ZoneOffset.UTC),
                        OffsetDateTime.of(2024, 2, 1, 8, 30, 0, 0, ZoneOffset.UTC)),

                new Product(UUID.fromString("22222222-2222-2222-2222-222222222222"), "/images/product2.jpg", "9876543210987", 2, 15,
                        OffsetDateTime.of(2023, 11, 5, 14, 45, 0, 0, ZoneOffset.UTC),
                        OffsetDateTime.of(2023, 12, 20, 10, 15, 0, 0, ZoneOffset.UTC)),

                new Product(UUID.fromString("33333333-3333-3333-3333-333333333333"), "/images/product3.jpg", "5555555555555", 3, 20,
                        OffsetDateTime.of(2023, 9, 15, 9, 0, 0, 0, ZoneOffset.UTC),
                        OffsetDateTime.of(2023, 10, 5, 16, 45, 0, 0, ZoneOffset.UTC)),

                new Product(UUID.fromString("44444444-4444-4444-4444-444444444444"), "/images/product4.jpg", "1111222233334", 4, 25,
                        OffsetDateTime.of(2022, 6, 25, 18, 30, 0, 0, ZoneOffset.UTC),
                        OffsetDateTime.of(2022, 7, 15, 20, 0, 0, 0, ZoneOffset.UTC)),

                new Product(UUID.fromString("55555555-5555-5555-5555-555555555555"), "/images/product5.jpg", "9999888877776", 5, 30,
                        OffsetDateTime.of(2021, 3, 10, 7, 15, 0, 0, ZoneOffset.UTC),
                        OffsetDateTime.of(2021, 4, 5, 12, 0, 0, 0, ZoneOffset.UTC))
        );
    }
    private final Random random = new Random();

    public Product next() {
        int pos = random.nextInt(PRODUCTS.size());
        return PRODUCTS.get(pos);
    }
}
