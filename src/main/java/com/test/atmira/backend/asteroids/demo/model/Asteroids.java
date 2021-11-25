package com.test.atmira.backend.asteroids.demo.model;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Asteroids {

	@JsonProperty("links")
	private LinkedHashMap<Object, Object> links;

	@JsonProperty("element_count")
	private Integer elementCount;
	
	@JsonProperty("near_earth_objects")
	private LinkedHashMap<Object, Object> nearEarthObjects;

	public LinkedHashMap<Object, Object> getLinks() {
		return links;
	}

	public Integer getElementCount() {
		return elementCount;
	}

	public LinkedHashMap<Object, Object> getNearEarthObjects() {
		return nearEarthObjects;
	}

	public Asteroids() {}

}
