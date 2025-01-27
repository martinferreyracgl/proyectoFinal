package com.codehouse.apis;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.codehouse.apis.models.DateCreateAtAPI;

@Component
public class DateRestApi implements DateRestApiInterface{
	
	private final String BASE_URL = "https://timeapi.io/api/Time/current/zone?timeZone=America/Argentina/Buenos_Aires";
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public DateCreateAtAPI getDateAPI() {
		try {
			//colocamos null por que no le enviamos nada al servicio
			DateCreateAtAPI dateRest = restTemplate.exchange(BASE_URL, HttpMethod.GET, null,DateCreateAtAPI.class).getBody();
			 if (dateRest == null || dateRest.getDateTime() == null) {
	                throw new RuntimeException("Respuesta de la API es nula");
	            }
			return dateRest;
		}catch(Exception e)
		{
			System.err.println("Error trayendo fecha de la API: " + e.getMessage());

	        // Generar la fecha actual con java.util.Date
	        Date now = new Date();
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // Formato similar al de la API
	        String formattedDate = formatter.format(now);

	        // Crear objeto de respuesta con la fecha generada manualmente
	        DateCreateAtAPI localDate = new DateCreateAtAPI();
	        localDate.setDateTime(formattedDate);  // Asignamos la fecha formateada como String
	        localDate.setTimeZone("Local");

	        return localDate;
		}
		
	}
	

}
