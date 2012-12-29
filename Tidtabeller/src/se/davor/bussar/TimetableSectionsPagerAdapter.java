package se.davor.bussar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TimetableSectionsPagerAdapter extends FragmentPagerAdapter {
	private FragmentActivity fa;
	
    public TimetableSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    
    public void setFragmentActivity(FragmentActivity fa) {
    	this.fa = fa;
    }

    @Override
    public Fragment getItem(int id) {
        return new TimetableFragment(StationManager.getInstance(fa).get(id));    	
    }


    @Override
    public CharSequence getPageTitle(int position) {
    	return StationManager.getInstance(fa).get(position).getStationName();
    }

	@Override
	public int getCount() {
		return StationManager.getInstance(fa).length();
	}
}