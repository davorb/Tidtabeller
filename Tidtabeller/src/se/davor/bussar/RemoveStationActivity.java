package se.davor.bussar;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class RemoveStationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remove_station);
		
		ArrayList<Station> stations =
				StationManager.getInstance(this).getStations();
		final ListView removeListView = 
				(ListView) findViewById(R.id.removeListView);
		ArrayAdapter<Station> aa = 
				new ArrayAdapter<Station>(this,
						android.R.layout.simple_selectable_list_item,
						stations);
		removeListView.setAdapter(aa);
		
		final Activity a = this;
		removeListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
				Station selectedFromList = (Station)(removeListView.getItemAtPosition(myItemInt));
				StationManager.getInstance(a).remove(selectedFromList, a);
				Intent mainIntent = new Intent(a, MainActivity.class);
				startActivity(mainIntent);
		    }    
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_remove_station, menu);
		return true;
	}

}
