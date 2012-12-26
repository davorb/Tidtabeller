package se.davor.bussar;

public class TimeEntry {
	private String time, name;
	
	public TimeEntry(String name, String unformatedTime) {
		setTime(unformatedTime);
		setName(name);
	}
	
	public String toString() {
		return getTime()+" ("+getName()+")";
	}

	public String getTime() {
		return time;
	}

	public void setTime(String unformatedTime) {
		this.time = parseTime(unformatedTime);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private String parseTime(String unformatedTime) {
		int len = unformatedTime.length();
		return unformatedTime.substring(len-8, len-3);
	}
}
