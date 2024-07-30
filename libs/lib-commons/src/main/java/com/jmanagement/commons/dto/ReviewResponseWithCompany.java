package com.jmanagement.commons.dto;

import java.time.LocalDateTime;

public record ReviewResponseWithCompany(
    Long id,
    CompanyResponse company,
    LocalDateTime createdAt,
    String title,
    String description,
    Double rating) {}
