package se.davor.bussar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TimetableSectionsPagerAdapter extends FragmentPagerAdapter {
	private FragmentActivity fa;
	private StationManager stationManager;
	
    public TimetableSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        stationManager = new StationManager(); // TODO: Fix!
    }
    
    public void setFragmentActivity(FragmentActivity fa) {
    	this.fa = fa;
    }

    @Override
    public Fragment getItem(int id) {
        return new TimetableFragment(stationManager.get(id));    	
    }


    @Override
    public CharSequence getPageTitle(int position) {
    	return stationManager.get(position).getStationName();
    }

	@Override
	public int getCount() {
		return stationManager.length();
	}
}