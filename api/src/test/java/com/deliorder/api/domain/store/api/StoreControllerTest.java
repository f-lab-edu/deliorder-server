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

    @Test
    @DisplayName("GET /api/v1/stores/{id} - 가게 상세 조회 성공")
    void getStoreDetailSuccess() throws Exception {

        Long STORE_ID = 1002L;

        mockMvc.perform(get("/stores/{id}", STORE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("가게 상세 조회 성공"))

                .andExpect(jsonPath("$.data.id").value(STORE_ID))
                .andExpect(jsonPath("$.data.name").value("롯데리아 남성역점"))
                .andExpect(jsonPath("$.data.rating").value(4.9))
                .andExpect(jsonPath("$.data.storeStatus").value("PREPARING"))

                .andExpect(jsonPath("$.data.categories.length()").value(3))
                .andExpect(jsonPath("$.data.categories[0]").value("BURGER"))

                .andExpect(jsonPath("$.data.deliveryOptions.length()").value(3))
                .andExpect(jsonPath("$.data.deliveryOptions[0].type").value("STORE"))
                .andExpect(jsonPath("$.data.deliveryOptions[0].discountedFee").value(0))
                .andExpect(jsonPath("$.data.deliveryOptions[0].badge").value("배민클럽은 무료배달"))
                .andExpect(jsonPath("$.data.deliveryOptions[2].badge").doesNotExist());
    }
}