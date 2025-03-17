package ru.prytkovv.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.prytkovv.demo.model.Product;

import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {

    @JsonProperty("id")
    UUID id;
    @JsonProperty("barcode")
    String barcode;
    @JsonProperty("category")
    Integer categoryId;
    @JsonProperty("manufacturer")
    Integer manufacturerId;
    @JsonProperty("manufactured_at")
    OffsetDateTime manufacturedAt;

    public static ProductDto of(Product in) {
        ProductDto out = new ProductDto();
        out.setId(in.id());
        out.setBarcode(in.barcode());
        out.setCategoryId(in.categoryId());
        out.setManufacturerId(in.manufacturerId());
        out.setManufacturedAt(in.manufacturedAt());
        return out;
    }
}
