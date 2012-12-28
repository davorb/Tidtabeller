package se.davor.bussar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends Activity {

	// UI references.
	private EditText mStationNameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);

		// Set up the login form.
		mStationNameView = (EditText) findViewById(R.id.station);

		findViewById(R.id.add_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						addStation();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}

	public void addStation() {
		String stationName = mStationNameView.getText().toString();
		StationManager.getInstance().add(new Station(stationName));
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
