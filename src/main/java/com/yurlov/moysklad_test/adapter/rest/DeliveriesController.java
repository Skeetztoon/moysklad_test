package com.yurlov.moysklad_test.adapter.rest;

import com.yurlov.moysklad_test.adapter.persistance.DeliveryRepository;
import com.yurlov.moysklad_test.app.ItemService;
import com.yurlov.moysklad_test.domain.Delivery;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
public class DeliveriesController extends ControllerTemplate<Delivery> {

    private final DeliveryRepository deliveryRepository;
    private final ItemService itemService;

    public DeliveriesController(DeliveryRepository deliveryRepository, ItemService itemService) {
        super(deliveryRepository);
        this.deliveryRepository = deliveryRepository;
        this.itemService = itemService;
    }

    @Override
    @PostMapping
    @Transactional
    public ResponseEntity<Delivery> create(@Valid @RequestBody Delivery item, BindingResult bindingResult) {
        validate(bindingResult);

        Delivery delivery = deliveryRepository.save(item);
        itemService.addQuantity(delivery.getItem().getId(), item.getQuantity());

        return ResponseEntity.status(HttpStatus.CREATED).body(delivery);
    }
}
