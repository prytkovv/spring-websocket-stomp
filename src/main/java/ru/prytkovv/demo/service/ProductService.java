
package ru.prytkovv.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.prytkovv.demo.dto.ProductDto;
import ru.prytkovv.demo.messaging.ProductMessagePublisher;
import ru.prytkovv.demo.model.Product;


@Slf4j
@Service
public class ProductService {

    private final ProductGenerator productGenerator;
    private final ProductMessagePublisher productMessagePublisher;


    public ProductService(ProductGenerator productGenerator, ProductMessagePublisher productMessagePublisher) {
        this.productGenerator = productGenerator;
        this.productMessagePublisher = productMessagePublisher;
    }

    @Scheduled(fixedDelay=5000)
    public void createProduct() {
        Product product = productGenerator.next();
        this.productMessagePublisher.publish(ProductDto.of(product));
        log.debug(String.format("Product %s was sent", product.id()));
    }
}
