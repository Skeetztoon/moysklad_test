package com.yurlov.moysklad_test.app;

import com.yurlov.moysklad_test.adapter.persistance.ItemRepository;
import com.yurlov.moysklad_test.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void addQuantity(Integer itemId, Integer quantity) {
        Item item = getItem(itemId);
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
            itemRepository.save(item);
        }
    }

    @Transactional
    public void subtractQuantity(Integer itemId, Integer quantity) {
        Item item = getItem(itemId);
        if (item != null) {
            if (item.getQuantity()-quantity<0) {
                throw new NegativeArraySizeException("Невозможная операция, количество товара не может быть меньше нуля");
            }
            item.setQuantity(item.getQuantity() - quantity);
            itemRepository.save(item);
        }
    }

    ///////////////////////
    public Item getItem(Integer itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        return item.orElse(null);
    }
}
