
package ru.prytkovv.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.prytkovv.demo.model.Product;


@Slf4j
@Service
public class ProductService {

    private final ProductGenerator productGenerator;


    public ProductService(ProductGenerator productGenerator) {
        this.productGenerator = productGenerator;
    }

    @Scheduled(fixedDelay=5000)
    public void createProduct() {
        Product product = productGenerator.next();
        // TODO: message publisher call should be here
        log.debug(String.format("Product %s was sent", product.id()));
    }
}
