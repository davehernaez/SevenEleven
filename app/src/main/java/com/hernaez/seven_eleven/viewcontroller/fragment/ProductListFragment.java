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
import com.hernaez.seven_eleven.model.businesslayer.ProductManager;
import com.hernaez.seven_eleven.viewcontroller.adapter.CustomViewAdapter;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by TAS on 7/28/2015.
 */
public class ProductListFragment extends BaseFragment {
    @Inject
    ProductManager productManager;

    @InjectView(R.id.listView_productList)
    ListView lv;

    Thread thread;

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

    public static ProductListFragment newInstance() {
        return new ProductListFragment();
    }

    public void populate() {

        try {

            CustomViewAdapter myadapter = null;

            myadapter = new CustomViewAdapter(getActivity(),
                    R.layout.list_item, productManager.getAllProducts());
            lv.setAdapter(myadapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
