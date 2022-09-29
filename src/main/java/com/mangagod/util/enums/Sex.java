package com.mangagod.util.enums;

import de.pentabyte.springfox.ApiEnum;
import io.swagger.annotations.ApiModel;

@ApiModel
public enum Sex {
	@ApiEnum("First Option")
	FEMENINO,
	@ApiEnum("Second Option")
	MASCULINO,
	@ApiEnum("Three Option")
	NO_ESPECIFICADO;
}
