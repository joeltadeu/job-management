package com.jmanagement.company.service;

import com.jmanagement.commons.dto.CompanyRequest;
import com.jmanagement.commons.validation.RestPreConditions;
import com.jmanagement.company.entity.CompanyEntity;
import com.jmanagement.company.mapper.CompanyMapper;
import com.jmanagement.company.repository.CompanyRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {

  private final CompanyRepository repository;

  public List<CompanyEntity> findAll() {
    return repository.findAll();
  }

  public CompanyEntity findById(Long id) {
    return RestPreConditions.checkNotNull(
        repository.findById(id), "Company with Id %s was not found", id);
  }

  public CompanyEntity create(CompanyEntity entity) {
    return repository.save(entity);
  }

  public void update(CompanyRequest company, Long id) {
    var entity = findById(id);
    repository.save(CompanyMapper.toEntity(company, entity));
  }

  public void delete(Long id) {
    var entity = findById(id);
    repository.delete(entity);
  }
}
