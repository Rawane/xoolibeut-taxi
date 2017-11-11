package com.xoolibeut.wolma.itineraire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

	/**
	 * determiner un point commun.
	 */
	public PointGeographique determinerPointCommunAtoB(PointGeographique pointA, PointGeographique pointB,
			GraphItineraire graphItineraire) {
		List<PointGeographique> pointRoutesA = new ArrayList<PointGeographique>();
		List<PointGeographique> pointRoutesB = new ArrayList<PointGeographique>();
		for (RoutePosition routeA : pointA.getRoutes()) {
			pointRoutesA.addAll(graphItineraire.getWay(routeA.getRoute()).getPoints());
		}
		for (RoutePosition routeB : pointB.getRoutes()) {
			pointRoutesB.addAll(graphItineraire.getWay(routeB.getRoute()).getPoints());
		}
		PointGeographique pointCommun = null;
		for (PointGeographique point : pointRoutesA) {
			if (pointRoutesB.contains(point)) {
				pointCommun = point;
				break;
			}
		}
		return pointCommun;
	}

	/**
	 * détermine point intersection les plus prioritaire.
	 * @param pointA
	 * @param pointB
	 * @param graphItineraire
	 * @return
	 */
	public List<PointGeographique> determinePointIntersectionRouteAvecPoidsFort(PointGeographique pointA,
			PointGeographique pointB, GraphItineraire graphItineraire) {
		List<PointGeographique> pointRoutesA = new ArrayList<PointGeographique>();
		List<PointGeographique> pointResult = new ArrayList<PointGeographique>();
		for (RoutePosition routeA : pointA.getRoutes()) {
			pointRoutesA.addAll(graphItineraire.getWay(routeA.getRoute()).getPoints());
		}
		for (PointGeographique point : pointRoutesA) {
			double d1 = Utils.calculedistanceAaB(point, pointA) - Utils.calculedistanceAaB(pointA, pointB);
			double d2 = Utils.calculedistanceAaB(point, pointB) - Utils.calculedistanceAaB(pointA, pointB);
			if (point.getPoids() > pointA.getPoids() && d1 < 0 && d2 < 0) {
				addPointIntersectionProche(point, pointA, pointResult);
			}
		}
		Comparator<PointGeographique> comparator = new ComparatorPoids();
		Collections.sort(pointResult, comparator);
		return pointResult;
	}

	private void addPointIntersectionProche(PointGeographique pointAdd, PointGeographique pointA,
			List<PointGeographique> pointResult) {
		if (pointResult.isEmpty()) {
			pointResult.add(pointAdd);
		} else {
			boolean existePoint = false;
			PointGeographique pointReplace = null;
			for (PointGeographique pointGeographique : pointResult) {
				if (pointGeographique.getPoids() == pointAdd.getPoids()) {
					existePoint = true;
					if (Utils.calculedistanceAaB(pointAdd, pointA) < Utils.calculedistanceAaB(pointGeographique,
							pointA)) {
						pointReplace = pointGeographique;
					}

				}
			}

			if (!existePoint) {
				pointResult.add(pointAdd);
			}
			if (pointReplace != null) {
				pointResult.remove(pointReplace);
				pointResult.add(pointAdd);
			}
		}

	}

	/**
	 * Voisin sur la route A vers B
	 */
	public PointGeographique trouveVoisinSurLaRouteAtoB(PointGeographique pointA, PointGeographique pointB,
			String route, GraphItineraire graph) {
		List<PointGeographique> voisTrouves = new ArrayList<PointGeographique>();
		for (String voisin : pointA.getVoisins()) {
			// meme route on utilise tous les points
			PointGeographique pointVois = graph.getPoint(voisin);
			if (pointVois != null && pointVois.getRoutes().contains(new RoutePosition(route))) {
				voisTrouves.add(pointVois);
			}
		}
		if (!voisTrouves.isEmpty()) {
			ComparatorAtoB comparatorAtoB = new ComparatorAtoB(pointB);
			Collections.sort(voisTrouves, comparatorAtoB);
			return voisTrouves.get(0);
		}
		return null;
	}

	/**
	 * appartenance à une meme route.
	 */
	public String verifieAppartenanceRoute(PointGeographique pointA, PointGeographique pointB) {
		for (RoutePosition route : pointA.getRoutes()) {
			if (pointB.getRoutes().contains(route)) {
				return route.getRoute();
			}
		}
		return null;

	}
}
