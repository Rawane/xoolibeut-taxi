package com.xoolibeut.wolma.itineraire;

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
	private PointGeographique pointGeographique;
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
				graph.putPoint(pointGeographique);
				startPoint = true;
				startWay = false;
			} else {
				if (qName.equalsIgnoreCase("w")) {
					// create a new instance of Pointvoisin
					wayWolma = new WayWolma();
					graph.putWay(wayWolma);
					startPoint = false;
					startWay = true;

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
					if ("o".equals(pointGeographique.getIn())) {
						graph.putPointGraph(pointGeographique);
						graph.putPointSecteur(pointGeographique);
					}
					startPoint = false;
				}
				if (qName.equalsIgnoreCase("id")) {
					pointGeographique.setId(tempVal);
				}
				if (qName.equalsIgnoreCase("i")) {
					pointGeographique.setIn(tempVal);
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
			} else {
				if (startWay) {
					if (qName.equalsIgnoreCase("w")) {
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
	public static void main(String[] args) {
		try {
			System.out.println("--------------------------------------");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			GraphSaxXMLHandler osmSaxXMLHandler = new GraphSaxXMLHandler();
			saxParser.parse("C:\\perso\\devs\\geolocalisation\\mbour.xml", osmSaxXMLHandler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
