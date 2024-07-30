package com.jmanagement.commons.dto;

import java.time.LocalDateTime;

public record ReviewResponse(
    Long id,
    LocalDateTime createdAt,
    String title,
    String description,
    Double rating) {}
