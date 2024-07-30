package com.jmanagement.job.controller;

import com.jmanagement.commons.dto.JobResponseWithCompany;
import com.jmanagement.job.service.impl.JobServiceImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/jobs")
@AllArgsConstructor
public class JobController {

  private final JobServiceImpl service;

  @GetMapping
  public ResponseEntity<List<JobResponseWithCompany>> findAll() {
    var jobs = service.findAll();
    return new ResponseEntity<>(jobs, HttpStatus.OK);
  }
}
