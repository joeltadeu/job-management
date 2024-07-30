package com.jmanagement.commons.dto;

public record CompanyResponse(Long id, String name, String description) {
    public CompanyResponse(Long id) {
        this(id, "", "");
    }
}
