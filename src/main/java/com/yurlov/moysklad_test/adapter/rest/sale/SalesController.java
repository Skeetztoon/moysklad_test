package com.yurlov.moysklad_test.adapter.rest.sale;

import com.yurlov.moysklad_test.adapter.persistance.SaleRepository;
import com.yurlov.moysklad_test.adapter.rest.ControllerTemplate;
import com.yurlov.moysklad_test.app.ItemService;
import com.yurlov.moysklad_test.domain.Sale;
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
@RequestMapping("/sales")
public class SalesController extends ControllerTemplate<Sale, SaleDTO> {

    private final SaleRepository saleRepository;
    private final ItemService itemService;

    public SalesController(SaleRepository service, ItemService itemService) {
        super(service);
        this.saleRepository = service;
        this.itemService = itemService;
    }

    @Override
    @PostMapping
    @Transactional
    public ResponseEntity<SaleDTO> create(@Valid @RequestBody Sale item, BindingResult bindingResult) {
        validate(bindingResult);

        itemService.subtractQuantity(item.getItem().getId(), item.getQuantity());

        Sale sale = saleRepository.save(item);

        SaleDTO saleDTO = convertToResponse(sale);

        return ResponseEntity.status(HttpStatus.CREATED).body(saleDTO);
    }

    //////////////////

    @Override
    protected SaleDTO convertToResponse(Sale sale) {
        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setId(sale.getId());
        saleDTO.setTitle(sale.getTitle());
        saleDTO.setItemId(sale.getItem().getId());
        saleDTO.setQuantity(sale.getQuantity());
        saleDTO.setPrice(sale.getPrice());
        return saleDTO;
    }
}
