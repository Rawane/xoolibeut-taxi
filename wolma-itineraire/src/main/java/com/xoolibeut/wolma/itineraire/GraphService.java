package com.xoolibeut.wolma.itineraire;

import java.util.List;

/**
 * Service pour faciliter l'aglgo pour déterminer l'itinéraire.
 * 
 * @author rgayeelhadji
 *
 */
public interface GraphService {
	/**
	 * determiner secteur.
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 */
	String determineSecteur(Double lat, Double lon);

	/**
	 * determiner secteur voisin
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 */
	List<String> determineSecteurVoisins(Double lat, Double lon);

	/**
	 * déterminer orientation.
	 * 
	 * @param pointA
	 * @param pointB
	 * @return
	 */
	Orientation determineOrientation(PointGeographique pointA, PointGeographique pointB);

	/**
	 * déterminer orientation nord ouest ou nord sud ..
	 * 
	 * @param latA
	 * @param lonA
	 * @param latB
	 * @param lonB
	 * @return
	 */

	Orientation determineOrientationAversB(Double latA, Double lonA, Double latB, Double lonB);

	/**
	 * determiner un point commun, une intersection.
	 * 
	 * @param pointA
	 * @param pointB
	 * @param graphItineraire
	 * @return
	 */
	PointGeographique determinerPointCommunAtoB(PointGeographique pointA, PointGeographique pointB,
			GraphItineraire graphItineraire);

	/**
	 * trouver un voisin sur la même route
	 * 
	 * @param pointA
	 * @param pointB
	 * @param route
	 * @param graph
	 * @return
	 */
	PointGeographique trouveVoisinSurLaRouteAtoB(PointGeographique pointA, PointGeographique pointB, String route,
			GraphItineraire graph);

	/**
	 * 
	 * @param pointA
	 * @param pointB
	 * @return
	 */
	String verifieAppartenanceRoute(PointGeographique pointA, PointGeographique pointB);

	/**
	 * détermine point intersection les plus prioritaire. 
	 * @param pointA
	 * @param pointB
	 * @param graphItineraire
	 * @return
	 */
	List<PointGeographique> determinePointIntersectionRouteAvecPoidsFort(PointGeographique pointA,
			PointGeographique pointB, GraphItineraire graphItineraire);

}
