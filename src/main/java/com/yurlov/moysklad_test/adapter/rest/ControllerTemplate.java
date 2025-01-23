package com.yurlov.moysklad_test.adapter.rest;


import com.yurlov.moysklad_test.domain.Delivery;
import com.yurlov.moysklad_test.domain.Item;
import com.yurlov.moysklad_test.domain.Sale;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class ControllerTemplate<T, R> {

    private final JpaRepository<T, Integer> repository;


    @GetMapping
    public ResponseEntity<List<R>> getAll(@RequestParam(required = false) String name,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(required = false) Boolean inStock,
        @RequestParam(defaultValue = "name") String sortBy,
        @RequestParam(defaultValue = "asc") String order,
        @RequestParam(defaultValue = "5") int limit) {

        List<T> items = repository.findAll();
        List<R> dtos = items.stream().map(this::convertToResponse).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<R> getById(@PathVariable int id) {
        Optional<T> item = repository.findById(id);

        if (item.isEmpty()) {
            throw new ResourceAccessException("id " + id + " not found");
        }

        R dto = convertToResponse(item.get());

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<R> create(@Valid @RequestBody T item, BindingResult bindingResult) {
        validate(bindingResult);

        T savedItem = repository.save(item);

        R dto = convertToResponse(savedItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<R> update(@PathVariable int id, @Valid @RequestBody T item, BindingResult bindingResult) {
        validate(bindingResult);

        if (!repository.existsById(id)) {
            throw new ResourceAccessException("id " + id + " not found");
        }

        setId(item, id);
        T savedItem = repository.save(item);
        R dto = convertToResponse(savedItem);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (!repository.existsById(id)) {
            throw new ResourceAccessException("id " + id + " not found");
        }

        repository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    //////////////////////////

    protected abstract R convertToResponse(T item);

    protected void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append("\n");
            }
            throw new ValidationException(errors.toString());
        }
    }

    private void setId(T item, int id) {
        if (item instanceof Delivery) {
            ((Delivery) item).setId(id);
        } else if (item instanceof Sale) {
            ((Sale) item).setId(id);
        } else if (item instanceof Item) {
            ((Item) item).setId(id);
        }
    }
}
