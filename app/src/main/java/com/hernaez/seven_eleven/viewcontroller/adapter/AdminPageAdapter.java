package com.hernaez.seven_eleven.viewcontroller.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hernaez.seven_eleven.viewcontroller.fragment.AddNewProductFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.ProductListFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.ReOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAS on 7/28/2015.
 */
public class AdminPageAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    List<FragmentGenerate> list = new ArrayList<FragmentGenerate>();

    public AdminPageAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
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
        list.add(new FragmentGenerate() {
            @Override
            public ReOrderFragment newInstance() {
                return ReOrderFragment.newInstance();
            }

            @Override
            public String instanceName() {
                return "Products for Re-Ordering";
            }
        });
        list.add(new FragmentGenerate() {
            @Override
            public AddNewProductFragment newInstance() {
                return AddNewProductFragment.newInstance();
            }

            @Override
            public String instanceName() {
                return "Add New Product";
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