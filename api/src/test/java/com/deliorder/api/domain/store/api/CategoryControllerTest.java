package com.deliorder.api.domain.store.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("카테고리 목록 조회 성공")
    void getCategoriesSuccess() throws Exception{
        mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("카테고리 목록 조회 성공"))
                .andExpect(jsonPath("$.data.categories.length()").value(3))
                .andExpect(jsonPath("$.data.categories[0].type").value("CHICKEN"))
                .andExpect(jsonPath("$.data.categories[0].label").value("치킨"));
    }
}