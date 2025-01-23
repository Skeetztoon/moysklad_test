package com.yurlov.moysklad_test.rest;

import com.yurlov.moysklad_test.adapter.persistance.ItemRepository;
import com.yurlov.moysklad_test.adapter.rest.item.ItemsController;
import com.yurlov.moysklad_test.domain.Item;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ItemsControllerTest {

    private static final String BASE_URI = "/items";
    public static final String FIRST_URI = "/items/1";

    @Autowired
    private ItemsController controller;

    @Autowired
    private ItemRepository itemRepository;

    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        itemRepository.deleteAll();

        resetAutoIncrement();

        Item item = new Item();
        item.setName("Item 1");
        item.setDescription("Desc 1");
        item.setPrice(100.0);

        itemRepository.save(item);
    }

    private void resetAutoIncrement() {
        String sql = "ALTER SEQUENCE item_id RESTART WITH 1";
        entityManager.createNativeQuery(sql).executeUpdate();
    }


    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(BASE_URI))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Item 1"));
    }

    @Test
    void testGetOne() throws Exception {
        mockMvc.perform(get(FIRST_URI))
            .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Item 1"));
    }

    @Test
    void testCreate() throws Exception {
        String json = "{\"name\":\"test\",\"description\":\"test\",\"price\": 10.0}";

        mockMvc.perform(post(BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("test"));
    }

    @Test
    void testUpdate() throws Exception {
        String json = "{\"name\":\"updated item\",\"description\":\"test\",\"price\": 10.0}";

        mockMvc.perform(put(FIRST_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("updated item"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(FIRST_URI))
            .andExpect(status().isOk());
    }
}
