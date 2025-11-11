package com.deliorder.api.domain.store.api;

import com.deliorder.api.common.dto.ApiResponse;
import com.deliorder.api.domain.store.api.dto.DeliveryOption;
import com.deliorder.api.domain.store.api.dto.StoreData;
import com.deliorder.api.domain.store.api.dto.StoreDetailData;
import com.deliorder.api.domain.store.api.dto.StoreItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoreControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("GET /api/v1/stores - 가게 목록 조회 성공")
    void getStoresSuccess() throws Exception {
        ResponseEntity<ApiResponse<StoreData>> response = restTemplate.exchange(
                "/api/v1/stores?categoryId=1&sort=RATING_DESC&minOrderPrice=10000",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ApiResponse<StoreData>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ApiResponse<StoreData> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.isSuccess()).isTrue();
        assertThat(body.getCode()).isEqualTo("OK");
        assertThat(body.getMessage()).isEqualTo("가게 목록 조회 성공");

        assertThat(body.getData().getHasNext()).isTrue();

        List<StoreItem> stores = body.getData().getStores();
        assertThat(stores).isNotNull();
        assertThat(stores).isNotEmpty();
        assertThat(stores).hasSize(2);

        assertThat(stores.getFirst().getId()).isEqualTo(1001);
        assertThat(stores.getFirst().getName()).isEqualTo("하이닭");
        assertThat(stores.getFirst().getRating()).isEqualTo(4.9);
        assertThat(stores.getFirst().getDiscount().getAmount()).isEqualTo(3000);
        assertThat(stores.getFirst().getMenus().getFirst().getName()).isEqualTo("하이 반반치킨");
        assertThat(stores.getFirst().getMenus().getFirst().getPrice()).isEqualTo(19500);
    }

    @Test
    @DisplayName("GET /api/v1/stores/{id} - 가게 상세 조회 성공")
    void getStoreDetailSuccess() throws Exception {
        Long STORE_ID = 1002L;
        ResponseEntity<ApiResponse<StoreDetailData>> response =
                restTemplate.exchange(
                        "/api/v1/stores/" + STORE_ID,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<StoreDetailData>>() {
                        }
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ApiResponse<StoreDetailData> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.isSuccess()).isTrue();
        assertThat(body.getCode()).isEqualTo("OK");
        assertThat(body.getMessage()).isEqualTo("가게 상세 조회 성공");

        StoreDetailData data = body.getData();
        assertThat(data.getId()).isEqualTo(STORE_ID);
        assertThat(data.getName()).isEqualTo("롯데리아 남성역점");
        assertThat(data.getRating()).isEqualTo(4.9);
        assertThat(data.getReviewCount()).isEqualTo(690);
        assertThat(data.getMinOrderPrice()).isEqualTo(14000);
        assertThat(data.getDistance()).isEqualTo(0.48);
        assertThat(data.getStoreStatus()).isEqualTo("PREPARING");
        assertThat(data.getStoreStatusLabel()).isEqualTo("준비 중이에요");

        List<String> categories = data.getCategories();
        assertThat(categories).isNotEmpty();
        assertThat(categories).hasSize(3);
        assertThat(categories.getFirst()).isEqualTo("BURGER");

        List<DeliveryOption> deliveryOptions = data.getDeliveryOptions();
        assertThat(deliveryOptions).isNotEmpty();
        assertThat(deliveryOptions).hasSize(3);
        assertThat(deliveryOptions.getFirst().getType()).isEqualTo("STORE");
        assertThat(deliveryOptions.getFirst().getLabel()).isEqualTo("가게배달");
        assertThat(deliveryOptions.getFirst().getOriginalFee()).isEqualTo(1500);
        assertThat(deliveryOptions.getFirst().getDiscountedFee()).isEqualTo(0);
        assertThat(deliveryOptions.getFirst().getIsDiscounted()).isTrue();
    }
}