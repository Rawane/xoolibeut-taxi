package com.xoolibeut.taxis;

import java.util.ArrayList;
import java.util.List;

public class PositionMap {
	private List<PointMap> points = new ArrayList<PointMap>();

	public PositionMap() {
		load();
	}

	/**
	 * 
	 */
	private void load() {
		points.add(new PointMap(14.418497, -16.959393));
		points.add(new PointMap(14.420848, -16.960910));
		points.add(new PointMap(14.420598, -16.964180));
		points.add(new PointMap(14.419809, -16.969029));
		points.add(new PointMap(14.422884, -16.972334));
		points.add(new PointMap(14.427747, -16.974093));
		points.add(new PointMap(14.431072, -16.975252));
		points.add(new PointMap(14.4315893, -16.976926));
		points.add(new PointMap(14.439634, -16.978342));
		points.add(new PointMap(14.445750, -16.989102));
		points.add(new PointMap(14.445750, -17.001544));
		points.add(new PointMap(14.444104, -17.008523));
		points.add(new PointMap(14.439873, -17.003971));
		points.add(new PointMap(14.432015, -16.993874));
		points.add(new PointMap(14.427891, -16.988533));
		points.add(new PointMap(14.424186, -16.984058));
		points.add(new PointMap(14.428240, -16.98066));
		points.add(new PointMap(14.420971, -16.980233));
		points.add(new PointMap(14.415588, -16.976480));
		points.add(new PointMap(14.414680, -16.972005));
		points.add(new PointMap(14.410625, -16.969335));
		points.add(new PointMap(14.412548, -16.966303));
		points.add(new PointMap(14.408225, -16.963299));
		points.add(new PointMap(14.401741, -16.960381));
		points.add(new PointMap(14.410802, -16.952828));
		points.add(new PointMap(14.415540, -16.953686));
		points.add(new PointMap(14.405797, -16.957531));
		points.add(new PointMap(14.412223, -16.958389));
		points.add(new PointMap(14.416033, -16.961059));
		points.add(new PointMap(14.420717, -16.953733));
		points.add(new PointMap(14.424177, -16.956187));
		points.add(new PointMap(14.422674, -16.949800));
		points.add(new PointMap(14.425190, -16.962142));

	}

	public List<PointMap> getPoints() {
		return points;
	}

	public void setPoints(List<PointMap> points) {
		this.points = points;
	}
}
