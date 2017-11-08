package com.xoolibeut.wolma.itineraire;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainTest {

	public static void main(String[] args) {
		try {
			System.out.println("calcule  itineraire  ");
			GraphService graphService = new AbstractGraphService() {
				public List<String> determineSecteurVoisins(Double lat, Double lon) {
					// TODO Auto-generated method stub
					return null;
				}

				public String determineSecteur(Double lat, Double lon) {
					// TODO Auto-generated method stub
					return "MDB";
				}
			};
			GraphSaxXMLHandler handler = new GraphSaxXMLHandler();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse("C:\\perso\\devs\\geolocalisation\\Mbour_graph.xml", handler);
			GraphItineraire graph = handler.getGraph();
			graph.setDistanceLocale(800D);
			graph.setDistanceMedium(1600D);
			graph.setDistanceLointain(4000D);
			graph.setDistanceLointain(7000D);
			ItineraireWolma itineraireWolma = new ItineraireWolma(graphService, graph);
			itineraireWolma.calculeItineraire(14.41666226, -16.9546365, 14.4132955,
					-16.9616317);
			System.out.println("Itineraire size   " + itineraireWolma.getItineraires().size());
			System.out.println("Itineraire  " + itineraireWolma.getItineraires().get(0).getChemins().size());
			System.out.println("Fin ----------------------");
			Map<String, String> maps = new HashMap<String, String>();
			/*for (PointItineraire pointGeographique : itineraireWolma.getItineraires().get(0).getChemins()) {
				maps.put(pointGeographique.getId(), pointGeographique.getId());
			}*/
			System.out.println("size maps "+maps.size());
			System.out.println("size point ignorée "+maps.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
