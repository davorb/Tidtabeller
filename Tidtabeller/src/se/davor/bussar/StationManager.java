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
	private static final String SAMPLE_XML = "http://www.w3schools.com/xml/note.xml";
	private static final String STATION_SEARCH_REQUEST = 
			"http://www.labs.skanetrafiken.se/v2.2/querystation.asp?inpPointfr=";
	
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
	
	public ArrayList<Station> getStations() {
		return stations;
	}
	
	public ArrayList<Station> searchStations(SearchSelectActivity ssa, String searchString) {
		ArrayList<Station> results = new ArrayList<Station>();
		new Downloader(ssa).execute(searchString);
		return results;
	}

	public void remove(Station station) {
		stations.remove(station);
	}
	
	//////////
	private class Downloader extends AsyncTask<String, Void, ArrayList<Station>> {
		private SearchSelectActivity ssa;
		
		private Downloader(SearchSelectActivity ssa) {
			this.ssa = ssa;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Station> result) {
			for (Station s : result) {
				ssa.add(s);
			}
		}
		
		@Override
		protected ArrayList<Station> doInBackground(String... arg) {
			String searchString = arg[0];
			InputStream data = null;
			ArrayList<Station> result = null;
			try {
				data = fetchStationData(searchString);
				result = 
						new XmlParser().parseSearchStationArrival(data);
			} catch (IOException e) { 
				Log.e("downloadersearch", "Failed to get data.");
				e.printStackTrace();				
			} finally {
				if (data != null)
					try {
						data.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			if (result == null)
				Log.e("downloadersearch", "Result is null");
			return result;
		}
		
		private InputStream fetchStationData(String searchString) throws IOException {
			Log.d("SearchConnection", "Fetching data for stations: "+searchString);
			InputStream is = null;
			URL url = new URL(STATION_SEARCH_REQUEST+searchString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect(); 
			int response = conn.getResponseCode();
			Log.d("Connection", "The response is: "+response);			
			is = conn.getInputStream(); 
			return is;
		}
		
		public String readIt(InputStream stream, int len) throws 
									IOException, UnsupportedEncodingException {
			BufferedReader r = new BufferedReader(new InputStreamReader(stream));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
			if (line == null)
				Log.e("downloadersearch", "Read null from network");
			return line;
		}

	}



}
