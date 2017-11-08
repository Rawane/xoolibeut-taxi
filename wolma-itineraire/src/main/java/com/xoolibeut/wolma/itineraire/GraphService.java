package com.xoolibeut.wolma.itineraire;

import java.util.List;

/**
 * Service pour faciliter l'aglgo pour déterminer l'itinéraire.
 * 
 * @author rgayeelhadji
 *
 */
public interface GraphService {

	String determineSecteur(Double lat, Double lon);

	List<String> determineSecteurVoisins(Double lat, Double lon);

	Orientation determineOrientation(PointGeographique pointA, PointGeographique pointB);

	Orientation determineOrientationAversB(Double latA, Double lonA, Double latB, Double lonB);


}
