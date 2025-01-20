package com.yurlov.moysklad_test.domain;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @NotBlank(message = "Названние обязательно")
    @Size(max = 255, message = "Максимальная длина названия - 255 симоволов")
    private String name;

    @Size(max = 4096, message = "Максимальная длина описания - 4096 симоволов")
    private String description;

    @DecimalMin(value = "0.0", message = "Цена не может быть меньше нуля")
    private double price;

    private boolean inStock = false;

}
