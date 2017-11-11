package com.xoolibeut.wolma.itineraire;

public class RoutePosition {
	private String route;
	private int position;

	public RoutePosition(String route) {
		this.route = route;
	}
	public RoutePosition() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((route == null) ? 0 : route.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (route == null)
			return ((RoutePosition) obj).getRoute() == null;
		return route.equals(((RoutePosition) obj).getRoute());

	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
