package se.davor.bussar;

import java.util.ArrayList;

public class StationManager {
	private ArrayList<Station> stations;
	private static StationManager instance;
	
	public StationManager() {
		this.stations = new ArrayList<Station>();
		
		// TODO: fix
		add(new Station("Tre Hšgars Park"));
	}
	
	public static StationManager getInstance() {
		if (instance == null)
			instance = new StationManager();
		
		return instance;
	}

	public int length() {
		return stations.size();
	}

	public Station get(int i) {
		return stations.get(i);
	}

	public void add(Station station) {
		stations.add(station);
	}
	
	public ArrayList<String> searchStations(String searchString) {
		ArrayList<String> results = new ArrayList<String>();
		results.add("result 1");
		results.add(searchString);
		
		return results;
	}
	
}
