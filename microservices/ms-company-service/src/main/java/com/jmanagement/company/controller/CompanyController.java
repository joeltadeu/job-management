package com.jmanagement.company.controller;

import com.jmanagement.commons.dto.CompanyRequest;
import com.jmanagement.commons.dto.CompanyResponse;
import com.jmanagement.company.mapper.CompanyMapper;
import com.jmanagement.company.service.CompanyService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/companies")
@AllArgsConstructor
public class CompanyController {

  private final CompanyService service;

  @GetMapping(value = "/{id}")
  public ResponseEntity<CompanyResponse> findById(@PathVariable Long id) {
    var entity = service.findById(id);
    return ResponseEntity.ok(CompanyMapper.toModel(entity));
  }

  @GetMapping
  public ResponseEntity<List<CompanyResponse>> findAll() {
    var companies = service.findAll();
    return new ResponseEntity<>(CompanyMapper.toModel(companies), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CompanyResponse> create(@RequestBody CompanyRequest request) {
    var saved = service.create(CompanyMapper.toEntity(request));
    return new ResponseEntity<>(CompanyMapper.toModel(saved), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Void> update(@RequestBody CompanyRequest request, @PathVariable Long id) {
    service.update(request, id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
