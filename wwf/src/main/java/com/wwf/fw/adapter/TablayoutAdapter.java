package com.wwf.fw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wwf.fw.bean.FragmentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: wwf
 * 描述:
 *
 * adapter一定是与list<bean>进行组合
 * **/

public class TablayoutAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mShowItems = new ArrayList<>();

    public TablayoutAdapter(FragmentManager fm, List<FragmentInfo> showItems) {
        super(fm);
        mShowItems = showItems;
    }


    @Override
    public Fragment getItem(int position) {
        return mShowItems.get(position).fragment;
    }

    @Override
    public int getCount() {
        return mShowItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mShowItems.get(position).title;
    }
}
