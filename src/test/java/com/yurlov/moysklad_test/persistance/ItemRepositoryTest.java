package com.yurlov.moysklad_test.persistance;

import com.yurlov.moysklad_test.adapter.persistance.item.ItemRepository;
import com.yurlov.moysklad_test.domain.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    public void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void findByNameTest() {
        Item item1 = new Item(null, "Монитор", "Описание 1", 10.0, true);
        Item item2 = new Item(null, "Стул", "Описание 2", 20.0, true);
        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> items = itemRepository.findByFilters("Монитор", null, null, null, Pageable.unpaged());

        assertThat(items.size()).isEqualTo(1);
        assertThat(items.get(0).getName()).isEqualTo("Монитор");
    }

    @Test
    void findByPriceRangeTest() {
        Item item1 = new Item(null, "Монитор", "Описание 1", 10.0, true);
        Item item2 = new Item(null, "Стул", "Описание 2", 20.0, true);
        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> items = itemRepository.findByFilters(null, 15.0, null, null, Pageable.unpaged());

        assertThat(items.size()).isEqualTo(1);
        assertThat(items.get(0).getName()).isEqualTo("Стул");
    }

    @Test
    void sortByPriceTest() {
        Item item1 = new Item(null, "Монитор", "Описание 1", 10.0, true);
        Item item2 = new Item(null, "Стул", "Описание 2", 20.0, true);
        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> items = itemRepository.findByFilters(null, null, null, null, PageRequest.of(0, 10, Sort.by("price")));

        assertThat(items.get(0).getName()).isEqualTo("Монитор");
    }
}

