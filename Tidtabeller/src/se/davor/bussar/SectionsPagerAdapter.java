package se.davor.bussar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
	private FragmentActivity fa;
	
	private Trip trip; // TODO: Move this out
	
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        
        trip = new Trip("Tre Hšgars Park", "Ole Ršmers VŠg"); // TODO: Add loader for this shit
    }
    
    public void setFragmentActivity(FragmentActivity fa) {
    	this.fa = fa;
    }

    @Override
    public Fragment getItem(int id) {
    	// TODO: Add class for loading of trips
    	
    	
    	
    	Fragment fragment = new TimetableFragment();    	
    	Bundle args = new Bundle();
        args.putString(TimetableFragment.TRIP_ID, trip.getStationId());
//        args.putString(TimetableFragment.TRIP_NAME, TripsManager.getTripName(stationId));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        // Number of pages to show
        return TripsManager.getCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
    	return trip.getTripName();
    }
}