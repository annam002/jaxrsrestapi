package com.tut3c.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Coordinate {

	private static Map<String, Integer> coordinateMap = createCoordinateMap();
	private final String field;
	private final Integer coordinate;

	public Coordinate(String field) {
		this.field = field;
		this.coordinate = coordinateMap.get(field.toUpperCase());
	}
	
	@XmlTransient
	public Integer getCoordinate() {
		return coordinate;
	}
	
	public String getField() {
		return field;
	}
	
	private static Map<String, Integer> createCoordinateMap() {
		Map<String, Integer> map = new HashMap<>();
		map.put("A1", 0);
		map.put("A2", 1);
		map.put("A3", 2);
		map.put("B1", 3);
		map.put("B2", 4);
		map.put("B3", 5);
		map.put("C1", 6);
		map.put("C2", 7);
		map.put("C3", 8);
		return map;
	}
}
