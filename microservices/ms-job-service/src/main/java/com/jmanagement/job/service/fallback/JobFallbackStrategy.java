package com.jmanagement.job.service.fallback;

import com.jmanagement.commons.dto.JobRequest;
import com.jmanagement.commons.dto.JobResponseWithCompany;
import java.util.List;

public interface JobFallbackStrategy {
    void deleteFallback(Long companyId, Long id, Exception ex);
    void saveFallback(JobRequest job, Long companyId, Long id, Exception ex);
    JobResponseWithCompany findByIdFallback(Long companyId, Long id, Exception ex);
    List<JobResponseWithCompany> findAllFallback(Exception ex);
}
