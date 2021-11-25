package com.test.atmira.backend.asteroids.demo.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.test.atmira.backend.asteroids.demo.model.Asteroids;
import com.test.atmira.backend.asteroids.demo.model.Response;

public class Service {

	private static String url = "https://api.nasa.gov/neo/rest/v1/feed?start_date=2020-09-09&end_date=2020-09-16&api_key=DEMO_KEY";

	private static LinkedHashMap<String, Object> info;

	public static Response getData() {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Asteroids> asteroides = restTemplate.getForEntity(url, Asteroids.class);
		List<Object> listResponse = Utils.topThree(Utils.sort(selectAsteroids(asteroides.getBody())));

		return mapper(listResponse);
	}

	private static Response mapper(List<Object> listTop) {

		Response response = new Response();

		for (Object listElement : listTop) {
			response = mapperToResponse((LinkedHashMap<String, Object>) listElement);
		}
		return response;

	}

	private static Response mapperToResponse(LinkedHashMap<String, Object> detail) {
		Response response = new Response();
		String name = (String) detail.get("name");
		LinkedHashMap<String, Object> diameter = (LinkedHashMap<String, Object>) detail.get("estimated_diameter");
		LinkedHashMap<String, Object> kilomneter = (LinkedHashMap<String, Object>) diameter.get("kilometers");
		Double diameterMin = (Double) kilomneter.get("estimated_diameter_min");
		Double diameterMax = (Double) kilomneter.get("estimated_diameter_max");
		Double average = Utils.average(diameterMin, diameterMax);
		List<Object> arrayCloseApproachData = (List<Object>) detail.get("close_approach_data");
		LinkedHashMap<String, Object> closeApproachData = (LinkedHashMap<String, Object>) arrayCloseApproachData.get(0);
		LinkedHashMap<String, Object> relativeVelocity = (LinkedHashMap<String, Object>) closeApproachData
				.get("relative_velocity");
		String kilometersPerHour = (String) relativeVelocity.get("kilometers_per_hour");
		String date = (String) closeApproachData.get("close_approach_date");
		String planeta = (String) closeApproachData.get("orbiting_body");

		response.setDiametro(average);
		response.setFecha(date);
		response.setNombre(name);
		response.setVelocidad(kilometersPerHour);
		response.setPlaneta(planeta);
		return response;
	}

	private static LinkedHashMap<Double, Object> selectAsteroids(Asteroids asteroid) {

		LinkedHashMap<Double, Object> result = new LinkedHashMap<Double, Object>();
		asteroid.getNearEarthObjects().forEach((k, v) -> {
			LinkedHashMap<Double, Object> list = recorrerElementos(
					(List<Object>) asteroid.getNearEarthObjects().get(k));
			result.putAll(list);
		}); // k son los días

		return result;

	}

	private static LinkedHashMap<Double, Object> recorrerElementos(List<Object> elements) {
		LinkedHashMap<Double, Object> potentiallyHazardousAsteroid = new LinkedHashMap<Double, Object>();
		elements.forEach(element -> {
			info = (LinkedHashMap<String, Object>) element;
			if ((Boolean) info.get("is_potentially_hazardous_asteroid")) {
				potentiallyHazardousAsteroid
						.put(getDiameter((LinkedHashMap<String, Object>) info.get("estimated_diameter")), info);
			}
		});

		return potentiallyHazardousAsteroid;
	}

	private static Double getDiameter(LinkedHashMap<String, Object> objectDiameter) {
		LinkedHashMap<String, Object> kilomneter = (LinkedHashMap<String, Object>) objectDiameter.get("kilometers");
		Double diameterMin = (Double) kilomneter.get("estimated_diameter_min");
		Double diameterMax = (Double) kilomneter.get("estimated_diameter_max");

		return Utils.average(diameterMin, diameterMax);
	}

}
