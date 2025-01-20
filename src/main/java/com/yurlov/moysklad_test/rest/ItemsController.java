package com.yurlov.moysklad_test.rest;

import com.yurlov.moysklad_test.domain.Item;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
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
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemsController {

    private List<Item> items= new ArrayList<>();

    @GetMapping
    public List<Item> getItems() {
        return items;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        isPresent(id);
        return ResponseEntity.ok(items.get(id));
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item, BindingResult bindingResult) {
        validate(bindingResult);

        items.add(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateItem(@PathVariable int id, @Valid @RequestBody Item item, BindingResult bindingResult) {
        isPresent(id);
        validate(bindingResult);

        items.set(id, item);
        return ResponseEntity.ok("Item updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        isPresent(id);

        items.remove(id);
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

    private void isPresent(int id) {
        if (id < 0 || id >= items.size()) {
            throw new ResourceAccessException("id " + id + " not found");
        }
    }
}
