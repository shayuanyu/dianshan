package com.bawei.dianshang.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class MFAdapter extends FragmentPagerAdapter {

     private List<Fragment> fgment;

    public MFAdapter(FragmentManager fm, List<Fragment> fgment) {
        super(fm);
        this.fgment = fgment;
    }

    @Override
    public Fragment getItem(int position) {
        return fgment.get(position);
    }

    @Override
    public int getCount() {
        return fgment.size();
    }
}
