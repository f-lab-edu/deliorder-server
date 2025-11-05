package com.deliorder.api.domain.store.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/stores - 가게 목록 조회 성공")
    void getStoresSuccess() throws Exception {
        String requestPath = "/stores?categoryId=1&sort=RATING_DESC&minOrderPrice=10000";

        mockMvc.perform(get(requestPath)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("가게 목록 조회 성공"))

                .andExpect(jsonPath("$.data.stores.length()").value(2))
                .andExpect(jsonPath("$.data.hasNext").value(true))

                .andExpect(jsonPath("$.data.stores[0].id").value(1001))
                .andExpect(jsonPath("$.data.stores[0].name").value("하이닭"))
                .andExpect(jsonPath("$.data.stores[0].rating").value(4.9))
                .andExpect(jsonPath("$.data.stores[0].discount.amount").value(3000))

                .andExpect(jsonPath("$.data.stores[0].menus[0].name").value("하이 반반치킨"));
    }
}