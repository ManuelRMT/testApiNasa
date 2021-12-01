package com.test.atmira.backend.asteroids.demo.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.test.atmira.backend.asteroids.demo.model.Response;
import com.test.atmira.backend.asteroids.demo.service.Service;

@RestController
public class Controller {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private Service service;

	@RequestMapping(value = "asteroids", method = RequestMethod.GET)
	public ResponseEntity<Object> asteroids(@RequestParam(value = "planet") String planet) throws MalformedURLException, IOException {
		Response response = new Response();

		if (!planet.isEmpty()) {
			response = service.getData();
		} else {
			response.setError("Campo obligatorio no informado");
		}

		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
