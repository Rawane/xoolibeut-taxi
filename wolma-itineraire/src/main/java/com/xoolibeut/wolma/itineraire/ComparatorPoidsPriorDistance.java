package com.xoolibeut.wolma.itineraire;

import java.util.Comparator;

/**
 * Compararer des point par rapport à distance.
 * 
 * @author rgayeelhadji
 *
 */
public class ComparatorPoidsPriorDistance implements Comparator<PointGeographique> {
	private PointGeographique pointRepere;

	/**
	* 
	*/
	public ComparatorPoidsPriorDistance(PointGeographique point) {
		this.pointRepere = point;

	}

	public int compare(PointGeographique pointA, PointGeographique pointB) {
		double distance = Utils.calculedistanceAaB(pointRepere.getLat(), pointRepere.getLon(), pointA)
				- Utils.calculedistanceAaB(pointRepere.getLat(), pointRepere.getLon(), pointB);
		int result = (int) (100 * distance);
		int poids = pointB.getPoids() - pointA.getPoids();
		if (poids == 0) {
			return result;
		}
		return poids;
	}

}
