package com.test.atmira.backend.asteroids.demo.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.test.atmira.backend.asteroids.demo.model.Asteroids;
import com.test.atmira.backend.asteroids.demo.model.AsteroidsResponse;
import com.test.atmira.backend.asteroids.demo.model.Response;
import com.test.atmira.backend.asteroids.demo.utils.Constant;
import com.test.atmira.backend.asteroids.demo.utils.Utils;

@org.springframework.stereotype.Service
public class Service {

	private static LinkedHashMap<String, Object> info;

	public Response getData() {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Asteroids> asteroides = restTemplate.getForEntity(Constant.URL, Asteroids.class);
		TreeMap<Double, Object> sort = Utils.sort(selectAsteroids(asteroides.getBody()));
		List<Object> listResponse = Utils.topThree(sort);

		return mapper(listResponse);
	}

	private Response mapper(List<Object> listTop) {

		Response response = new Response();
		List<AsteroidsResponse> listData = new ArrayList<AsteroidsResponse>();
		for (Object listElement : listTop) {
			listData.add(mapperToResponse((LinkedHashMap<String, Object>) listElement));
		}
		response.setData(listData);
		return response;

	}

	private AsteroidsResponse mapperToResponse(LinkedHashMap<String, Object> detail) {
		AsteroidsResponse response = new AsteroidsResponse();
		String name = (String) detail.get(Constant.NAME);
		LinkedHashMap<String, Object> diameter = (LinkedHashMap<String, Object>) detail.get(Constant.ESTIMATED_DIAMETER);
		LinkedHashMap<String, Object> kilomneter = (LinkedHashMap<String, Object>) diameter.get(Constant.KILOMETERS);
		Double diameterMin = (Double) kilomneter.get(Constant.ESTIMATED_DIAMETER_MIN);
		Double diameterMax = (Double) kilomneter.get(Constant.ESTIMATED_DIAMETER_MAX);
		Double average = Utils.average(diameterMin, diameterMax);
		List<Object> arrayCloseApproachData = (List<Object>) detail.get(Constant.CLOSE_APPROACH_DATA);
		LinkedHashMap<String, Object> closeApproachData = (LinkedHashMap<String, Object>) arrayCloseApproachData.get(0);
		LinkedHashMap<String, Object> relativeVelocity = (LinkedHashMap<String, Object>) closeApproachData
				.get(Constant.RELATIVE_VELOCITY);
		String kilometersPerHour = (String) relativeVelocity.get(Constant.KILOMETERS_PER_HOUR);
		String date = (String) closeApproachData.get(Constant.CLOSE_APPROACH_DATE);
		String planeta = (String) closeApproachData.get(Constant.ORBITING_DATA);

		response.setDiametro(average);
		response.setFecha(date);
		response.setNombre(name);
		response.setVelocidad(kilometersPerHour);
		response.setPlaneta(planeta);
		return response;
	}

	private LinkedHashMap<Double, Object> selectAsteroids(Asteroids asteroid) {

		LinkedHashMap<Double, Object> result = new LinkedHashMap<Double, Object>();
		asteroid.getNearEarthObjects().forEach((k, v) -> {
			LinkedHashMap<Double, Object> list = recorrerElementos(
					(List<Object>) asteroid.getNearEarthObjects().get(k));
			result.putAll(list);
		});

		return result;

	}

	private LinkedHashMap<Double, Object> recorrerElementos(List<Object> elements) {
		LinkedHashMap<Double, Object> potentiallyHazardousAsteroid = new LinkedHashMap<Double, Object>();
		elements.forEach(element -> {
			info = (LinkedHashMap<String, Object>) element;
			if ((Boolean) info.get(Constant.IS_POTENTIALLY_HAZARDOUS_ASTEROID)) {
				potentiallyHazardousAsteroid
						.put(Utils.getDiameter((LinkedHashMap<String, Object>) info.get(Constant.ESTIMATED_DIAMETER)), info);
			}
		});

		return potentiallyHazardousAsteroid;
	}

}
