package com.yurlov.moysklad_test.adapter.persistance.item;

import com.yurlov.moysklad_test.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
