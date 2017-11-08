package com.xoolibeut.wolma.itineraire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Arbre de l'algorithe alogo dikistrat + A* optimisé.
 * 
 * @author rgayeelhadji
 *
 */
public class GraphItineraire {
	/**
	 * tous les points du réseaux.
	 */
	private Map<String, PointGeographique> mapPoints = new HashMap<String, PointGeographique>(1000);
	/**
	 * les points qui sont dans des intersections
	 */
	private Map<String, PointGeographique> mapPointsGraph = new HashMap<String, PointGeographique>(500);
	/**
	 * points regroupé par secteur.
	 */
	private Map<String, List<PointGeographique>> mapPointsSecteur = new HashMap<String, List<PointGeographique>>(20);

	/**
	 * les routes.
	 */

	private Map<String, WayWolma> mapWay = new HashMap<String, WayWolma>(100);
	/**
	 * distance considérée comme proche.
	 */
	private Double distanceLocale;
	/**
	 * distance moyen utilisant les routes secondaires
	 */
	private Double distanceMedium;
	/**
	 * distance lointain.
	 */
	private Double distanceLointain;
	/**
	 * distance très lointain.
	 */
	private Double distanceTresLointain;

	public void putPoint(PointGeographique pointGeographique) {
		mapPoints.put(pointGeographique.getId(), pointGeographique);
	}

	public PointGeographique getPoint(String id) {
		return mapPoints.get(id);
	}

	public void putPointGraph(PointGeographique pointGeographique) {
		mapPointsGraph.put(pointGeographique.getId(), pointGeographique);
	}

	public PointGeographique getPointGraphique(String id) {
		return mapPointsGraph.get(id);
	}

	public void putPointSecteur(PointGeographique pointGeographique) {
		List<PointGeographique> points = mapPointsSecteur.get(pointGeographique.getSecteur());
		if (points == null) {
			points = new ArrayList<PointGeographique>();
			mapPointsSecteur.put(pointGeographique.getSecteur(), points);
		}
		points.add(pointGeographique);
	}

	public List<PointGeographique> getListPointSecteur(String secteur) {
		return mapPointsSecteur.get(secteur);
	}

	public void putWay(WayWolma wayWolma) {
		getMapWay().put(wayWolma.getId(), wayWolma);
	}

	public WayWolma getWay(String id) {
		return getMapWay().get(id);
	}

	public Map<String, PointGeographique> getMapPoints() {
		return mapPoints;
	}

	public void setMapPoints(Map<String, PointGeographique> mapPoints) {
		this.mapPoints = mapPoints;
	}

	public Map<String, PointGeographique> getMapPointsGraph() {
		return mapPointsGraph;
	}

	public void setMapPointsGraph(Map<String, PointGeographique> mapPointsGraph) {
		this.mapPointsGraph = mapPointsGraph;
	}

	public Map<String, List<PointGeographique>> getMapPointsSecteur() {
		return mapPointsSecteur;
	}

	public void setMapPointsSecteur(Map<String, List<PointGeographique>> mapPointsSecteur) {
		this.mapPointsSecteur = mapPointsSecteur;
	}

	public Map<String, WayWolma> getMapWay() {
		return mapWay;
	}

	public void setMapWay(Map<String, WayWolma> mapWay) {
		this.mapWay = mapWay;
	}

	public Double getDistanceLocale() {
		return distanceLocale;
	}

	public void setDistanceLocale(Double distanceLocale) {
		this.distanceLocale = distanceLocale;
	}

	public Double getDistanceMedium() {
		return distanceMedium;
	}

	public void setDistanceMedium(Double distanceMedium) {
		this.distanceMedium = distanceMedium;
	}

	public Double getDistanceLointain() {
		return distanceLointain;
	}

	public void setDistanceLointain(Double distanceLointain) {
		this.distanceLointain = distanceLointain;
	}

	public Double getDistanceTresLointain() {
		return distanceTresLointain;
	}

	public void setDistanceTresLointain(Double distanceTresLointain) {
		this.distanceTresLointain = distanceTresLointain;
	}

}
