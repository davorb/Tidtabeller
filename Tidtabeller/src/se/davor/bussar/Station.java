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

public class Station {
	private String stationName;
	private String stationId;
	
	public Station(String stationName, String stationId) {
		this.stationName = stationName;
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public String getStationId() {
		return stationId;
	}
	
	public String toString() {
		return stationName;
	}

	public ArrayList<String> getTimes(TimetableFragment tf) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		new Downloader(tf, getStationId()).execute();
    	list.add("lol");
    	return list;
	}
	
	private class Downloader extends AsyncTask<Void, Void, ArrayList<TimeEntry>> {
		
		private TimetableFragment o;
		private String tripId;
		
		public Downloader(TimetableFragment o, String tripId) {
			this.o = o;
			this.tripId = tripId;
		}

		@Override
		protected ArrayList<TimeEntry> doInBackground(Void... arg0) {
			InputStream data = null;
			ArrayList<TimeEntry> result = null;
			try {
				data = fetchTripData(tripId); // TODO: Trip!

				result = 
						new XmlParser().parseDepartureArrival("random", data);
			} catch (IOException e) { 
				Log.e("Connection", "Failed to get data.");
				e.printStackTrace();				
			} finally {
				if (data != null)
					try {
						data.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			return result;
		}
		
		@Override
        protected void onPostExecute(ArrayList<TimeEntry> result) {
			if (result == null) {
				Log.e("Connection", "Result is null");
				return;
			}
			
			ArrayList<TimeEntry> parsedData = result;
			
        	for (TimeEntry item : parsedData)
        		o.add_time(item);	
		}
		
		private InputStream fetchTripData(String tripId) throws IOException {
			Log.d("Connection", "Fetching data for trip "+tripId);
			InputStream is = null;
			URL url = new URL(DEPARTURE_ARRIVAL_REQUEST+tripId);
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
				Log.e("Connection", "Read null from network");
			return line;
		}

	}

	public void setStationId(String id) {
		this.stationId = id;
	}
	
	private static final String SAMPLE_XML = "http://www.w3schools.com/xml/note.xml";
	private static final String DEPARTURE_ARRIVAL_REQUEST = 
			"http://www.labs.skanetrafiken.se/v2.2/stationresults.asp?selPointFrKey=";

}
