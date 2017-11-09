package com.xoolibeut.wolma.itineraire;

import java.util.ArrayList;
import java.util.List;

public class Itineraire {
	private List<PointItineraire> chemins = new ArrayList<PointItineraire>();
	private Double distance;
	private int poids;
	public List<PointItineraire> getChemins() {
		return chemins;
	}

	public void setChemins(List<PointItineraire> chemins) {
		this.chemins = chemins;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public int getPoids() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids = poids;
	}

}
