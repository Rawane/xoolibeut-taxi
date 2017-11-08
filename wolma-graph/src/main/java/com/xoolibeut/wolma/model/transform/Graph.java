package com.xoolibeut.wolma.model.transform;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "graph")
@XmlAccessorType(XmlAccessType.FIELD)
public class Graph {
	@XmlElement(name = "pt")
	private List<PointGeographique> points = new ArrayList<PointGeographique>(1000);
	@XmlElement(name = "w")
	private List<WayWolma> ways = new ArrayList<WayWolma>(300);

	public List<PointGeographique> getPoints() {
		return points;
	}

	public void setPoints(List<PointGeographique> points) {
		this.points = points;
	}

	public List<WayWolma> getWays() {
		return ways;
	}

	public void setWays(List<WayWolma> ways) {
		this.ways = ways;
	}

}
