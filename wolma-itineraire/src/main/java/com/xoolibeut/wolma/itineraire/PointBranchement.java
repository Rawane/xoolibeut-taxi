package com.xoolibeut.wolma.itineraire;

/**
 * Point de changement.
 * 
 * @author rgayeelhadji
 *
 */
public class PointBranchement {
	private PointGeographique point;
	private int index;
	private String routeA;
	private String routeB;

	public PointBranchement(PointGeographique pt, int index, String routeA, String routeB) {
		this.point = pt;
		this.index = index;
		this.routeA = routeA;
		this.routeB = routeB;
	}

	@Override
	public boolean equals(Object object) {
		if (null == object || object.getClass() != this.getClass()) {
			return false;
		}
		PointGeographique pointGeo = (PointGeographique) object;
		return equals(this.point.getId(), pointGeo.getId());
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
		return this.point.getId().hashCode();
	}

	public PointGeographique getPoint() {
		return point;
	}

	public void setPoint(PointGeographique point) {
		this.point = point;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getRouteA() {
		return routeA;
	}

	public void setRouteA(String routeA) {
		this.routeA = routeA;
	}

	public String getRouteB() {
		return routeB;
	}

	public void setRouteB(String routeB) {
		this.routeB = routeB;
	}

}
