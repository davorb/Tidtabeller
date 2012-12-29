package se.davor.bussar;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.util.Log;


public class XmlParser {
	public ArrayList<TimeEntry> parseDepartureArrival(String tag,
												InputStream stream) {
		ArrayList<TimeEntry> times = new ArrayList<TimeEntry>();
		InputSource is = new InputSource();
        is.setCharacterStream(new InputStreamReader(stream));
		
		Document doc = getDoc(is);
		
		if (doc == null)
			Log.e("XmlParser", "Doc is null");
			
		NodeList nl = null;
		NodeList children = null;
		Node node = null;
		String journeyDateTimes, name;
		TimeEntry timeEntry;

		try {
			nl = doc.getElementsByTagName("Line");
			
			for (int i=0; i < nl.getLength(); i++) {
				node = nl.item(i);
				children = node.getChildNodes();
				
				journeyDateTimes = 
						children.item(2).getFirstChild().getNodeValue();
				name = children.item(0).getFirstChild().getNodeValue();

				timeEntry = new TimeEntry(name, journeyDateTimes);
				
				if (name != null) {
					times.add(timeEntry);
				} else {
					Log.e("XmlParser", "Null node value");
				}
			}
		} catch (Exception e) {
			Log.e("Xml", "Failed to find xml tag.");
			e.printStackTrace();
		}
		return times;
	}
	
	private Document getDoc(InputSource is) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(is);
		} catch (Exception e) {
			e.printStackTrace(); // TODO: fix
		}
		return doc;
	}

	public ArrayList<Station> parseSearchStationArrival(InputStream stream) {
		ArrayList<Station> stations = new ArrayList<Station>();
		InputSource is = new InputSource();
        is.setCharacterStream(new InputStreamReader(stream));
		
		Document doc = getDoc(is);
		
		if (doc == null)
			Log.e("XmlParser", "Doc is null");
			
		NodeList nl = null;
		NodeList children = null;
		Node node = null;
		String name, id;
		Station station = null;

		try {
			nl = doc.getElementsByTagName("Point");
			
			for (int i=0; i < nl.getLength(); i++) {
				node = nl.item(i);
				children = node.getChildNodes();
				
				id   = children.item(0).getFirstChild().getNodeValue();
				name = children.item(1).getFirstChild().getNodeValue();

				station = new Station(name, id);
				
				if (name != null) {
					stations.add(station);
				} else {
					Log.e("XmlParser", "Null node value");
				}
			}
		} catch (Exception e) {
			Log.e("Xml", "Failed to find xml tag.");
			e.printStackTrace();
		}
		return stations;
	}
}
