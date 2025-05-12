package kz.dzhumanov.sportshop.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class AddToCartRequest {

    @NotNull(message = "Product не может быть null")
    private Long productId;

    @Min(value = 1, message = "quantity должно быть минимум 1")
    private int quantity;

}
