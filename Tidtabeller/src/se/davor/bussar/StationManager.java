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
	
	public ArrayList<Station> searchStations(String searchString) {
		ArrayList<Station> results = new ArrayList<Station>();
		
		results.add(new Station("result 1")); // TODO
		results.add(new Station(searchString));
		
		return results;
	}
	
}
