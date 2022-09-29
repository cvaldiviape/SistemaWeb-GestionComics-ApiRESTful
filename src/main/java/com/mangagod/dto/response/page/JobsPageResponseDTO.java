package com.mangagod.dto.response.page;

import java.util.List;

import com.mangagod.dto.response.JobResponseDTO;
import com.mangagod.dto.response.page.base.PageBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class JobsPageResponseDTO extends PageBase {

	private List<JobResponseDTO> jobs;
	
}