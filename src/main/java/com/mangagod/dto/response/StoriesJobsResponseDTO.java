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
public class StoriesJobsResponseDTO {

	private Integer storyId;
	private String nameStory;
	private Integer jobId;
	private String nameJob;
	
}