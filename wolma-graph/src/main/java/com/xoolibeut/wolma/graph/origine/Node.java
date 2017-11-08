package com.xoolibeut.wolma.graph.origine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Node d'origine
 * @author rgayeelhadji
 *
 */
public class Node implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private Double lat;

	private Double lon;

	private List<Tag> tags = new ArrayList<Tag>();

	@Override
	public boolean equals(Object object) {
		if (null == object || object.getClass() != this.getClass()) {
			return false;
		}
		Node node = (Node) object;
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

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
