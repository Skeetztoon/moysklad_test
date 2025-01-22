package com.yurlov.moysklad_test.adapter.rest;

import com.yurlov.moysklad_test.adapter.persistance.ItemRepository;
import com.yurlov.moysklad_test.domain.Item;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemsController extends ControllerTemplate<Item>{

    private final ItemRepository itemRepository;

    public ItemsController(final ItemRepository itemRepository) {
        super(itemRepository);
        this.itemRepository = itemRepository;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Item>> getAll(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(required = false) Boolean inStock,
        @RequestParam(defaultValue = "name") String sortBy,
        @RequestParam(defaultValue = "asc") String order,
        @RequestParam(defaultValue = "5") int limit) {

        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        if (order.equals("desc")) {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        }
        Pageable pageable = PageRequest.of(0, limit, sort);

        List<Item> items = itemRepository.findByFilters(name, minPrice, maxPrice, inStock, pageable);

        return ResponseEntity.ok(items);
    }
}
