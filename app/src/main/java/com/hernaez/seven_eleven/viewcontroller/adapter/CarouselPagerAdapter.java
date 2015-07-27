

package com.hernaez.seven_eleven.viewcontroller.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hernaez.seven_eleven.viewcontroller.fragment.CustomerOrderFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.OrderSummaryFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Pager adapter
 */
public class CarouselPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    List<FragmentGenerate> list = new ArrayList<FragmentGenerate>();

    public CarouselPagerAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
        list.add(new FragmentGenerate() {
            @Override
            public Fragment newInstance() {
                return CustomerOrderFragment.newInstance();
            }

            @Override
            public String instanceName() {
                return "Customer Order";
            }
        });
        list.add(new FragmentGenerate() {
            @Override
            public Fragment newInstance() {
                return OrderSummaryFragment.newInstance();
            }

            @Override
            public String instanceName() {
                return "Order Summary";
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
