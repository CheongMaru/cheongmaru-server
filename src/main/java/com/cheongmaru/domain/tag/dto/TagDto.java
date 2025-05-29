package com.cheongmaru.domain.tag.dto;

import com.cheongmaru.domain.tag.domain.Tag;

public class TagDto {

    private Long id;
    private String name;

    public TagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TagDto fromEntity(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
