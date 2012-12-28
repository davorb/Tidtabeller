package se.davor.bussar;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchSelectActivity extends Activity {
	private ArrayAdapter<Station> aa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_select);
		Intent intent = getIntent();
		String searchString = intent.getStringExtra(SearchActivity.SEARCH_TEXT);

		ArrayList<Station> arrayList = 
				StationManager.getInstance().searchStations(this, searchString);
				
		final ListView stationsListView = 
				(ListView) findViewById(R.id.stationsListView);
		aa = new ArrayAdapter<Station>(this, 
						android.R.layout.simple_expandable_list_item_1, 
						arrayList);
		stationsListView.setAdapter(aa);
		final Activity a = this;
		stationsListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
				Station selectedFromList = (Station)(stationsListView.getItemAtPosition(myItemInt));
				StationManager.getInstance().add(selectedFromList);
				Intent mainIntent = new Intent(a, MainActivity.class);
				startActivity(mainIntent);
		    }    
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_search_select, menu);
		return true;
	}
	
	public void add(Station s) {
		aa.add(s);
	}

}
