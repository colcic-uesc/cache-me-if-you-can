package com.cachemeifyoucan.econometro.application.dto;

public record CategoryRequest(
    String name,
    long parentId
) {

    public CategoryRequest(String name) {
        this(name, 0);
    }
}
