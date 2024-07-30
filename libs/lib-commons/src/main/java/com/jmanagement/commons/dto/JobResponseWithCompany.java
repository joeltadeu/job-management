package com.jmanagement.commons.dto;

public record JobResponseWithCompany(
    Long id,
    CompanyResponse company,
    String title,
    String description,
    Integer minSalary,
    Integer maxSalary,
    String location) {}
