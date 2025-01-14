package com.codehouse.apis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codehouse.apis.DateRestApi;
import com.codehouse.apis.models.DateCreateAtAPI;
@Service
public class DateCreateAtServiceImplement implements  DateCreatedServiceAt{
	
	@Autowired
	private DateRestApi dateRestApi;

	@Override
	public List<DateCreateAtAPI> getDateAPI() {
		
		return dateRestApi.getDateAPI();
	}

}
