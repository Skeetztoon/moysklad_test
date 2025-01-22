package com.yurlov.moysklad_test.adapter.rest;

import com.yurlov.moysklad_test.adapter.persistance.SaleRepository;
import com.yurlov.moysklad_test.domain.Sale;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
public class SalesController extends ControllerTemplate<Sale>{

    public SalesController(SaleRepository service) {
        super(service);
    }
}
