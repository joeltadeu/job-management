package com.jmanagement.commons.dto;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record ReviewRequest(Long companyId, String title, String description, Double rating)
    implements ReviewRequestBuilder.With {}
