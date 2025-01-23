package com.yurlov.moysklad_test.adapter.rest.delivery;

import lombok.Data;

@Data
public class DeliveryDTO {
    private Integer id;
    private String title;
    private Integer itemId;
    private Integer quantity;
}
