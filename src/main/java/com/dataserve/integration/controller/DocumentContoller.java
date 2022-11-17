package com.dataserve.integration.controller;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.dataserve.integration.dto.ClassDTO;
import com.dataserve.integration.exception.ServiceException;
import com.dataserve.integration.service.DatabaseService;
import com.dataserve.integration.service.FileNetService;
import com.dataserve.integration.util.IntegrationLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class DocumentContoller {
	
	@Autowired
	private FileNetService fnService;
	
	@Autowired
	private DatabaseService dbService;
	
	@Autowired
	private ObjectMapper mapper;

	@GetMapping(value="/getDocumentClasses")
	public ResponseEntity<JSONArray> getDocumentClasses(@RequestHeader(value="username") String userName, @RequestHeader(value="password") String password) {
		try {
			fnService.getAllDocumentClasses();
		} catch (ServiceException e) {
			IntegrationLogger.error("Error getting all document classes", e);
			return new ResponseEntity<>(new JSONArray(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new JSONArray(), HttpStatus.OK);
	}

	@GetMapping(value="/getAllDocumentClasses")
	public ResponseEntity<String> getAllDocumentClasses(@RequestHeader(value="username") String userName, @RequestHeader(value="password") String password) {
		try {
			List<ClassDTO> classes = dbService.getAllDocumentClasses();
			String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(classes);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (ServiceException e) {
			IntegrationLogger.error("Error getting all document classes", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (JsonProcessingException e) {
			IntegrationLogger.error("Error mapping result to json", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
