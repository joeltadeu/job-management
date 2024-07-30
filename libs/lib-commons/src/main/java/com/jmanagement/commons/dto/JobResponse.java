package com.jmanagement.commons.dto;

public record JobResponse(
    Long id,
    String title,
    String description,
    Integer minSalary,
    Integer maxSalary,
    String location) {}
