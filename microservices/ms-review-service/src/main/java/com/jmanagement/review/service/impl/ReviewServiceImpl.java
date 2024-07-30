package com.jmanagement.review.service.impl;

import static java.util.stream.Collectors.toList;

import com.jmanagement.commons.dto.*;
import com.jmanagement.commons.validation.RestPreConditions;
import com.jmanagement.review.client.CompanyClient;
import com.jmanagement.review.entity.ReviewEntity;
import com.jmanagement.review.mapper.ReviewMapper;
import com.jmanagement.review.repository.ReviewRepository;
import com.jmanagement.review.service.ReviewService;
import io.github.resilience4j.retry.annotation.Retry;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository repository;
  private final CompanyClient companyClient;

  @Override
  @Retry(name = "companyBreaker", fallbackMethod = "findAllFallback")
  public List<ReviewResponseWithCompany> findAll() {
    return getAll().stream()
        .map(entity -> ReviewMapper.toModel(entity, companyClient.findById(entity.getCompanyId())))
        .collect(toList());
  }

  @Override
  public List<ReviewResponse> findAllByCompanyId(Long companyId) {
    var reviews = repository.findByCompanyId(companyId);
    return ReviewMapper.toModel(reviews);
  }

  @Override
  @Retry(name = "companyBreaker", fallbackMethod = "findByIdFallback")
  public ReviewResponseWithCompany findById(Long companyId, Long id) {
    var company = companyClient.findById(companyId);
    return get(id, company);
  }

  @Override
  public ReviewEntity findById(Long id) {
    return RestPreConditions.checkNotNull(
        repository.findById(id), "Review with Id %s was not found", id);
  }

  @Override
  public ReviewEntity create(ReviewEntity entity) {
    entity.setCreatedAt(LocalDateTime.now());
    return repository.save(entity);
  }

  @Override
  @Retry(name = "companyBreaker", fallbackMethod = "saveFallback")
  public void update(ReviewRequest review, Long companyId, Long id) {
    companyClient.findById(companyId);
    save(review, companyId, id);
  }

  @Override
  @Retry(name = "companyBreaker", fallbackMethod = "deleteFallback")
  public void delete(Long companyId, Long id) {
    companyClient.findById(companyId);
    delete(id);
  }

  @Override
  public List<ReviewResponseWithCompany> toModel(List<ReviewEntity> reviews) {
    return reviews.stream()
        .map(entity -> ReviewMapper.toModel(entity, new CompanyResponse(entity.getCompanyId())))
        .collect(toList());
  }

  @Override
  public List<ReviewEntity> getAll() {
    return repository.findAll();
  }

  @Override
  public ReviewResponseWithCompany get(Long id, CompanyResponse company) {
    var entity = findById(id);
    return ReviewMapper.toModel(entity, company);
  }

  @Override
  public void save(ReviewRequest review, Long companyId, Long id) {
    var entity = findById(id);
    repository.save(ReviewMapper.toEntity(review.withCompanyId(companyId), entity));
  }

  @Override
  public void delete(Long id) {
    var entity = findById(id);
    repository.delete(entity);
  }
}
