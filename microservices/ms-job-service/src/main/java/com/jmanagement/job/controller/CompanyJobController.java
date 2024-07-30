package com.jmanagement.job.controller;

import com.jmanagement.commons.dto.JobRequest;
import com.jmanagement.commons.dto.JobResponse;
import com.jmanagement.commons.dto.JobResponseWithCompany;
import com.jmanagement.job.mapper.JobMapper;
import com.jmanagement.job.service.impl.JobServiceImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/companies/{companyId}/jobs")
@AllArgsConstructor
public class CompanyJobController {

  private final JobServiceImpl service;

  @GetMapping(value = "/{id}")
  public ResponseEntity<JobResponseWithCompany> findById(
      @PathVariable Long companyId, @PathVariable Long id) {
    var job = service.findById(companyId, id);
    return ResponseEntity.ok(job);
  }

  @GetMapping
  public ResponseEntity<List<JobResponse>> findAll(@PathVariable Long companyId) {
    var jobs = service.findAllByCompanyId(companyId);
    return new ResponseEntity<>(jobs, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<JobResponse> create(
      @PathVariable Long companyId, @RequestBody JobRequest request) {
    var saved = service.create(JobMapper.toEntity(request, companyId));
    return new ResponseEntity<>(JobMapper.toModel(saved), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Void> update(
      @RequestBody JobRequest request, @PathVariable Long companyId, @PathVariable Long id) {
    service.update(request, companyId, id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long companyId, @PathVariable Long id) {
    service.delete(companyId, id);
    return ResponseEntity.noContent().build();
  }
}
