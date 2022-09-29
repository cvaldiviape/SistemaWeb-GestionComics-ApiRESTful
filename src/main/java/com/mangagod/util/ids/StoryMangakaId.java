package com.mangagod.util.ids;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StoryMangakaId implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "story_id")
	private Integer storyId;
	@Column(name = "mangaka_id")
	private Integer mangakaId;
	
	public StoryMangakaId() {
		
	}
	
	public StoryMangakaId(Integer storyId, Integer mangakaId) {
		this.storyId = storyId;
		this.mangakaId = mangakaId;
	}

	public Integer getStoryId() {
		return storyId;
	}

	public void setStoryId(Integer storyId) {
		this.storyId = storyId;
	}

	public Integer getMangakaId() {
		return mangakaId;
	}

	public void setMangakaId(Integer mangakaId) {
		this.mangakaId = mangakaId;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((storyId == null) ? 0 : storyId.hashCode());
        result = prime * result + ((mangakaId == null) ? 0 : mangakaId.hashCode());
        return result;
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StoryMangakaId other = (StoryMangakaId) obj;
        return Objects.equals(getStoryId(), other.getStoryId()) && Objects.equals(getMangakaId(), other.getMangakaId());
    }

}