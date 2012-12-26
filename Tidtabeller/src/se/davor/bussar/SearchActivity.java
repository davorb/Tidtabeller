package se.davor.bussar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends Activity {
	
//	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mStartLocationView;
	private EditText mStopLocationView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);

		// Set up the login form.
		mStartLocationView = (EditText) findViewById(R.id.start_location);
		mStartLocationView.setText(mEmail);

		mStopLocationView = (EditText) findViewById(R.id.stop_location);

		mLoginFormView = findViewById(R.id.search_form);

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
		// TODO
	}
}
