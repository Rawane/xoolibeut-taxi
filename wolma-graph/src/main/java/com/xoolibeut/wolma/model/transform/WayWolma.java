package com.xoolibeut.wolma.model.transform;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class WayWolma {
	private String id;
	/**
	 * highway.
	 */
	private String h;
	/**
	 * String nom.
	 */
	private String n;
	/**
	 * ref de la route ex N 1
	 */
	private String r;
	/**
	 * vitesse.
	 */
	private Integer v;
	/**
	 * sens unique.
	 * 
	 */
	private String s = "N";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHighway() {
		return h;
	}

	public void setHighway(String highway) {
		this.h = highway;
	}

	public String getNom() {
		return n;
	}

	public void setNom(String nom) {
		this.n = nom;
	}

	public String getRef() {
		return r;
	}

	public void setRef(String ref) {
		this.r = ref;
	}

	public Integer getVitesse() {
		return v;
	}

	public void setVitesse(Integer vitesse) {
		this.v = vitesse;
	}

	public String getSensUnique() {
		return s;
	}

	public void setSensUnique(String sensUnique) {
		this.s = sensUnique;
	}

}
