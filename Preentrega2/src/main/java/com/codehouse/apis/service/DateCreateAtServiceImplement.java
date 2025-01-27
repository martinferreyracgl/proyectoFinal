package com.codehouse.apis.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codehouse.apis.DateRestApi;
import com.codehouse.apis.models.DateCreateAtAPI;
@Service
public class DateCreateAtServiceImplement implements  DateCreatedServiceAt{
	
	@Autowired
	private DateRestApi dateRestApi;

	@Override
	public DateCreateAtAPI getDateAPI() {
		
		return dateRestApi.getDateAPI();
	}

	@Override
	public Date obtenerFechaActual() {
		DateCreateAtAPI dateCreateAt = dateRestApi.getDateAPI();
		
		if (dateCreateAt != null && dateCreateAt.getDateTime() != null) {
            LocalDateTime localDateTime = LocalDateTime.parse(dateCreateAt.getDateTime(), DateTimeFormatter.ISO_DATE_TIME);
            return Date.valueOf(localDateTime.toLocalDate());
        }
		
		throw new RuntimeException("No se pudo obtener la fecha de la API");
	}

}
