package com.xoolibeut.wolma.graph.origine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Way implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private List<Node> nodes = new ArrayList<Node>();

	private List<Tag> tags = new ArrayList<Tag>();

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
