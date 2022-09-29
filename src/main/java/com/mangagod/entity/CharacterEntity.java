package com.mangagod.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.mangagod.entity.base.BaseEntity;
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
@Entity
@Table(name = "characters")
public class CharacterEntity extends BaseEntity {

	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "url_image")
	private String urlImage;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "story_id")
	private StoryEntity story;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	private TypeCharacterEntity type;
	
}