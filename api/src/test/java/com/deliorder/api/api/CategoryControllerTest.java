package com.deliorder.api.api;

import com.deliorder.api.common.dto.ApiResponse;
import com.deliorder.api.api.dto.CategoryData;
import com.deliorder.api.api.dto.CategoryItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("카테고리 목록 조회 성공")
    void getCategoriesSuccess() throws Exception{

        ResponseEntity<ApiResponse<CategoryData>> response =
                restTemplate.exchange(
                        "/api/v1/categories",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<CategoryData>>() {}
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ApiResponse<CategoryData> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.isSuccess()).isTrue();
        assertThat(body.getCode()).isEqualTo("OK");
        assertThat(body.getMessage()).isEqualTo("카테고리 목록 조회 성공");

        List<CategoryItem> categories = body.getData().getCategories();
        assertThat(categories).isNotEmpty();
        assertThat(categories).hasSize(3);
        assertThat(categories.getFirst().getType()).isEqualTo("CHICKEN");
        assertThat(categories.getFirst().getLabel()).isEqualTo("치킨");
    }
}
