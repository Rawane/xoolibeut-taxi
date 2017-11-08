package com.xoolibeut.wolma.itineraire;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author rgayeelhadji
 * 
 */
public class PointGeographique {
	private String id;
	private Double lat;
	private Double lon;
	private int poids;
	private int poidsIntersection;
	private String secteur;
	/**
	 * o indique c'est un point intersection.
	 */
	private String in = "n";
	/**
	 * voisins
	 */
	private List<String> voisins = new ArrayList<String>();

	private List<String> routes = new ArrayList<String>();

	@Override
	public boolean equals(Object object) {
		if (null == object || object.getClass() != this.getClass()) {
			return false;
		}
		PointGeographique node = (PointGeographique) object;
		return equals(this.id, node.getId());
	}

	private boolean equals(String id, String idRef) {
		if (null == id) {
			return null == idRef;
		} else {
			return id.equals(idRef);
		}
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public int getPoids() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids = poids;
	}

	public String getSecteur() {
		return secteur;
	}

	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}	public int getPoidsIntersection() {
		return poidsIntersection;
	}

	public void setPoidsIntersection(int poidsIntersection) {
		this.poidsIntersection = poidsIntersection;
	}

	public List<String> getRoutes() {
		return routes;
	}

	public void setRoutes(List<String> routes) {
		this.routes = routes;
	}

	public List<String> getVoisins() {
		return voisins;
	}

	public void setVoisins(List<String> voisins) {
		this.voisins = voisins;
	}

}
