package se.davor.bussar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

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
	
}
