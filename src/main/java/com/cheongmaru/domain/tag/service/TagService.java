package com.cheongmaru.domain.tag.service;

import com.cheongmaru.domain.tag.domain.Tag;
import com.cheongmaru.domain.tag.repository.TagRepository;
import com.cheongmaru.global.exception.CustomException;
import com.cheongmaru.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        if (tags == null || tags.isEmpty()) {
            throw new CustomException(ErrorCode.TAG_NOT_FOUND);
        }
        return tags;
    }
}
