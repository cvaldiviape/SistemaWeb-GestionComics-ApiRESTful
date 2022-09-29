package com.mangagod.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean // indico que de esta interface no se puedan crear instancias
public interface BaseRepository <Entity, ID> extends JpaRepository<Entity, ID>{

}