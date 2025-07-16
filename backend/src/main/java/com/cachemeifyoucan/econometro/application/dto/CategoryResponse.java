package com.cachemeifyoucan.econometro.application.dto;

public record CategoryResponse(
    long id,
    String name,
    long parentId
) {

}
