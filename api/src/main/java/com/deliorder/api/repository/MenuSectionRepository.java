package com.deliorder.api.repository;

import com.deliorder.api.entity.MenuSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuSectionRepository extends JpaRepository<MenuSection, Long> {
    List<MenuSection> findByStoreIdOrderByDisplayOrder(Long storeId);
}
