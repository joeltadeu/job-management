package com.jmanagement.job.service;

import com.jmanagement.commons.dto.*;
import com.jmanagement.job.entity.JobEntity;

import java.util.List;

public interface JobService {
  List<JobResponseWithCompany> findAll();

  List<JobResponse> findAllByCompanyId(Long companyId);

  JobResponseWithCompany findById(Long companyId, Long id);

  JobEntity findById(Long id);

  JobEntity create(JobEntity entity);

  void update(JobRequest job, Long companyId, Long id);

  void delete(Long companyId, Long id);

  List<JobResponseWithCompany> toModel(List<JobEntity> jobs);

  void save(JobRequest job, Long companyId, Long id);

  void delete(Long id);

  List<JobEntity> getAll();

  JobResponseWithCompany get(Long id, CompanyResponse company);

  default void deleteFallback(Long companyId, Long id, Exception ex) {
    delete(id);
  }

  default List<JobResponseWithCompany> findAllFallback(Exception ex) {
    return toModel(getAll());
  }

  default JobResponseWithCompany findByIdFallback(Long companyId, Long id, Exception ex) {
    return get(id, new CompanyResponse(companyId));
  }

  default void saveFallback(JobRequest job, Long companyId, Long id, Exception ex) {
    save(job, companyId, id);
  }
}
