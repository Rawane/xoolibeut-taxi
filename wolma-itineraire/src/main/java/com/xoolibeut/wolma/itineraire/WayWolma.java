package com.xoolibeut.wolma.itineraire;

public class WayWolma {
	private String id;
	private String highway;
	private String nom;
	private String ref;
	private Integer vitesse;
	private String sensUnique="N";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHighway() {
		return highway;
	}

	public void setHighway(String highway) {
		this.highway = highway;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Integer getVitesse() {
		return vitesse;
	}

	public void setVitesse(Integer vitesse) {
		this.vitesse = vitesse;
	}

	public String getSensUnique() {
		return sensUnique;
	}

	public void setSensUnique(String sensUnique) {
		this.sensUnique = sensUnique;
	}

}
