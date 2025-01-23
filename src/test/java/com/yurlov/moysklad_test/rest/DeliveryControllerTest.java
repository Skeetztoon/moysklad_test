package com.yurlov.moysklad_test.rest;

import com.yurlov.moysklad_test.adapter.persistance.DeliveryRepository;
import com.yurlov.moysklad_test.adapter.persistance.ItemRepository;
import com.yurlov.moysklad_test.adapter.rest.delivery.DeliveriesController;
import com.yurlov.moysklad_test.domain.Delivery;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DeliveryControllerTest {

    private static final String BASE_URI = "/deliveries";
    private static final String FIRST_URI = "/deliveries/1";

    @Autowired
    private DeliveriesController controller;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

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

        Delivery delivery = new Delivery(null,"Delivery 1", item, 10);

        deliveryRepository.save(delivery);
    }

    private void resetAutoIncrement() {
        String sql = "ALTER SEQUENCE item_id RESTART WITH 1";
        String sql1 = "ALTER SEQUENCE delivery_id RESTART WITH 1";
        entityManager.createNativeQuery(sql).executeUpdate();
        entityManager.createNativeQuery(sql1).executeUpdate();
    }



    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(BASE_URI))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("Delivery 1"));
    }

    @Test
    void testGetOne() throws Exception {
        mockMvc.perform(get(FIRST_URI))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Delivery 1"))
            .andExpect(jsonPath("$.itemId").value("1"));
    }


    @Test
    void testCreate() throws Exception {
        String json = "{\"title\": \"Test delivery\",\"item\": {\"id\": 1},\"quantity\": 5}";

        mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("Test delivery"));
    }

    @Test
    void testUpdate() throws Exception {

        String json = "{\"title\":\"Updated delivery\",\"item\": {\"id\": 1},\"quantity\": 5}";

        mockMvc.perform(put(FIRST_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated delivery"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(FIRST_URI))
            .andExpect(status().isOk());
    }
}
