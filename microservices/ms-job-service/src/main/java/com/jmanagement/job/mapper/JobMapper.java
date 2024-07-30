package com.jmanagement.job.mapper;

import static java.util.stream.Collectors.toList;

import com.jmanagement.commons.dto.CompanyResponse;
import com.jmanagement.commons.dto.JobRequest;
import com.jmanagement.commons.dto.JobResponse;
import com.jmanagement.commons.dto.JobResponseWithCompany;
import com.jmanagement.job.entity.JobEntity;
import java.util.List;

public class JobMapper {
  public static JobResponse toModel(JobEntity entity) {
    return new JobResponse(
        entity.getId(),
        entity.getTitle(),
        entity.getDescription(),
        entity.getMinSalary(),
        entity.getMaxSalary(),
        entity.getLocation());
  }

  public static JobResponseWithCompany toModel(JobEntity entity, CompanyResponse company) {
    return new JobResponseWithCompany(
        entity.getId(),
        company,
        entity.getTitle(),
        entity.getDescription(),
        entity.getMinSalary(),
        entity.getMaxSalary(),
        entity.getLocation());
  }

  public static List<JobResponse> toModel(List<JobEntity> jobEntities) {
    return jobEntities.stream().map(JobMapper::toModel).collect(toList());
  }

  public static JobEntity toEntity(JobRequest request, Long companyId) {
    return JobEntity.builder()
        .companyId(companyId)
        .title(request.title())
        .description(request.description())
        .location(request.location())
        .minSalary(request.minSalary())
        .maxSalary(request.maxSalary())
        .build();
  }

  public static JobEntity toEntity(JobRequest job, JobEntity entity) {
    entity.setCompanyId(job.companyId());
    entity.setDescription(job.description());
    entity.setTitle(job.title());
    entity.setLocation(job.location());
    entity.setMinSalary(job.minSalary());
    entity.setMaxSalary(job.maxSalary());
    return entity;
  }
}
