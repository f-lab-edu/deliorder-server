package com.deliorder.api.service;

import com.deliorder.api.entity.*;
import com.deliorder.api.store.entity.*;
import com.deliorder.api.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Test
    @DisplayName("가게 상세 정보 조회 - 성공")
    public void findStore() throws Exception {
        //given
        List<DeliveryOption> deliveryOptions = List.of(
                DeliveryOption.builder()
                        .type(DeliveryType.STORE)
                        .label("가게배달")
                        .originalFee(1500)
                        .discountedFee(0)
                        .build(),
                DeliveryOption.builder()
                        .type(DeliveryType.ALDDLE)
                        .label("알뜰배달")
                        .originalFee(2000)
                        .discountedFee(1000)
                        .build(),
                DeliveryOption.builder()
                        .type(DeliveryType.SINGLE)
                        .label("한집배달")
                        .originalFee(2000)
                        .discountedFee(1000)
                        .build()
        );

        Store expectedStore = Store.builder()
                .id(1L)
                .name("롯데리아 남성역점")
                .rating(4.9)
                .reviewCount(690)
                .minOrderPrice(14000)
                .discountType(DiscountType.INSTANT)
                .discountAmount(3000)
                .storeStatus(StoreStatus.BREAK)
                .storeStatusLabel("준비 중이에요")
                .address("서울특별시 동작구 사당로 200")
                .latitude(37.484806)
                .longitude(126.970473)
                .deliveryOptions(deliveryOptions)
                .build();

        given(storeRepository.findById(1L)).willReturn(Optional.of(expectedStore));

        //when
        Store store = storeService.findStore(1L);

        //then
        assertThat(store).isNotNull();
        assertThat(store.getId()).isEqualTo(1L);
        assertThat(store.getName()).isEqualTo("롯데리아 남성역점");
        assertThat(store.getDeliveryOptions()).isNotEmpty();
        assertThat(store.getDeliveryOptions()).hasSize(3);
    }

    @Test
    @DisplayName("가게 상세 정보 조회 - 실패")
    public void findStoreDetails_fail() throws Exception {
        //given
        long storeId = 9999L;

        given(storeRepository.findById(storeId)).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> storeService.findStore(storeId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("가게를 찾을 수 없습니다.");

    }
}
