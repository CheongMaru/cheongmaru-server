package com.cheongmaru.domain.category.repository;

import com.cheongmaru.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
