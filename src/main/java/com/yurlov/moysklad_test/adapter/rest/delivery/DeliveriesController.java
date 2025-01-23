package com.yurlov.moysklad_test.adapter.rest.delivery;

import com.yurlov.moysklad_test.adapter.persistance.DeliveryRepository;
import com.yurlov.moysklad_test.adapter.rest.ControllerTemplate;
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
public class DeliveriesController extends ControllerTemplate<Delivery, DeliveryDTO> {

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
    public ResponseEntity<DeliveryDTO> create(@Valid @RequestBody Delivery item, BindingResult bindingResult) {
        validate(bindingResult);

        itemService.addQuantity(item.getItem().getId(), item.getQuantity());

        Delivery delivery = deliveryRepository.save(item);

        DeliveryDTO dto = convertToResponse(delivery);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    //////////////////

    @Override
    protected DeliveryDTO convertToResponse(Delivery delivery) {
        DeliveryDTO deliveryDTO = new DeliveryDTO();
        deliveryDTO.setId(delivery.getId());
        deliveryDTO.setTitle(delivery.getTitle());
        deliveryDTO.setItemId(delivery.getItem().getId());
        deliveryDTO.setQuantity(delivery.getQuantity());
        return deliveryDTO;
    }
}
