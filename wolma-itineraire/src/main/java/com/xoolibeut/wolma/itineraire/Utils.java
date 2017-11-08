package com.xoolibeut.wolma.itineraire;

public class Utils {
	public static Double calculedistanceAaB(PointGeographique pointA, PointGeographique pointB) {

		return calculedistanceAaB(pointA.getLat(), pointA.getLon(), pointB.getLat(), pointB.getLon());
	}

	public static Double calculedistanceAaB(PointItineraire pointA, PointItineraire pointB) {

		return calculedistanceAaB(pointA.getLat(), pointA.getLon(), pointB.getLat(), pointB.getLon());
	}

	public static Double calculedistanceAaB(double latA, double latB, PointGeographique pointB) {
		return calculedistanceAaB(latA, latB, pointB.getLat(), pointB.getLon());
	}

	/**
	 * calcul de la distance en m.
	 * 
	 * @param latA
	 * @param lonA
	 * @param latB
	 * @param lonB
	 * @return
	 */
	public static Double calculedistanceAaBOld(Double latA, Double lonA, Double latB, Double lonB) {
		Double rayonTerre = 6372.795477598;
		Double latConvertiA = latA * Math.PI / 180;
		Double lonConvertiA = lonA * Math.PI / 180;
		Double latConvertiB = latB * Math.PI / 180;
		Double lonConvertiB = lonB * Math.PI / 180;
		Double distance = rayonTerre * Math.acos(Math.sin(latConvertiA) * Math.sin(latConvertiB)
				+ Math.cos(latConvertiA) * Math.cos(latConvertiB) * Math.cos(lonConvertiB - lonConvertiA));
		return 1000 * distance;
	}

	public static Double calculedistanceAaB(Double latitudeA, Double longitudeA, Double latitudeB, Double longitudeB) {
		Double rayonTerre = 6372.795477598;
		double latRadianA = latitudeA * Math.PI / 180;
		double latRadianB = latitudeB * Math.PI / 180;
		double lonRadianA = longitudeA * Math.PI / 180;
		double lonradianB = longitudeB * Math.PI / 180;
		Double distance = rayonTerre * Math.acos(Math.sin(latRadianA) * Math.sin(latRadianB)
				+ Math.cos(latRadianA) * Math.cos(latRadianB) * Math.cos(lonradianB - lonRadianA));
		return 1000 * distance;
	}

	public static void main(String[] args) {
		System.out.println(Utils.calculedistanceAaB(14.413440344637204, -16.97196006818558, 14.413440344637204,
				-16.948986053466797));
	}
}
