package com.xoolibeut.wolma.model.handler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.xoolibeut.wolma.graph.origine.Node;
import com.xoolibeut.wolma.graph.origine.Osm;
import com.xoolibeut.wolma.graph.origine.Tag;
import com.xoolibeut.wolma.graph.origine.Way;

/**
 * Classe permettant de transformer le modele 
 * d'openstreet map en model graph. * 
 * @author rgayeelhadji
 *
 */
public class OsmSaxXMLHandler extends DefaultHandler {
	private String tempVal;
	private Osm osm;
	private boolean startNode;
	private boolean startWay;
	private Node node;
	private Way way;
	private Tag tag;

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// reset
		tempVal = "";
		if (qName.equalsIgnoreCase("osm")) {
			// create a new instance of osm
			osm = new Osm();

		} else {
			if (qName.equalsIgnoreCase("node")) {
				// create a new instance of Pointvoisin
				node = new Node();
				node.setId(attributes.getValue("id"));
				node.setLat(Double.parseDouble(attributes.getValue("lat")));
				node.setLon(Double.parseDouble(attributes.getValue("lon")));
				osm.getNodes().add(node);
				startNode = true;
				startWay = false;
			} else {
				if (qName.equalsIgnoreCase("way")) {
					// create a new instance of Pointvoisin
					way = new Way();
					way.setId(attributes.getValue("id"));
					startNode = false;
					startWay = true;
					osm.getWays().add(way);
				} else {
					if (qName.equalsIgnoreCase("tag")) {
						// create a new instance of Pointvoisin
						tag = new Tag();
						tag.setK(attributes.getValue("k"));
						tag.setV(attributes.getValue("v"));
					} else {
						if (qName.equalsIgnoreCase("nd")) {
							// create a new instance of Pointvoisin
							Node nodeWay = new Node();
							nodeWay.setId(attributes.getValue("ref"));
							way.getNodes().add(nodeWay);
						} else {
							// startNode = false;
							// startWay = false;
						}
					}
				}
			}
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("osm")) {
		} else {
			if (startNode) {
				if (qName.equalsIgnoreCase("node")) {
					startNode = false;
				}
			} else {
				if (startWay) {
					if (qName.equalsIgnoreCase("way")) {
						startWay = false;
					} else if (qName.equalsIgnoreCase("tag")) {
						way.getTags().add(tag);
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

	public static void main(String[] args) {
		try {
			System.out.println("--------------------------------------");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			OsmSaxXMLHandler osmSaxXMLHandler = new OsmSaxXMLHandler();
			saxParser.parse("C:\\perso\\devs\\geolocalisation\\mbour.xml", osmSaxXMLHandler);
			System.out.println(osmSaxXMLHandler.osm.getNodes().size());
			System.out.println(osmSaxXMLHandler.osm.getWays().size());
			for (Node node : osmSaxXMLHandler.osm.getNodes()) {
				System.out.println("Node Identifiant " + node.getId() + "     " + node.getLat());
			}
			for (Way way : osmSaxXMLHandler.osm.getWays()) {
				System.out.println("Identifiant " + way.getId() + " " + way.getNodes().size());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Osm getOsm() {
		return osm;
	}

	public void setOsm(Osm osm) {
		this.osm = osm;
	}
}
