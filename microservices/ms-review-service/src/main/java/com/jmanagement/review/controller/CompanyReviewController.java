package com.jmanagement.review.controller;

import com.jmanagement.commons.dto.ReviewRequest;
import com.jmanagement.commons.dto.ReviewResponse;
import com.jmanagement.commons.dto.ReviewResponseWithCompany;
import com.jmanagement.review.mapper.ReviewMapper;
import com.jmanagement.review.service.ReviewService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/companies/{companyId}/reviews")
@AllArgsConstructor
public class CompanyReviewController {

  private final ReviewService service;

  @GetMapping(value = "/{id}")
  public ResponseEntity<ReviewResponseWithCompany> findById(
      @PathVariable Long companyId, @PathVariable Long id) {
    var review = service.findById(companyId, id);
    return ResponseEntity.ok(review);
  }

  @GetMapping
  public ResponseEntity<List<ReviewResponse>> findAll(@PathVariable Long companyId) {
    var reviews = service.findAllByCompanyId(companyId);
    return new ResponseEntity<>(reviews, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ReviewResponse> create(
      @PathVariable Long companyId, @RequestBody ReviewRequest request) {
    var saved = service.create(ReviewMapper.toEntity(request, companyId));
    return new ResponseEntity<>(ReviewMapper.toModel(saved), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Void> update(
      @RequestBody ReviewRequest request, @PathVariable Long companyId, @PathVariable Long id) {
    service.update(request, companyId, id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long companyId, @PathVariable Long id) {
    service.delete(companyId, id);
    return ResponseEntity.noContent().build();
  }
}
