package com.mangagod.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppSettingProperties {

	// setting application.proterties
	@Value("${app.jwt-expiration-milliseconds}") // obtendre el valor definido en el archivo "application.porperties"
	public Integer JWT_EXPIRATION_IN_MLS;
	@Value("${app.jwt-type}") // obtendre el valor definido en el archivo "application.porperties"
	public String JWT_TYPE;
	@Value("${app.jwt-secret}") // obtendre el valor definido en el archivo "application.porperties"
	public String JWT_SECRET;
}
