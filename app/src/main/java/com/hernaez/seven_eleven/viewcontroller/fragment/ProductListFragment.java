package com.hernaez.seven_eleven.viewcontroller.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.businesslayer.ProductsRetrotfitManager;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;
import com.hernaez.seven_eleven.viewcontroller.activity.MainActivity;
import com.hernaez.seven_eleven.viewcontroller.adapter.ProductListAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by TAS on 7/28/2015.
 */
public class ProductListFragment extends BaseFragment {
    @Inject
    ProductsRetrotfitManager productsRetrotfitManager;
    @Inject
    AndroidUtils utils;
    @Inject
    Bus bus;

    @InjectView(R.id.listView_productList)
    ListView lv;

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {
        bus.register(this);
        populate();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                utils.alert("Long click to update product details.");
                //utils.loadFragment((ActionBarActivity)getActivity(), R.id.container, new AddNewProductFragment());
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.prdouct_list, container, false);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState2(Bundle outState) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void populate() {

        try {

            ProductListAdapter myadapter = null;

            myadapter = new ProductListAdapter(getActivity(),
                    R.layout.list_item, productsRetrotfitManager.getAllProducts());
            lv.setAdapter(myadapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void refresh(Product product) {
        populate();
    }

    public static ProductListFragment newInstance() {
        return newInstance(R.layout.prdouct_list);
    }

    int currentItem = 0;

    public static ProductListFragment newInstance(int layoutId) {
        ProductListFragment productListFragment = new ProductListFragment();
        productListFragment.currentItem = layoutId;
        productListFragment.setRetainInstance(true);
        return productListFragment;
    }
}
