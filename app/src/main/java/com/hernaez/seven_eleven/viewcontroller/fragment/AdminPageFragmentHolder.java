package com.hernaez.seven_eleven.viewcontroller.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.viewcontroller.adapter.AdminPagerAdapter;

import butterknife.InjectView;

/**
 * Created by TAS on 7/28/2015.
 */
public class AdminPageFragmentHolder extends BaseFragment {

    @InjectView(R.id.tpi_header)
    protected com.rey.material.widget.TabPageIndicator indicator;

    @InjectView(R.id.vp_pages)
    protected ViewPager pager;

    @Override
    public View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_fragment_activity_carousel, container, false);

    }

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {

        pager.setAdapter(new AdminPagerAdapter(getResources(), getChildFragmentManager()));
        indicator.setViewPager(pager);
        pager.setCurrentItem(currentItem);

    }

    int currentItem = 0;

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        currentItem = savedInstanceState.getInt("currentItem");
    }

    @Override
    public void onSaveInstanceState2(Bundle outState) {
        currentItem = pager.getCurrentItem();
        outState.putInt("currentItem", currentItem);
    }

    public static AdminPageFragmentHolder newInstance() {
        return new AdminPageFragmentHolder();
    }
}
