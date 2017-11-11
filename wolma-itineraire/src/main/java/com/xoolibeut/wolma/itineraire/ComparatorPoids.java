package com.xoolibeut.wolma.itineraire;

import java.util.Comparator;

/**
 * Compararer des point par rapport à distance.
 * 
 * @author rgayeelhadji
 *
 */
public class ComparatorPoids implements Comparator<PointGeographique> {

	public int compare(PointGeographique pointA, PointGeographique pointB) {

		return pointB.getPoids() - pointA.getPoids();
	}

}
