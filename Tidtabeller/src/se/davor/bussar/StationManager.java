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
	ArrayList<Station> stations;
	
	public StationManager() {
		this.stations = new ArrayList<Station>();
		
		
		// TODO: fix
		stations.add(new Station("Testarv�gen"));
	}
	
	public static int getCount() {
		return 3;
	}

	public int getLength() {
		return 1;
	}

	public Station get(int i) {
		return stations.get(i);
	}
	
}