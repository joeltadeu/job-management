package com.jmanagement.company.mapper;

import com.jmanagement.commons.dto.CompanyRequest;
import com.jmanagement.commons.dto.CompanyResponse;
import com.jmanagement.company.entity.CompanyEntity;

import static java.util.stream.Collectors.toList;

import java.util.List;

public class CompanyMapper {
  public static CompanyResponse toModel(CompanyEntity entity) {
    return new CompanyResponse(entity.getId(), entity.getName(), entity.getDescription());
  }

  public static List<CompanyResponse> toModel(List<CompanyEntity> jobEntities) {
    return jobEntities.stream().map(CompanyMapper::toModel).collect(toList());
  }

  public static CompanyEntity toEntity(CompanyRequest request) {
    return CompanyEntity.builder().name(request.name()).description(request.description()).build();
  }

  public static CompanyEntity toEntity(CompanyRequest job, CompanyEntity entity) {
    entity.setName(job.name());
    entity.setDescription(job.description());
    return entity;
  }
}
