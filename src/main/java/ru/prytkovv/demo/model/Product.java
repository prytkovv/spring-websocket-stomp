package ru.prytkovv.demo.model;

import java.time.OffsetDateTime;
import java.util.UUID;


public record Product(UUID id,
                      String imagePath,
                      String barcode,
                      Integer categoryId,
                      Integer manufacturerId,
                      OffsetDateTime manufacturedAt,
                      OffsetDateTime createdAt) {
}
