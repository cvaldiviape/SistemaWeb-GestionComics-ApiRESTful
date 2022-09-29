package com.mangagod.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "countries") 
public class CountryEntity extends BaseEntity {

	@Column(name = "name", nullable = false, unique = true)
	private String name;
	@Builder.Default
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
	private Set<StoryEntity> stories = new HashSet<>();
	
}