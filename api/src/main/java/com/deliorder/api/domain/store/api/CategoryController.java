package com.deliorder.api.domain.store.api;

import com.deliorder.api.common.dto.ApiResponse;
import com.deliorder.api.domain.store.api.dto.CategoryData;
import com.deliorder.api.domain.store.api.dto.CategoryItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @GetMapping
    public ResponseEntity<ApiResponse<CategoryData>> getCategories() {
        List<CategoryItem> categoryItems = Arrays.asList(
            CategoryItem.builder().type("CHICKEN").label("치킨").iconUrl("/icons/chicken.png").build(),
            CategoryItem.builder().type("PIZZA").label("피자").iconUrl("/icons/pizza.png").build(),
            CategoryItem.builder().type("KOREAN").label("한식").iconUrl("/icons/korean.png").build()
        );

        CategoryData categoryData = CategoryData.builder().categories(categoryItems).build();

        ApiResponse<CategoryData> res = ApiResponse.success("카테고리 목록 조회 성공", categoryData);

        return ResponseEntity.ok(res);
    }
}
