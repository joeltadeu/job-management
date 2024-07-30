package com.jmanagement.review.service;

import com.jmanagement.commons.dto.CompanyResponse;
import com.jmanagement.commons.dto.ReviewRequest;
import com.jmanagement.commons.dto.ReviewResponse;
import com.jmanagement.commons.dto.ReviewResponseWithCompany;
import com.jmanagement.review.entity.ReviewEntity;
import java.util.List;

public interface ReviewService {
  List<ReviewResponseWithCompany> findAll();

  List<ReviewResponse> findAllByCompanyId(Long companyId);

  ReviewResponseWithCompany findById(Long companyId, Long id);

  ReviewEntity findById(Long id);

  ReviewEntity create(ReviewEntity entity);

  void delete(Long companyId, Long id);

  void update(ReviewRequest review, Long companyId, Long id);

  List<ReviewResponseWithCompany> toModel(List<ReviewEntity> reviews);

  void save(ReviewRequest review, Long companyId, Long id);

  void delete(Long id);

  List<ReviewEntity> getAll();

  ReviewResponseWithCompany get(Long id, CompanyResponse company);

  default void deleteFallback(Long companyId, Long id, Exception ex) {
    delete(id);
  }

  default List<ReviewResponseWithCompany> findAllFallback(Exception ex) {
    return toModel(getAll());
  }

  default ReviewResponseWithCompany findByIdFallback(Long companyId, Long id, Exception ex) {
    return get(id, new CompanyResponse(companyId));
  }

  default void saveFallback(ReviewRequest review, Long companyId, Long id, Exception ex) {
    save(review, companyId, id);
  }
}
