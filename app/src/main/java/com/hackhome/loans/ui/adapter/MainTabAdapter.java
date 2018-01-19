package com.hackhome.loans.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hackhome.loans.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * author: aragon
 * date: 2018/1/18 0018.
 */
public class MainTabAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mFragments = new ArrayList<BaseFragment>();

    public MainTabAdapter(List<BaseFragment> fragmentList,FragmentManager fm) {
        super(fm);
        if (fragmentList != null){
            mFragments = fragmentList;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
