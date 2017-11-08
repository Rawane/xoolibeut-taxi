package com.xoolibeut.wolma.itineraire;

public abstract class AbstractGraphService implements GraphService {
	/**
	 * determine orientation A vers B.
	 */
	public Orientation determineOrientation(PointGeographique pointA, PointGeographique pointB) {
		return determineOrientationAversB(pointA.getLat(), pointA.getLon(), pointB.getLat(), pointB.getLon());
	}

	/**
	 * determine orientation.
	 */
	public Orientation determineOrientationAversB(Double latA, Double lonA, Double latB, Double lonB) {
		if (latA >= latB) {
			if (lonA <= lonB) {
				return Orientation.NO;
			} else {
				return Orientation.NE;
			}
		} else {
			if (lonA <= lonB) {
				return Orientation.SO;
			} else {
				return Orientation.SE;
			}
		}
	}

}
