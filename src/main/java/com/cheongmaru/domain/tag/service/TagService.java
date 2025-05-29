package com.cheongmaru.domain.tag.service;

import com.cheongmaru.domain.tag.domain.Tag;
import com.cheongmaru.domain.tag.dto.TagDto;
import com.cheongmaru.domain.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagDto> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagDto::fromEntity)
                .collect(Collectors.toList());
    }
}
