package se.davor.bussar;

public class Trip {
	private static String stationSrcName, stationDestName;
	private String stationId;
	
	public Trip(String stationSrcName, String stationDestName) {
		this.stationSrcName = stationSrcName;
		this.stationDestName = stationDestName;
	}

	public static String getStationSrcName() {
		return stationSrcName;
	}

	public static String getStationDestName() {
		return stationDestName;
	}

	public String getStationId() {
		if (stationId == null) // TODO: Check from disk
			stationId = findStationId();
		return stationId;
	}

	private String findStationId() {
		return "81811"; // TODO: fix
	}
	
	public String getTripName() {
		return getStationSrcName();
	}
}
