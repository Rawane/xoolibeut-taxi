package com.xoolibeut.wolma.model.transform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 
 * @author rgayeelhadji
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PointGeographique {
	private String id;
	/**
	 * latitude.
	 */
	private Double a;
	/**
	 * longitude
	 */
	private Double o;
	/**
	 * le poids
	 */
	private int p;
	/**
	 * poidsIntersection
	 */
	private int pi;
	/**
	 * secteur s
	 */
	private String s;
	/**
	 * o indique c'est un point intersection.
	 */
	private String i = "n";
	/**
	 * voisin nord est
	 */
	private Set<String> v = new HashSet<String>();
	
	private List<RoutePosition> r = new ArrayList<RoutePosition>();

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
		return a;
	}

	public void setLat(Double lat) {
		this.a = lat;
	}

	public Double getLon() {
		return o;
	}

	public void setLon(Double lon) {
		this.o = lon;
	}

	public int getPoids() {
		return p;
	}

	public void setPoids(int poids) {
		this.p = poids;
	}

	public String getS() {
		return s;
	}

	public void setSecteur(String secteur) {
		this.s = secteur;
	}

	public String getI() {
		return i;
	}

	public void setIn(String in) {
		this.i = in;
	}

	public int getPoidsIntersection() {
		return pi;
	}

	public void setPoidsIntersection(int poidsIntersection) {
		this.pi = poidsIntersection;
	}

	public List<RoutePosition> getRoutes() {
		return r;
	}

	public void setRoutes(List<RoutePosition> routes) {
		this.r = routes;
	}

	public Set<String> getVoisins() {
		return v;
	}

	public void setVoisins(Set<String> v) {
		this.v = v;
	}

}
