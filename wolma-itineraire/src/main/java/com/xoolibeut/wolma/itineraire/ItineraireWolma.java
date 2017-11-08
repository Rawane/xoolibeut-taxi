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
		pointVisites.add(pointDepart);
		for (PointGeographique pointGeographique : listPointsSecteurDepart) {
			Double distance = Utils.calculedistanceAaB(latA, lonA, pointGeographique);
			LOGGER.debug(" distance A  " + distance+"   id bouton "+pointGeographique.getId());
		}
		for (PointGeographique pointGeographique : listPointsSecteurArrive) {
			Double distance = Utils.calculedistanceAaB(latB, lonB, pointGeographique);
			LOGGER.debug(" distance B " + distance+"   id bouton "+pointGeographique.getId());
		}
		Double distanceAB = Utils.calculedistanceAaB(latA, lonA, latB, lonB);
		if (distanceAB <= graph.getDistanceLocale()) {
			int maxIteration = 100;
			int compteIteration = 0;
			double maxEcartAutorise = 200;
			LOGGER.debug("point de départ " + pointDepart.getId());
			int indexPointDepart = 0;
			LOGGER.debug("Distance A vers B " + distanceAB);
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
					pointIgnoree.add(pointEncours);
					if (!pointEncours.equals(pointArrive) && pointIgnoree.contains(pointEncours)) {
						pointVisites.remove(pointVisites.size() - 1);
						if (pointVisites.isEmpty()) {
							indexPointDepart++;
							// recherche un autre départ point de départ
							// on peut aller jusqu'a 5 point de départ
							if (indexPointDepart < 5 && listPointsSecteurDepart.size() > indexPointDepart) {
								pointVisites.add(listPointsSecteurDepart.get(indexPointDepart));
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
							pointVisites.add(voisinSelect);
						} else {
							pointIgnoree.add(voisinSelect);
						}

					} else {
						pointVisites.add(voisinSelect);
					}
				}

			}
			// Trace itinéraire
			
			LOGGER.debug("Point de départ  " + pointDepart.getLat() + "   " + pointDepart.getLon());
			LOGGER.debug("Nombre de point départ  " + indexPointDepart);
			LOGGER.debug("Nombre d 'itération " + compteIteration);
			LOGGER.debug("Nombre point ignorés " + pointIgnoree.size());
			LOGGER.debug("Nombre point visités " + pointVisites.size());
			itineraires.add(traceItineraireComplet());

		} else {
			if (secteurA.equals(secteurB)) {
				// calcul itineraire locals
			} else {

			}
		}
	}

	private Itineraire traceItineraireComplet() {
		// construire itinéraire
		if (pointVisites.isEmpty()) {
			return null;
		}
		int sizePointVisite = pointVisites.size();
		Itineraire itineraire = new Itineraire();
		if (sizePointVisite > 1) {
			for (int i = 0; i < sizePointVisite; i++) {
				PointGeographique pointEncours = pointVisites.get(i);
				itineraire.getChemins().add(new PointItineraire(pointEncours.getLat(), pointEncours.getLon()));
				for (String id : pointEncours.getVoisins()) {
					PointGeographique voisin = graph.getPoint(id);
					if (voisin != null && "n".equals(voisin.getIn())) {
						PointGeographique pointVisiteSuiv = pointVisites.get(i + 1);
						if (pointVisiteSuiv.getRoutes().contains(voisin.getRoutes().get(0))) {
							PointGeographique voisinPrecedant = pointEncours;
							int compte = 0;
							while (voisin != null && !pointVisiteSuiv.equals(voisin)) {
								compte++;
								itineraire.getChemins().add(new PointItineraire(voisin.getLat(), voisin.getLon()));
								for (String idV : voisin.getVoisins()) {
									if (!idV.equals(voisinPrecedant.getId())) {
										voisinPrecedant = voisin;
										voisin = graph.getPoint(idV);
									}
								}
								LOGGER.debug("recherche proche non intersection " + compte);
							}

						}
					}
				}
			}
			itineraire.getChemins().add(new PointItineraire(pointVisites.get(sizePointVisite - 1).getLat(),
					pointVisites.get(sizePointVisite - 1).getLon()));
			double distance = 0;
			for (int k = 0; k < itineraire.getChemins().size() - 1; k++) {
				distance = distance
						+ Utils.calculedistanceAaB(itineraire.getChemins().get(k), itineraire.getChemins().get(k + 1));
			}
			itineraire.setDistance(distance);
			// calcule de distance

		} else {
			itineraire.getChemins()
					.add(new PointItineraire(pointVisites.get(0).getLat(), pointVisites.get(0).getLon()));

		}
		return itineraire;
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
