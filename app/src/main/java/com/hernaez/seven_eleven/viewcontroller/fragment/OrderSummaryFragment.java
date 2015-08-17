package com.hernaez.seven_eleven.viewcontroller.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.Order;
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.businesslayer.OrderDaoManager;
import com.hernaez.seven_eleven.model.businesslayer.ProductsRetrotfitManager;
import com.hernaez.seven_eleven.model.dataaccesslayer.greendao.OrderTable;
import com.hernaez.seven_eleven.viewcontroller.activity.MainActivity;
import com.hernaez.seven_eleven.viewcontroller.adapter.OrderSummaryDaoAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by TAS on 7/22/2015.
 */
public class OrderSummaryFragment extends BaseFragment implements AdapterView.OnItemLongClickListener,
        AdapterView.OnItemClickListener, View.OnClickListener {
    public int layoutId;

    Double total;
    Integer orderId, userid;
    @Inject
    Bus bus;
    @Inject
    Context context;
    @Inject
    OrderDaoManager orderDaoManager;
    @Inject
    ProductsRetrotfitManager productsRetrotfitManager;

    @InjectView(R.id.button_clear_summary)
    Button btn_confirm;
    @InjectView(R.id.textView_summary_grandtotal)
    TextView tv_grantotal;
    @InjectView(R.id.listView_order_summary)
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.order_summary, container, false);


    }

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {
        userid = MainActivity.userid;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                populate();
            }
        });

        bus.register(this);


        total = 0.0;

        btn_confirm.setOnClickListener(this);

    }

    @Override
    public View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState2(Bundle outState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_clear_summary:
                YoYo.with(Techniques.Pulse).duration(500).playOn(btn_confirm);
                try {
                    if (orderDaoManager.getAllOrders(context).toArray().length > 0) {
                        new AlertDialog.Builder(getActivity())
                                // Alert dialog for confirmation
                                .setIcon(android.R.drawable.ic_input_add)
                                .setTitle("Confirm")
                                .setMessage("You ordered a total of " + tv_grantotal.getText().toString() + " pesos." +
                                        "Are you sure you want to place this orders?")
                                .setPositiveButton("Yes",

                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {


                                                try {
                                                    newOrder(userid);
                                                    finishOrder();
                                                    deleteAll();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                            }

                                        }).setNegativeButton("No", null).show();

                    } else {
                        Toast.makeText(getActivity(),
                                "You have no orders yet.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv_name = (TextView) view
                .findViewById(R.id.textView_order_summary_productname);
        TextView tv_qty = (TextView) view
                .findViewById(R.id.textView_order_summary_qty);
        String qty = tv_qty.getText().toString();
        final String name = tv_name.getText().toString();
        new AlertDialog.Builder(getActivity())
                // Alert dialog for confirmation
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Confirm")
                .setMessage(
                        "Are you sure you want to remove " + qty + " (" + name
                                + "(s)) from your orders?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                delete(name);
                                populate();
                                Toast.makeText(getActivity(),
                                        name + " is deleted from your orders.",
                                        Toast.LENGTH_LONG).show();


                            }

                        }).setNegativeButton("No", null).show();

        return true;
    }

    public void populate() {

        List<OrderTable> orders = orderDaoManager.getAllOrders(getActivity());
        if (orders.toArray().length == 0) {
            List<String> stringList = new ArrayList<String>();
            stringList.add("You have no orders yet.");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stringList);
            lv.setAdapter(adapter);
        } else {
            OrderSummaryDaoAdapter myadapter = new OrderSummaryDaoAdapter(getActivity(), R.layout.order_summary_holder, orders);
            lv.setAdapter(myadapter);
            lv.setOnItemLongClickListener(this);
            lv.setOnItemClickListener(this);
        }

        Integer count = orders.toArray().length;
        Double grandTotal = 0.00;
        Product product = new Product();
        for (int i = 0; i < count; i++) {
            orders.get(i);

            product.productId = Integer.parseInt(String.valueOf(orders.get(i).getId()));
            grandTotal += orders.get(i).getProductSubtotal();

            product.productQty = orders.get(i).getProductQty();

        }
        tv_grantotal.setText(grandTotal.toString());


    }

    public void delete(String name) {
        orderDaoManager.deleteSpecificByName(context, name);
        populate();

    }

    public void newOrder(Integer userid) throws Exception {
        Order order = productsRetrotfitManager.newOrder(userid);
        orderId = order.orderId;
        Log.e("orderId", orderId + "");
    }

    public void placeOrder(Integer orderid, Product product) throws Exception {
        productsRetrotfitManager.placeOrder(orderid, product);

    }

    public void deleteAll() {
        orderDaoManager.deleteAll(getActivity());
        populate();

    }

    public void finishOrder() throws Exception {

        List<OrderTable> products = orderDaoManager.getAllOrders(getActivity());
        Integer count = products.toArray().length;

        for (int i = 0; i < count; i++) {

            products.get(i);
            Product product = new Product();
            product.productId = Integer.parseInt(String.valueOf(products.get(i).getId()));
            product.productQty = products.get(i).getProductQty();

            placeOrder(orderId, product);

        }


    }

    @Subscribe
    public void refreshListView(Order event) {
        populate();
    }

    public static OrderSummaryFragment newInstance() {
        return newInstance(R.layout.order_summary);
    }

    public static OrderSummaryFragment newInstance(int layoutId) {
        OrderSummaryFragment orderSummaryFragment = new OrderSummaryFragment();
        orderSummaryFragment.layoutId = layoutId;
        orderSummaryFragment.setRetainInstance(false);
        return orderSummaryFragment;
    }


}
