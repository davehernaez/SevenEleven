package com.hernaez.seven_eleven.viewcontroller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.model.businesslayer.ProductsRetrotfitManager;
import com.hernaez.seven_eleven.viewcontroller.adapter.ProductListAdapter;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by TAS on 7/28/2015.
 */
public class ProductListFragment extends BaseFragment {
    @Inject
    ProductsRetrotfitManager productsRetrotfitManager;

    @InjectView(R.id.listView_productList)
    ListView lv;

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {

        populate();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView name = (TextView) view
                        .findViewById(R.id.textViewProduct_name);
                TextView price = (TextView) view
                        .findViewById(R.id.textViewProduct_price);

                Toast.makeText(getActivity(),
                        "You clicked: " + name.getText().toString()+" p" +price.getText().toString(),
                        Toast.LENGTH_SHORT).show();
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
    /*public static ProductListFragment newInstance() {
        return new ProductListFragment();
    }*/

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
}
