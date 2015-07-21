package com.hernaez.seven_eleven.viewcontroller.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hernaez.seven_eleven.R;

import butterknife.InjectView;

/**
 * Created by TAS on 7/21/2015.
 */
public class MyFragmentActivity extends BaseFragment{
    /*@InjectView(R.id.tpi_header)
    protected com.rey.material.widget.TabPageIndicator indicator;

    @InjectView(R.id.vp_pages)
    protected ViewPager pager;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.customer_order, container, false);
    }

    @Override
    public View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {

    }

    int currentItem=0;
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        currentItem=savedInstanceState.getInt("currentItem");
    }
    @Override
    public void onSaveInstanceState2(Bundle outState) {
        /*currentItem=pager.getCurrentItem();
        outState.putInt("currentItem", pager.getCurrentItem());*/
    }

    public static MyFragmentActivity newInstance(){
        return new MyFragmentActivity();
    }
}
