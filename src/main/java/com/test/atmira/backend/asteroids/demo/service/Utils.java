package com.test.atmira.backend.asteroids.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Utils {

	public static Double average(Double min, Double max) {
		Double result = (min + max) / 2;

		return result;
	}

	public static TreeMap<Double, Object> sort(LinkedHashMap<Double, Object> potentiallyHazardousAsteroid) {
		
		Map<Double, Object> map = new TreeMap<Double, Object>(Collections.reverseOrder());
		map.putAll(potentiallyHazardousAsteroid);
		
		return  (TreeMap<Double, Object>) map;
	}
	
	public static List<Object> topThree(TreeMap<Double, Object> sort) {
		List<Object> top = new ArrayList<Object>();
	
		
		for (int i = 1; i<=3; i++) {
			Double key = sort.firstKey();
			top.add(sort.get(key));
			sort.remove(key);
		}
		
		return top;
	}

}
