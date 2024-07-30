package com.jmanagement.job.client;

import com.jmanagement.commons.dto.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("company-service")
public interface CompanyClient {

  @GetMapping("/v1/companies/{id}")
  CompanyResponse findById(@PathVariable("id") Long id);
}
