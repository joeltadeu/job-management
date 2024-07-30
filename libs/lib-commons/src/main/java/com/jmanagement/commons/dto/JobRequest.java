package com.jmanagement.commons.dto;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record JobRequest(
    Long companyId,
    String title,
    String description,
    Integer minSalary,
    Integer maxSalary,
    String location) {}
