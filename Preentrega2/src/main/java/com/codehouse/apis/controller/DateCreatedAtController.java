package com.codehouse.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codehouse.apis.models.DateCreateAtAPI;
import com.codehouse.apis.service.DateCreatedServiceAt;

@RestController
@RequestMapping("/api/dateCreatedAt")
public class DateCreatedAtController {
	
	@Autowired
	
private DateCreatedServiceAt dateCreateAtService;
	
	@GetMapping(value="/", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<DateCreateAtAPI> getDateCreateAt()
	{
		try {
			DateCreateAtAPI dateCreateAtAPI = dateCreateAtService.getDateAPI();
		return ResponseEntity.ok(dateCreateAtAPI);
		}
		catch(Exception e)
		{
			return ResponseEntity.internalServerError().build();
		}
	}
}
