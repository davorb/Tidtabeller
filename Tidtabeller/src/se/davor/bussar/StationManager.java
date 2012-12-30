package se.davor.bussar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

public class StationManager {
	private static final String SAMPLE_XML = "http://www.w3schools.com/xml/note.xml";
	private static final String STATION_SEARCH_REQUEST = 
			"http://www.labs.skanetrafiken.se/v2.2/querystation.asp?inpPointfr=";
	private static final String SAVED_STATION_NAMES = "se.davor.bussar.saved_station_names";
	private static final String SAVED_STATION_IDS   = "se.davor.bussar.saved_station_ids";
	private static final String APP					= "se.davor.bussar";
	
	private ArrayList<Station> stations;
	private static StationManager instance;
	
	public StationManager(Activity a) {	
		loadValues(a);
		Log.d("StationManager", "Created new instance");
	}
	
	public static StationManager getInstance(Activity a) {
		if (instance == null)
			instance = new StationManager(a);
		
		return instance;
	}

	public int length() {
		return stations.size();
	}

	public Station get(int i) {
		return stations.get(i);
	}

	public void add(Station station, Activity a) {
		stations.add(station);
		saveValues(a);
	}
	
	public void remove(Station station, Activity a) {
		stations.remove(station);
		saveValues(a);
	}
	
	private void saveValues(Activity a) {
		SharedPreferences sharedPrefs = a.getSharedPreferences(APP, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		
		ArrayList<String> stationNames = new ArrayList<String>();
		ArrayList<String> stationIds   = new ArrayList<String>();
	
		for (Station s : stations) {
			Log.d("StationSaver", "Saving station: "+s.toString());
			stationNames.add(s.getStationName());
			stationIds.add(s.getStationId());
		}
		Set<String> nameSet = new HashSet<String>(stationNames);
		Set<String> idSet   = new HashSet<String>(stationIds);
		
		editor.putStringSet(SAVED_STATION_NAMES, nameSet);
		editor.putStringSet(SAVED_STATION_IDS, idSet);
		editor.commit();
		Log.d("StationSaver", "Saving "+nameSet.size()+" stations");
	}
	
	private void loadValues(Activity a) {
		SharedPreferences sharedPrefs = a.getSharedPreferences(APP, Context.MODE_PRIVATE);
		
		Set<String> stationNamesSet = new HashSet<String>();
		Set<String> stationIdsSet = new HashSet<String>();
		stationNamesSet = sharedPrefs.getStringSet(SAVED_STATION_NAMES, stationNamesSet);
		stationIdsSet = sharedPrefs.getStringSet(SAVED_STATION_IDS, stationIdsSet);
		
		String[] names = new String[stationNamesSet.size()];
		String[] ids = new String[stationIdsSet.size()];
		names = stationNamesSet.toArray(names);
		ids   = stationIdsSet.toArray(ids);
		
		int length = stationIdsSet.size() - stationNamesSet.size();
		if (length != 0) {
			Log.e("StationLoader", "Id and name sets are not the same size. Id: "+
					stationIdsSet.size()+", name: "+stationNamesSet.size());
		} else if (stationIdsSet.size() == 0) {
			Log.w("StationLoader", "Id and name sets are size zero. Id: "+
					stationIdsSet.size()+", name: "+stationNamesSet.size());
		}
		
		Log.d("StationLoader", "Loading "+stationIdsSet.size()+" stations.");
		Log.d("StationLoaderIds", stationIdsSet.toString());
		Log.d("StationLoaderNames", stationNamesSet.toString());
		
		ArrayList<Station> stationList = new ArrayList<Station>();
		for (int i=0; i < stationNamesSet.size(); i++) {
			Station s = new Station(names[i], ids[i]);
			stationList.add(s);
		}
		this.stations = stationList;
	}
	
	public ArrayList<Station> getStations() {
		return stations;
	}
	
	public ArrayList<Station> searchStations(SearchSelectActivity ssa, String searchString) {
		String encodedString = searchString;
		try {
			encodedString = URLEncoder.encode(searchString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e("SearchStations", "Failed to encode the search string: "+searchString);
			e.printStackTrace();
		}
		
		ArrayList<Station> results = new ArrayList<Station>();
		new Downloader(ssa).execute(encodedString);
		return results;
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
