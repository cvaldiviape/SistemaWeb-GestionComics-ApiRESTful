package com.mangagod.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
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
@Table(name = "genres") 
public class GenreEntity extends BaseEntity {

	@Column(name = "name", nullable = false, unique = true)
	private String name;
	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "genres")
	private Set<StoryEntity> stories = new HashSet<>();
	
}