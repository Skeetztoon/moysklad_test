package com.yurlov.moysklad_test.adapter.rest.sale;

import lombok.Data;

@Data
public class SaleDTO {
    private Integer id;
    private String title;
    private Integer itemId;
    private Integer quantity;
    private double price;
}
