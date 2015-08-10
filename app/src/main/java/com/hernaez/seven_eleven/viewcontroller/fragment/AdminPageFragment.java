package com.hernaez.seven_eleven.viewcontroller.fragment;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by TAS on 7/28/2015.
 */
public class AdminPageFragment extends BaseFragment implements View.OnClickListener {


    @InjectView(R.id.button_for_reorder)
    protected Button btn_for_reordering;

    @InjectView(R.id.button_productList)
    protected Button btn_all_products;

    @Inject
    AndroidUtils androidUtils;

    @Override
    public View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.admin_page, container, false);
    }

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {

        btn_all_products.setOnClickListener(this);
        btn_for_reordering.setOnClickListener(this);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState2(Bundle outState) {
    }

    public static AdminPageFragment newInstance() {
        //return new AdminPageFragment();
        return newInstance(R.layout.admin_page);
    }

    int currentItem = 0;

    public static AdminPageFragment newInstance(int layoutId) {
        AdminPageFragment adminPageFragment = new AdminPageFragment();
        adminPageFragment.currentItem = layoutId;
        adminPageFragment.setRetainInstance(true);
        return adminPageFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_productList:
                YoYo.with(Techniques.Pulse).duration(300).playOn(btn_all_products);
                //androidUtils.loadFragment((ActionBarActivity) getActivity(), getParentFragment().getId(), ProductListFragment.newInstance());
                break;

            case R.id.button_for_reorder:
                YoYo.with(Techniques.Pulse).duration(300).playOn(btn_for_reordering);
                //androidUtils.loadFragment((ActionBarActivity) getActivity(), getParentFragment().getId(), ReOrderFragment.newInstance());
                break;
        }
    }
}
