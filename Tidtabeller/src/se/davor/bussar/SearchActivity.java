package se.davor.bussar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends Activity {
	public static final String SEARCH_TEXT = "se.davor.bussar.search_text";
	
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
		Intent intent = new Intent(this, SearchSelectActivity.class);
		String stationName = mStationNameView.getText().toString();
		intent.putExtra(SEARCH_TEXT, stationName);
		startActivity(intent);
	}
}
