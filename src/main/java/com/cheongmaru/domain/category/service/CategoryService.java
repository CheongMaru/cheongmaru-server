package com.cheongmaru.domain.category.service;

import com.cheongmaru.domain.category.domain.Category;
import com.cheongmaru.domain.category.repository.CategoryRepository;
import com.cheongmaru.global.api.ApiResult;
import com.cheongmaru.global.exception.CustomException;
import com.cheongmaru.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ApiResult<List<Category>> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        return ApiResult.success(categories);
    }
}
