package com.mangagod.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Component
public class AppHelpers {

	public String convertLocalDateTimeToString(LocalDateTime localDateTime) {
		// Get current date time
		LocalDateTime currentDateTime = localDateTime;
		// Inbuilt format
		// DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		// Custom format if needed
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		// Format LocalDateTime
		String formattedDateTime = currentDateTime.format(formatter);
		
		return formattedDateTime;
	}
	
	public Pageable getPageable(Integer numberPage, Integer sizePage, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
				? Sort.by(sortBy).ascending() 
				: Sort.by(sortBy).descending();
	
		// agregando paginación
		return PageRequest.of(numberPage, sizePage, sort);
	}
	
}