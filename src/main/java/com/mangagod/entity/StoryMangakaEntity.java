package com.mangagod.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import com.mangagod.util.ids.StoryMangakaId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stories_mangakas") 
public class StoryMangakaEntity {

	@Builder.Default
	@EmbeddedId
	private StoryMangakaId id = new StoryMangakaId();
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("storyId") // hace referencia al nombre de atributo en la clase "StoryMangakaId"
	@JoinColumn(name = "story_id")
	private StoryEntity story;
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("mangakaId") // hace referencia al nombre de atributo en la clase "StoryMangakaId"
	@JoinColumn(name = "mangaka_id")
	private MangakaEntity mangaka;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "job_id")
	private JobEntity job;
	
}