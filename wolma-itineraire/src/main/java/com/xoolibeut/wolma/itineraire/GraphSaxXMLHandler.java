package com.xoolibeut.wolma.itineraire;

import java.util.function.Predicate;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Classe permettant de transformer le modele d'openstreet map en model graph. *
 * 
 * @author rgayeelhadji
 *
 */
public class GraphSaxXMLHandler extends DefaultHandler {
	private String tempVal;
	private GraphItineraire graph;
	private boolean startPoint;
	private boolean startWay;
	private boolean startRoutePosition;
	private PointGeographique pointGeographique;
	private RoutePosition routePosition;
	private WayWolma wayWolma;

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// reset
		tempVal = "";
		if (qName.equalsIgnoreCase("graph")) {
			// create a new instance of osm
			graph = new GraphItineraire();

		} else {
			if (qName.equalsIgnoreCase("pt")) {
				// create a new instance of Pointvoisin
				pointGeographique = new PointGeographique();
				startPoint = true;
				startWay = false;
			} else {
				if (qName.equalsIgnoreCase("w")) {
					// create a new instance of Pointvoisin
					wayWolma = new WayWolma();
					startPoint = false;
					startWay = true;

				} else {
					if (qName.equalsIgnoreCase("r")) {
						routePosition = new RoutePosition();
						startRoutePosition = true;

					}
				}
			}
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("graph")) {
		} else {
			if (startPoint) {
				if (qName.equalsIgnoreCase("pt")) {
					graph.putPoint(pointGeographique);
					if ("o".equals(pointGeographique.getIn())) {
						graph.putPointGraph(pointGeographique);
						graph.putPointSecteur(pointGeographique);
					}
					startPoint = false;
				}
				if (qName.equalsIgnoreCase("id")) {
					if (startRoutePosition) {
						routePosition.setRoute(tempVal);
					} else {
						pointGeographique.setId(tempVal);
					}
				}
				if (qName.equalsIgnoreCase("i")) {
					if (startRoutePosition) {
						routePosition.setPosition(Integer.parseInt(tempVal));
					} else {
						pointGeographique.setIn(tempVal);
					}
				}
				if (qName.equalsIgnoreCase("a")) {
					pointGeographique.setLat(Double.parseDouble(tempVal));
				}
				if (qName.equalsIgnoreCase("o")) {
					pointGeographique.setLon(Double.parseDouble(tempVal));
				}
				if (qName.equalsIgnoreCase("p")) {
					pointGeographique.setPoids(Integer.parseInt(tempVal));
				}
				if (qName.equalsIgnoreCase("pi")) {
					pointGeographique.setPoidsIntersection(Integer.parseInt(tempVal));
				}
				if (qName.equalsIgnoreCase("s")) {
					pointGeographique.setSecteur(tempVal);
				}
				if (qName.equalsIgnoreCase("v")) {
					pointGeographique.getVoisins().add(tempVal);
				}
				if (qName.equalsIgnoreCase("r")) {
					pointGeographique.getRoutes().add(routePosition);
					startRoutePosition = false;
				}
			} else {
				if (startWay) {
					if (qName.equalsIgnoreCase("w")) {
						graph.putWay(wayWolma);
						startWay = false;
					}
					if (qName.equalsIgnoreCase("id")) {
						wayWolma.setId(tempVal);
					}
					if (qName.equalsIgnoreCase("h")) {
						wayWolma.setHighway(tempVal);
					}
					if (qName.equalsIgnoreCase("n")) {
						wayWolma.setNom(tempVal);
					}
					if (qName.equalsIgnoreCase("s")) {
						wayWolma.setSensUnique(tempVal);
					}
					if (qName.equalsIgnoreCase("v")) {
						wayWolma.setVitesse(Integer.parseInt(tempVal));
					}
				}
			}

		}

	}

	public String getTempVal() {
		return tempVal;
	}

	public void setTempVal(String tempVal) {
		this.tempVal = tempVal;
	}

	public GraphItineraire getGraph() {
		return graph;
	}

	public void setGraph(GraphItineraire graph) {
		this.graph = graph;
	}

	public <T> void majRouteGraph() {
		for (String key : graph.getMapPointsGraph().keySet()) {
			PointGeographique pointGeographique = graph.getMapPointsGraph().get(key);
			for (RoutePosition route : pointGeographique.getRoutes()) {
				WayWolma wayWolma = graph.getWay(route.getRoute());
				if (wayWolma != null) {
					if (wayWolma.getPoints().size() >= route.getPosition()) {
						wayWolma.getPoints().add(route.getPosition(), pointGeographique);
					} else {
						int sizeWay = wayWolma.getPoints().size();
						for (int i = 0; i < route.getPosition() - sizeWay; i++) {
							wayWolma.getPoints().add(null);
						}
						wayWolma.getPoints().add(route.getPosition(), pointGeographique);
					}
				}
			}

		}
		for (String key : graph.getMapWay().keySet()) {
			Predicate<? super PointGeographique> filter = new Predicate<PointGeographique>() {
				public boolean test(PointGeographique point) {
					return point == null;
				}
			};
			graph.getWay(key).getPoints().removeIf(filter);

		}
	}

	public static void main(String[] args) {
		try {
			System.out.println("--------------------------------------");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			GraphSaxXMLHandler graphSaxXMLHandler = new GraphSaxXMLHandler();
			saxParser.parse("C:\\perso\\devs\\geolocalisation\\Mbour_graph.xml", graphSaxXMLHandler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
