package se.davor.bussar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
	private FragmentActivity fa;
	
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    
    public void setFragmentActivity(FragmentActivity fa) {
    	this.fa = fa;
    }

    @Override
    public Fragment getItem(int position) {
    	Fragment fragment = new TimetableFragment();    	
    	Bundle args = new Bundle();
        args.putInt(TimetableFragment.TRIP_ID, position + 1);
        args.putString(TimetableFragment.TRIP_NAME, TripsManager.getTripName(position));
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
    	return TripsManager.getTripName(position);
    }
}