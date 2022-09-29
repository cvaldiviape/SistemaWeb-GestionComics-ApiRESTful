package com.mangagod.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.mangagod.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "stories") 
public class StoryEntity extends BaseEntity {
	
	@Column(name = "title", nullable = false, unique = true)
	private String title;
	@Column(name = "year", nullable = false)
	private Short year;
	@Column(name = "synopsis", nullable = false)
	private String synopsis;
	@Column(name = "state", nullable = false)
	private Boolean state;
	@Column(name = "url_image")
	private String urlImage;
	@Column(name = "adaptation_anime", nullable = false)
	private Boolean adaptationAnime;
	@Column(name = "price")
	private Double price;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id")
	private CountryEntity country;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "demography_id")
	private DemographyEntity demography;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private CategoryEntity category;
	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "story")
	private Set<CharacterEntity> characters = new HashSet<>();
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "stories_genres", joinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"), 
									    inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
	private Set<GenreEntity> genres = new HashSet<>();
	@Builder.Default
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "story", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<StoryMangakaEntity> storiesMangakas = new HashSet<>();
		
}