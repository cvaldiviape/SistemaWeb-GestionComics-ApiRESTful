package com.mangagod.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.mangagod.entity.base.BaseEntity;
import com.mangagod.util.enums.Sex;

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
@Table(name = "mangakas")
public class MangakaEntity extends BaseEntity {

	@Column(name = "name", nullable = false, unique = true)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "sex")
	private Sex sex;
	@Column(name = "birth_date")
	private LocalDate birthDate;
	@Builder.Default
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "mangaka", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<StoryMangakaEntity> storiesMangakas = new HashSet<>();
	
}