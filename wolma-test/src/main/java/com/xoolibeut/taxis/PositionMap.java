package com.xoolibeut.taxis;

import java.util.ArrayList;
import java.util.List;

public class PositionMap {
	private List<PointMap> points = new ArrayList<PointMap>();

	private List<PointMap> pointsClients = new ArrayList<PointMap>();

	public PositionMap() {
		load();
		loadClients();
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
		points.add(new PointMap(14.432691530866265, -16.96735382080078));
		points.add(new PointMap(14.429948489057892, -16.974949836730957));
		points.add(new PointMap(14.42824446122106, -16.980013847351074));
		points.add(new PointMap(14.4202228867615, -16.97662353515625));
		points.add(new PointMap(14.43057191061816,-16.991515159606934 ));
		points.add(new PointMap(14.415359926406207,-16.96834087371826 ));

	}

	private void loadClients() {
		pointsClients.add(new PointMap(14.418497, -16.959393));
		pointsClients.add(new PointMap(14.425050412690583, -16.961088180541992));
		pointsClients.add(new PointMap(14.410004312441648, -16.9698429107666));
		pointsClients.add(new PointMap(14.401857381386938, -16.955080032348633));
		pointsClients.add(new PointMap(14.446009348782093, -16.980743408203125));
		pointsClients.add(new PointMap(14.444347002725246, -17.005634307861328));
		pointsClients.add(new PointMap(14.454487120378943, -17.027435302734375));
		pointsClients.add(new PointMap(14.419896614283617, -16.975936889648438));
		pointsClients.add(new PointMap(14.414160795262095, -16.973705291748047));
		pointsClients.add(new PointMap(14.419474745921393, -16.95542335510254));
		pointsClients.add(new PointMap(14.425916987499027, -16.95713996887207));
	}

	public List<PointMap> getPoints() {
		return points;
	}	

	public List<PointMap> getPointsClients() {
		return pointsClients;
	}
}
