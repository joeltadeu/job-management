package com.jmanagement.review.mapper;

import static java.util.stream.Collectors.toList;

import com.jmanagement.commons.dto.*;
import com.jmanagement.review.entity.ReviewEntity;
import java.util.List;

public class ReviewMapper {
  public static ReviewResponse toModel(ReviewEntity entity) {
    return new ReviewResponse(
        entity.getId(),
        entity.getCreatedAt(),
        entity.getTitle(),
        entity.getDescription(),
        entity.getRating());
  }

  public static ReviewResponseWithCompany toModel(ReviewEntity entity, CompanyResponse company) {
    return new ReviewResponseWithCompany(
        entity.getId(),
        company,
        entity.getCreatedAt(),
        entity.getTitle(),
        entity.getDescription(),
        entity.getRating());
  }

  public static List<ReviewResponseWithCompany> toModel(
      List<ReviewEntity> reviewEntities, CompanyResponse company) {
    return reviewEntities.stream()
        .map(entity -> ReviewMapper.toModel(entity, company))
        .collect(toList());
  }

  public static List<ReviewResponse> toModel(List<ReviewEntity> jobEntities) {
    return jobEntities.stream().map(ReviewMapper::toModel).collect(toList());
  }

  public static ReviewEntity toEntity(ReviewRequest request, Long companyId) {
    return ReviewEntity.builder()
        .companyId(companyId)
        .title(request.title())
        .description(request.description())
        .rating(request.rating())
        .build();
  }

  public static ReviewEntity toEntity(ReviewRequest review, ReviewEntity entity) {
    entity.setDescription(review.description());
    entity.setTitle(review.title());
    entity.setRating(review.rating());
    return entity;
  }
}
