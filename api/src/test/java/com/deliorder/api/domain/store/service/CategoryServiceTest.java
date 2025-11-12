package com.deliorder.api.domain.store.service;

import com.deliorder.api.domain.store.entity.Category;
import com.deliorder.api.domain.store.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리 목록 조회 성공")
    public void findAllCategories() throws Exception {
        //given
        Category chicken = new Category("CHICKEN", "치킨", "/icons/chicken.png");
        Category pizza = new Category("PIZZA", "피자", "/icons/pizza.png");
        Category korean = new Category("KOREAN", "한식", "/icons/korean.png");

        List<Category> expectedCategories = Arrays.asList(chicken, pizza, korean);

        given(categoryRepository.findAll()).willReturn(expectedCategories);

        //when
        List<Category> allCategories = categoryService.findAllCategories();

        //then
        assertThat(allCategories).isNotEmpty();
        assertThat(allCategories).hasSize(3);
        assertThat(allCategories.getFirst().getType()).isEqualTo("CHICKEN");
        assertThat(allCategories.getFirst().getLabel()).isEqualTo("치킨");
        assertThat(allCategories.getFirst().getIconUrl()).isEqualTo("/icons/chicken.png");
    }

    @Test
    @DisplayName("카테고리 목록 조회 성공 - 빈 목록 조회")
    public void findAllCategories_emptyList() throws Exception {
        //given
        given(categoryRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<Category> allCategories = categoryService.findAllCategories();

        //then
        assertThat(allCategories).isNotNull();
        assertThat(allCategories).isEmpty();
        assertThat(allCategories).hasSize(0);
    }
}