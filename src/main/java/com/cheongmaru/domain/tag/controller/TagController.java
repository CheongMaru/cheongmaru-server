package com.cheongmaru.domain.tag.controller;

import com.cheongmaru.domain.tag.dto.TagDto;
import com.cheongmaru.domain.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Tag", description = "태그 관련 API")
@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @Operation(summary = "태그 목록 조회", description = "전체 태그 목록을 조회합니다.")
    public List<TagDto> getTags() {
        return tagService.getAllTags();
    }
}
