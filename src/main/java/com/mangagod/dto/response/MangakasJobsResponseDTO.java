package com.mangagod.dto.response;

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
public class MangakasJobsResponseDTO {

	private Integer mangakaId;
	private String nameMangaka;
	private Integer jobId;
	private String nameJob;
	
}