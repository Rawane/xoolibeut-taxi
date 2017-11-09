package com.xoolibeut.wolma.itineraire;

import java.util.Comparator;

/**
 * Compararer des point par rapport à distance.
 * 
 * @author rgayeelhadji
 *
 */
public class ComparatorAtoB implements Comparator<PointGeographique> {
	private double latitudeRepere;
	private double longitudeRepere;

	/**
	* 
	*/
	public ComparatorAtoB(double latitude, double longitude) {
		this.latitudeRepere = latitude;
		this.longitudeRepere = longitude;
	}

	public ComparatorAtoB(PointGeographique point) {
		this.latitudeRepere = point.getLat();
		this.longitudeRepere = point.getLon();
	}

	public int compare(PointGeographique pointA, PointGeographique pointB) {
		double distance = Utils.calculedistanceAaB(latitudeRepere, longitudeRepere, pointA)
				- Utils.calculedistanceAaB(latitudeRepere, longitudeRepere, pointB);
		int result = (int) (100 * distance);
		return result;
	}

}
