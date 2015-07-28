package com.hernaez.seven_eleven.viewcontroller.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hernaez.seven_eleven.viewcontroller.fragment.AdminPageFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.ProductListFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.ReOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAS on 7/28/2015.
 */
public class AdminPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    List<FragmentGenerate> list = new ArrayList<FragmentGenerate>();

    public AdminPagerAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;

        list.add(new FragmentGenerate() {
            @Override
            public AdminPageFragment newInstance() {
                return AdminPageFragment.newInstance();
            }

            @Override
            public String instanceName() {
                return "Admin Page";
            }
        });

        list.add(new FragmentGenerate() {
            @Override
            public ReOrderFragment newInstance() {
                return ReOrderFragment.newInstance();
            }

            @Override
            public String instanceName() {
                return "ReOrder";
            }
        });
        list.add(new FragmentGenerate() {
            @Override
            public ProductListFragment newInstance() {
                return ProductListFragment.newInstance();
            }

            @Override
            public String instanceName() {
                return "All Products";
            }
        });
    }


    private interface FragmentGenerate {
        abstract public Fragment newInstance();

        abstract public String instanceName();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(final int position) {
        return list.get(position).newInstance();
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return list.get(position).instanceName();
    }
}