package com.caucse.seatchecker.seatchecker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    TabPagerAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                return new FragmentHome();
            case 1:
                return new FragmentAlarm();
            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
