package com.test.atmira.backend.asteroids.demo.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Response {

	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String error;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public List<AsteroidsResponse> data;

	public void setError(String error) {
		this.error = error;
	}

	public void setData(List<AsteroidsResponse> data) {
		this.data = data;
	}

}
