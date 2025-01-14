package com.codehouse.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.codehouse.apis.models.DateCreateAtAPI;

@Component
public class DateRestApi implements DateRestApiInterface{
	
	private final String BASE_URL = "https://6785a7b9f80b78923aa42062.mockapi.io/api/date/CreateAtDate";
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<DateCreateAtAPI> getDateAPI() {
		try {
			//colocamos null por que no le enviamos nada al servicio
			List<DateCreateAtAPI> dateRest = restTemplate.exchange(BASE_URL, HttpMethod.GET, null,List.class).getBody();
			return dateRest;
		}catch(Exception e)
		{
			throw new RuntimeException("Error Trayendo Fecha"+e.getMessage(),e);
		}
		
	}
	

}
