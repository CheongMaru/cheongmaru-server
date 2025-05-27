package com.cheongmaru.domain.category.controller;

import com.cheongmaru.domain.category.domain.Category;
import com.cheongmaru.domain.category.service.CategoryService;
import com.cheongmaru.global.api.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "카테고리 관련 API")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "카테고리 목록 조회",
            description = "모든 게시글 카테고리 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 목록 조회 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ApiResult<List<Category>> getAllCategories() {
        return categoryService.findAllCategories();
    }
}
