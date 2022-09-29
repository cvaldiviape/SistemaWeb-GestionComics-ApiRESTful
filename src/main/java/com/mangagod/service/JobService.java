package com.mangagod.service;

import com.mangagod.dto.request.JobRequestDTO;
import com.mangagod.dto.response.JobResponseDTO;
import com.mangagod.dto.response.page.JobsPageResponseDTO;
import com.mangagod.service.base.BaseService;

public interface JobService extends BaseService<JobsPageResponseDTO, JobResponseDTO, JobRequestDTO, Integer>{

}