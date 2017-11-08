package com.xoolibeut.wolma.graph.origine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Mapping root openstreet map.
 * @author rgayeelhadji
 *
 */
public class Osm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Node> nodes = new ArrayList<Node>(2000);

	private List<Way> ways = new ArrayList<Way>(1000);

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Way> getWays() {
		return ways;
	}

	public void setWays(List<Way> ways) {
		this.ways = ways;
	}
}
