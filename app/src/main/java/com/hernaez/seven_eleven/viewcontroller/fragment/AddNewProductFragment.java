package com.hernaez.seven_eleven.viewcontroller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hernaez.seven_eleven.R;

/**
 * Created by TAS on 8/11/2015.
 */
public class AddNewProductFragment extends BaseFragment {
    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_product, container, false);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState2(Bundle outState) {

    }

    public static AddNewProductFragment newInstance() {
        return new AddNewProductFragment();
    }
}
