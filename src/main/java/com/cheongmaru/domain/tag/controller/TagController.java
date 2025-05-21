package com.cheongmaru.domain.tag.controller;

import com.cheongmaru.domain.tag.domain.Tag;
import com.cheongmaru.domain.tag.service.TagService;
import com.cheongmaru.global.api.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag", description = "태그 관련 API")
public class TagController {

    private final TagService tagService;
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @Operation(summary = "태그 목록 조회", description = "전체 태그 목록을 조회합니다.")
    public ApiResult<List<Tag>> getTags() {
        return ApiResult.success(tagService.getAllTags());
    }
}
