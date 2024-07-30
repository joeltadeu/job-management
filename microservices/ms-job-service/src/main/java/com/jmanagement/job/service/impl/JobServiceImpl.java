package com.jmanagement.job.service.impl;

import static java.util.stream.Collectors.toList;

import com.jmanagement.commons.dto.*;
import com.jmanagement.commons.validation.RestPreConditions;
import com.jmanagement.job.client.CompanyClient;
import com.jmanagement.job.entity.JobEntity;
import com.jmanagement.job.mapper.JobMapper;
import com.jmanagement.job.repository.JobRepository;
import com.jmanagement.job.service.JobService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {

  private final JobRepository repository;
  private final CompanyClient companyClient;

  @Override
  @CircuitBreaker(name = "companyBreaker", fallbackMethod = "findAllFallback")
  public List<JobResponseWithCompany> findAll() {
    List<JobEntity> jobs = repository.findAll();
    return jobs.stream()
        .map(entity -> JobMapper.toModel(entity, companyClient.findById(entity.getCompanyId())))
        .collect(toList());
  }

  @Override
  public List<JobResponse> findAllByCompanyId(Long companyId) {
    var jobs = repository.findByCompanyId(companyId);
    return JobMapper.toModel(jobs);
  }

  @Override
  @CircuitBreaker(name = "companyBreaker", fallbackMethod = "findByIdFallback")
  public JobResponseWithCompany findById(Long companyId, Long id) {
    var company = companyClient.findById(companyId);
    return get(id, company);
  }

  @Override
  public JobEntity findById(Long id) {
    return RestPreConditions.checkNotNull(
        repository.findById(id), "Job with Id %s was not found", id);
  }

  @Override
  public JobEntity create(JobEntity entity) {
    return repository.save(entity);
  }

  @Override
  @CircuitBreaker(name = "companyBreaker", fallbackMethod = "saveFallback")
  public void update(JobRequest job, Long companyId, Long id) {
    companyClient.findById(companyId);
    save(job, companyId, id);
  }

  @Override
  @CircuitBreaker(name = "companyBreaker", fallbackMethod = "deleteFallback")
  public void delete(Long companyId, Long id) {
    companyClient.findById(companyId);
    delete(id);
  }

  @Override
  public List<JobResponseWithCompany> toModel(List<JobEntity> reviews) {
    return reviews.stream()
        .map(entity -> JobMapper.toModel(entity, new CompanyResponse(entity.getCompanyId())))
        .collect(toList());
  }

  @Override
  public List<JobEntity> getAll() {
    return repository.findAll();
  }

  @Override
  public JobResponseWithCompany get(Long id, CompanyResponse company) {
    var entity = findById(id);
    return JobMapper.toModel(entity, company);
  }

  @Override
  public void save(JobRequest job, Long companyId, Long id) {
    var entity = findById(id);
    repository.save(
        JobMapper.toEntity(JobRequestBuilder.builder(job).companyId(companyId).build(), entity));
  }

  @Override
  public void delete(Long id) {
    var entity = findById(id);
    repository.delete(entity);
  }
}
