package com.xoolibeut.wolma.itineraire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItineraireWolma {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItineraireWolma.class);

	private List<PointGeographique> pointVisites = new ArrayList<PointGeographique>();
	private List<PointGeographique> pointIgnoree = new ArrayList<PointGeographique>();
	private GraphService graphService;
	private GraphItineraire graph;
	private List<Itineraire> itineraires = new ArrayList<Itineraire>();

	public ItineraireWolma(GraphService graphService, GraphItineraire graph) {
		this.graphService = graphService;
		this.graph = graph;
	}

	public void calculeItineraire(double latA, double lonA, double latB, double lonB) {
		// calcule itineraire point le plus proche en privilegiant les grands axes
		resetItineraire();
		String secteurA = graphService.determineSecteur(latA, lonA);
		String secteurB = graphService.determineSecteur(latB, lonB);
		List<PointGeographique> listPointsSecteurDepart = graph.getListPointSecteur(secteurA);
		List<PointGeographique> listPointsSecteurArrive = null;
		if (secteurA.equals(secteurB)) {
			listPointsSecteurArrive = new ArrayList<PointGeographique>(listPointsSecteurDepart.size());
			listPointsSecteurArrive.addAll(listPointsSecteurDepart);
		} else {
			listPointsSecteurArrive = graph.getListPointSecteur(secteurB);
		}
		ComparatorAtoB comparatorDepart = new ComparatorAtoB(latA, lonA);

		ComparatorAtoB comparatorArrive = new ComparatorAtoB(latB, lonB);

		// on trie dabord les points du secterur d'arrivé au cas on est dans le meme
		// secteur
		Collections.sort(listPointsSecteurArrive, comparatorArrive);
		// TODO Au cas ou on a besoin de changer de point d'arrivé il faut cloner la
		// liste meme secteur
		PointGeographique pointArrive = listPointsSecteurArrive.get(0);
		Collections.sort(listPointsSecteurDepart, comparatorDepart);
		PointGeographique pointDepart = listPointsSecteurDepart.get(0);
		addPointVisite(pointDepart);
		Double distanceAB = Utils.calculedistanceAaB(latA, lonA, latB, lonB);
		LOGGER.debug("Point de départ  " + pointDepart.getId() + "   " + pointDepart.getLat() + "   "
				+ pointDepart.getLon());
		LOGGER.debug(
				"Point d'arrivé  " + pointArrive.getId() + "   " + pointArrive.getLat() + "   " + pointArrive.getLon());
		LOGGER.debug("Distance A vers B " + distanceAB);
		if (distanceAB <= graph.getDistanceLocale()) {
			String routeCommun = verifieAppartenanceRoute(pointDepart, pointArrive);
			if (routeCommun != null && "n".equals(graph.getWay(routeCommun).getSensUnique())) {
				LOGGER.debug("CAS 01 Itinéraire même route commun  " + routeCommun);
				// TODO cas deux points qui sont sur la même route
				while (!pointArrive.equals(pointVisites.get(pointVisites.size() - 1))) {
					PointGeographique pointGeographique = trouveVoisinSurLaRouteAtoB(
							pointVisites.get(pointVisites.size() - 1), pointArrive, routeCommun);
					LOGGER.debug("Voisin trouvé " + pointGeographique.getId() + "    " + pointGeographique.getRoutes());
					pointVisites.add(pointGeographique);
				}
				LOGGER.debug("CAS 01 Fin calcule ");
				itineraires.add(traceItineraireMemeRoute());
				LOGGER.debug("CAS 01 Fin itinéraire ");
			} else {
				// TODO Petite distance
				LOGGER.debug("CAS 02 Itinéraire petite distance ");
				calculeItinerairePetiteDistance(listPointsSecteurDepart, pointArrive, comparatorArrive);
			}

		} else {
			LOGGER.debug("Autre distance" + distanceAB);
			if (secteurA.equals(secteurB)) {
				// calcul itineraire locals
			} else {

			}
		}
	}

	/**
	 * 
	 */
	private void resetItineraire() {
		pointVisites.clear();
		pointIgnoree.clear();
		itineraires.clear();

	}

	private void calculeItinerairePetiteDistance(List<PointGeographique> listPointsSecteurDepart,
			PointGeographique pointArrive, ComparatorAtoB comparatorArrive) {
		int maxIteration = 100;
		int compteIteration = 0;
		double maxEcartAutorise = 200;
		int indexPointDepart = 0;
		while (!isPointArriveTrouve(pointArrive, compteIteration, maxIteration)) {
			compteIteration++;
			List<PointGeographique> voisins = new ArrayList<PointGeographique>();
			PointGeographique pointEncours = pointVisites.get(pointVisites.size() - 1);
			for (String id : pointEncours.getVoisins()) {
				PointGeographique voisin = graph.getPointGraphique(id);
				// on recupère que les voisins qui sont dans les intersections
				if (voisin != null && !pointIgnoree.contains(voisin) && !pointVisites.contains(voisin)) {
					voisins.add(voisin);
				}
			}
			if (voisins.isEmpty()) {
				addPointIgnore(pointEncours);
				if (!pointEncours.equals(pointArrive) && pointIgnoree.contains(pointEncours)) {
					pointVisites.remove(pointVisites.size() - 1);
					if (pointVisites.isEmpty()) {
						indexPointDepart++;
						// recherche un autre départ point de départ
						// on peut aller jusqu'a 5 point de départ
						if (indexPointDepart < 5 && listPointsSecteurDepart.size() > indexPointDepart) {
							addPointVisite(listPointsSecteurDepart.get(indexPointDepart));
						}

					}
				}

			} else {
				Collections.sort(voisins, comparatorArrive);
				PointGeographique voisinSelect = voisins.get(0);
				Orientation orientationVoisin = graphService.determineOrientation(voisinSelect, pointArrive);
				Orientation orientation = graphService.determineOrientation(pointEncours, pointArrive);
				if (!orientation.equals(orientationVoisin)) {
					double distanceVoisinArrive = Utils.calculedistanceAaB(voisinSelect, pointArrive);
					// Eviter les écarts très longue
					if (distanceVoisinArrive < maxEcartAutorise) {
						addPointVisite(voisinSelect);
					} else {
						addPointIgnore(voisinSelect);
					}

				} else {
					addPointVisite(voisinSelect);
				}
			}

		}
		// Trace itinéraire
		LOGGER.debug("Nombre de point départ essayé " + indexPointDepart);
		LOGGER.debug("Nombre d 'itération " + compteIteration);
		LOGGER.debug("Nombre point ignorés " + pointIgnoree.size());
		LOGGER.debug("Nombre point visités " + pointVisites.size());
		// lissage de l'itinéraire
		ameliorerItineraire();
		itineraires.add(traceItineraireComplet());
	}

	private String verifieAppartenanceRoute(PointGeographique pointA, PointGeographique pointB) {
		for (String route : pointA.getRoutes()) {
			if (pointB.getRoutes().contains(route)) {
				return route;
			}
		}
		return null;

	}

	private PointGeographique trouveVoisinSurLaRouteAtoB(PointGeographique pointA, PointGeographique pointB,
			String route) {
		List<PointGeographique> voisTrouves = new ArrayList<PointGeographique>();
		for (String voisin : pointA.getVoisins()) {
			// meme route on utilise tous les points
			PointGeographique pointVois = graph.getPoint(voisin);
			if (pointVois != null && pointVois.getRoutes().contains(route)) {
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

	private void addPointVisite(PointGeographique pointGeographique) {
		if (!pointVisites.contains(pointGeographique)) {
			pointVisites.add(pointGeographique);
		}
	}

	private void addPointIgnore(PointGeographique pointGeographique) {
		if (!pointVisites.contains(pointGeographique)) {
			pointVisites.add(pointGeographique);
		}
	}

	private void ameliorerItineraire() {
		int sizePointVisite = pointVisites.size();
		if (sizePointVisite > 3) {
			List<PointGeographique> pointsChangement = new ArrayList<PointGeographique>();
			for (int i = 1; i < sizePointVisite - 1; i++) {
				String routeCommun = verifieAppartenanceRoute(pointVisites.get(i - 1), pointVisites.get(i + 1));
				if (routeCommun == null) {
					pointsChangement.add(pointVisites.get(i));
					LOGGER.debug("Changement ---  " + pointVisites.get(i).getId());
				}
			}
		}

	}

	/**
	 * trace itinéraire même route
	 * 
	 * @return
	 */
	private Itineraire traceItineraireMemeRoute() {
		// construire itinéraire
		if (pointVisites.isEmpty()) {
			return null;
		}
		int sizePointVisite = pointVisites.size();
		Itineraire itineraire = new Itineraire();
		if (sizePointVisite > 1) {
			for (PointGeographique pointGeographique : pointVisites) {
				itineraire.getChemins()
						.add(new PointItineraire(pointGeographique.getLat(), pointGeographique.getLon()));
				itineraire.setPoids(itineraire.getPoids() + pointGeographique.getPoids());

			}
			calculeDistance(itineraire);
		} else {
			itineraire.getChemins()
					.add(new PointItineraire(pointVisites.get(0).getLat(), pointVisites.get(0).getLon()));
			itineraire.setPoids(pointVisites.get(0).getPoids());
		}
		return itineraire;
	}

	private Itineraire traceItineraireComplet() {
		// construire itinéraire
		if (pointVisites.isEmpty()) {
			return null;
		}
		int sizePointVisite = pointVisites.size();
		Itineraire itineraire = new Itineraire();
		if (sizePointVisite > 1) {
			for (int i = 0; i < sizePointVisite - 1; i++) {
				PointGeographique pointEncours = pointVisites.get(i);
				itineraire.getChemins().add(new PointItineraire(pointEncours.getLat(), pointEncours.getLon()));
				for (String id : pointEncours.getVoisins()) {
					PointGeographique voisin = graph.getPoint(id);
					if (voisin != null && "n".equals(voisin.getIn())) {
						PointGeographique pointVisiteSuiv = pointVisites.get(i + 1);
						String routeCommun = voisin.getRoutes().get(0);
						if (pointVisiteSuiv.getRoutes().contains(routeCommun)) {
							PointGeographique voisinPrecedant = pointEncours;
							int compte = 0;
							while (voisin != null && !pointVisiteSuiv.equals(voisin)) {
								compte++;
								itineraire.getChemins().add(new PointItineraire(voisin.getLat(), voisin.getLon()));
								boolean continueSearchVoisin = false;
								for (String idV : voisin.getVoisins()) {
									if (!idV.equals(voisinPrecedant.getId())) {
										voisinPrecedant = voisin;
										voisin = graph.getPoint(idV);
										if (voisin != null && voisin.getIn().equals("n")) {
											continueSearchVoisin = true;
										}
									}
								}
								System.out.println("voisin  " + voisin.getId());
								if (!continueSearchVoisin) {
									break;
								}
								LOGGER.debug("recherche proche non intersection " + compte);
							}

						}
					}
				}
			}
			itineraire.getChemins().add(new PointItineraire(pointVisites.get(sizePointVisite - 1).getLat(),
					pointVisites.get(sizePointVisite - 1).getLon()));
			calculeDistance(itineraire);
			// calcule de distance

		} else {
			itineraire.getChemins()
					.add(new PointItineraire(pointVisites.get(0).getLat(), pointVisites.get(0).getLon()));

		}
		return itineraire;
	}

	private void calculeDistance(Itineraire itineraire) {
		double distance = 0;
		for (int k = 0; k < itineraire.getChemins().size() - 1; k++) {
			distance = distance
					+ Utils.calculedistanceAaB(itineraire.getChemins().get(k), itineraire.getChemins().get(k + 1));

		}
		itineraire.setDistance(distance);
	}

	public boolean isPointArriveTrouve(PointGeographique pointArrive, int compteIteration, int maxIteration) {
		if (compteIteration >= maxIteration) {
			return true;
		}
		if (this.pointVisites.size() != 0) {
			PointGeographique lastPointVisite = this.pointVisites.get(this.pointVisites.size() - 1);
			return pointArrive.equals(lastPointVisite);
		}
		return false;
	}

	public List<Itineraire> getItineraires() {
		return itineraires;
	}

	public void setItineraires(List<Itineraire> itineraires) {
		this.itineraires = itineraires;
	}

	public List<PointGeographique> getPointIgnoree() {
		return pointIgnoree;
	}

	public void setPointIgnoree(List<PointGeographique> pointIgnoree) {
		this.pointIgnoree = pointIgnoree;
	}

}
