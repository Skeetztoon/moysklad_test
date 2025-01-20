package com.yurlov.moysklad_test.rest;

import com.yurlov.moysklad_test.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ItemsControllerTest {

    @Autowired
    private ItemsController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        controller.getItems().clear();

        controller.getItems().add(new Item("Item 1", "Desc 1", 1.0, true));
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/items")).andExpect(status().isOk());
    }

    @Test
    void testGetOne() throws Exception {
        mockMvc.perform(get("/items/0"))
            .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Item 1"));
    }

    @Test
    void testCreate() throws Exception {
        String json = "{\"name\":\"test\",\"description\":\"test\",\"price\": 10.0}";

        mockMvc.perform(post("/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("test"));
    }

    @Test
    void testUpdate() throws Exception {
        String json = "{\"name\":\"updated item\",\"description\":\"test\",\"price\": 10.0}";

        mockMvc.perform(put("/items/0")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk()
            );
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/items/0"))
            .andExpect(status().isOk());
    }
}
