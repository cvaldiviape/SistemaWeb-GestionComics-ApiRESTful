package com.mangagod.dto.response.page;

import java.util.List;

import com.mangagod.dto.response.page.base.PageBase;
import com.mangagod.dto.response.view.MangakaViewResponseDTO;
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
public class MangakasPageResponseDTO extends PageBase {

	private List<MangakaViewResponseDTO> mangakas;
	
}