package com.mangagod.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.mangagod.entity.StoryMangakaEntity;
import com.mangagod.repository.base.BaseRepository;
import com.mangagod.util.ids.StoryMangakaId;

public interface StoryMangakaRepository extends BaseRepository<StoryMangakaEntity, StoryMangakaId> {
	
	@Modifying
	@Query( value = "DELETE FROM stories_mangakas WHERE story_id = ?1", 
			nativeQuery = true)
	public void deleteByStoryId(Integer storyId);
	
	@Modifying
	@Query( value = "DELETE FROM stories_mangakas WHERE mangaka_id = ?1", 
			nativeQuery = true)
	public void deleteByMangakaId(Integer mangakaId);

}