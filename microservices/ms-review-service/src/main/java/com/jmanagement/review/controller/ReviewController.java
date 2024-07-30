package com.jmanagement.review.controller;

import com.jmanagement.commons.dto.ReviewResponseWithCompany;
import com.jmanagement.review.service.ReviewService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reviews")
@AllArgsConstructor
public class ReviewController {

  private final ReviewService service;

  @GetMapping
  public ResponseEntity<List<ReviewResponseWithCompany>> findAll() {
    var reviews = service.findAll();
    return new ResponseEntity<>(reviews, HttpStatus.OK);
  }
}
