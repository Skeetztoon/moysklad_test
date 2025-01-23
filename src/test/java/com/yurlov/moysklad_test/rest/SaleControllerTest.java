package com.yurlov.moysklad_test.rest;

import com.yurlov.moysklad_test.adapter.persistance.ItemRepository;
import com.yurlov.moysklad_test.adapter.persistance.SaleRepository;
import com.yurlov.moysklad_test.adapter.rest.sale.SalesController;
import com.yurlov.moysklad_test.domain.Item;
import com.yurlov.moysklad_test.domain.Sale;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import org.junit.Assert;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SaleControllerTest {

    private static final String BASE_URI = "/sales";
    private static final String FIRST_URI = "/sales/1";

    private static final String TITLE = "Sale 1";

    @Autowired
    private SalesController controller;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SaleRepository saleRepository;

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
        item.setQuantity(30);

        itemRepository.save(item);

        Sale sale = new Sale(null,TITLE, item, 10, 100.0);

        saleRepository.save(sale);
    }

    private void resetAutoIncrement() {
        String sql = "ALTER SEQUENCE item_id RESTART WITH 1";
        String sql1 = "ALTER SEQUENCE sale_id RESTART WITH 1";
        entityManager.createNativeQuery(sql).executeUpdate();
        entityManager.createNativeQuery(sql1).executeUpdate();
    }



    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(BASE_URI))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value(TITLE));
    }

    @Test
    void testGetOne() throws Exception {
        mockMvc.perform(get(FIRST_URI))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(TITLE))
            .andExpect(jsonPath("$.itemId").value("1"));
    }


    @Test
    void testCreate() throws Exception {
        String json = "{\"title\": \"Test sale\",\"item\": {\"id\": 1},\"quantity\": 5,\"price\":20.0}";

        mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("Test sale"));
    }

    @Test
    void testCreateExpectError() throws Exception {
        String json = "{\"title\": \"Test sale\",\"item\": {\"id\": 1},\"quantity\": 100,\"price\":20.0}";

        Assert.assertThrows(ServletException.class, () -> {
            mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        });
    }

    @Test
    void testUpdate() throws Exception {

        String json = "{\"title\":\"Updated sale\",\"item\": {\"id\": 1},\"quantity\": 5,\"price\":20.0}";

        mockMvc.perform(put(FIRST_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated sale"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(FIRST_URI))
            .andExpect(status().isOk());
    }
}
