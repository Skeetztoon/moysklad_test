package com.yurlov.moysklad_test.adapter.rest.item;

import com.yurlov.moysklad_test.adapter.persistance.item.ItemRepository;
import com.yurlov.moysklad_test.domain.item.Item;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemsController {

    private final ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<List<Item>> getItems(
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

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        Optional<Item> item = itemRepository.findById(id);

        if (item.isEmpty()) {
            throw new ResourceAccessException("id " + id + " not found");
        }
        return ResponseEntity.ok(item.get());
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item, BindingResult bindingResult) {
        validate(bindingResult);

        Item savedItem = itemRepository.save(item);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateItem(@PathVariable int id, @Valid @RequestBody Item item, BindingResult bindingResult) {
        validate(bindingResult);

        if (!itemRepository.existsById(id)) {
            throw new ResourceAccessException("id " + id + " not found");
        }

        item.setId(id);
        itemRepository.save(item);
        return ResponseEntity.ok("Item updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceAccessException("id " + id + " not found");
        }

        itemRepository.deleteById(id);
        return ResponseEntity.ok("Item deleted");
    }

    //////////////////////////

    private void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append("\n");
            }
            throw new ValidationException(errors.toString());
        }
    }
}
