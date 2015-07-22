package com.hernaez.seven_eleven.viewcontroller.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.Order;
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.businesslayer.GetAllProductName;
import com.hernaez.seven_eleven.model.businesslayer.GetSpecificProduct;
import com.hernaez.seven_eleven.model.businesslayer.OrderManager;
import com.hernaez.seven_eleven.model.dataaccesslayer.DBHelper;
import com.hernaez.seven_eleven.model.dataaccesslayer.OrderDao;
import com.hernaez.seven_eleven.viewcontroller.activity.OrderSummaryActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by TAS on 7/21/2015.
 */
public class CustomerOrderFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher {

    Integer available_qty;
    Intent i;
    String prodimg;
    Integer userid;
    Integer prodid;
    OrderManager orderManager;
    DBHelper dbhelper;
    OrderDao orderDao;
    ArrayAdapter<String> adapter;

    @Inject
    GetAllProductName getAllProductName;
    @Inject
    GetSpecificProduct getSpecificProduct;

    @InjectView(R.id.button_plus)
    Button btn_plus;
    @InjectView(R.id.button_minus)
    Button btn_minus;
    @InjectView(R.id.button_finish_ordering)
    Button btn_finish;
    @InjectView(R.id.button_buy)
    Button btn_buy;

    @InjectView(R.id.imageView_Product_chosen)
    ImageView img;

    @InjectView(R.id.textView_product_display_price)
    TextView tv_price;
    @InjectView(R.id.textView_subTotal)
    TextView tv_subTotal;

    @InjectView(R.id.spinner_product_name)
    Spinner sp_prodname;

    @InjectView(R.id.editText_qtydisplay)
    EditText qty;

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

        adapter = null;
        dbhelper = new DBHelper(getActivity());
        orderDao = new OrderDao(dbhelper);
        orderManager = new OrderManager(orderDao);

        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        btn_buy.setOnClickListener(this);

        qty.addTextChangedListener(this);

        qty.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        sp_prodname.setOnItemSelectedListener(this);

        try {


            try {
                adapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, getAllProductName.getAllProductsName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            sp_prodname.setAdapter(adapter);

        } catch (Exception e) {
        }

    }


    private void getSubTotal() {
        if (!TextUtils.isEmpty(qty.getText())) {

            Integer qty1 = Integer.parseInt(qty.getText().toString());
            if (qty1 == 0) {
                qty1 = 1;
                qty.setText(qty1.toString());
            }

            tv_subTotal.setText(String.format("%.2f",
                    Double.parseDouble(tv_price.getText().toString()) * qty1));

        }
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState2(Bundle outState) {

    }

    public static CustomerOrderFragment newInstance() {
        return new CustomerOrderFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_minus:
                YoYo.with(Techniques.Flash).duration(500).playOn(qty);
                YoYo.with(Techniques.Pulse).duration(400).playOn(btn_minus);
                btn_plus.setEnabled(true);
                Integer intqtyminus = Integer.parseInt(qty.getText().toString());
                if (intqtyminus > 1) {
                    btn_minus.setEnabled(true);
                    intqtyminus--;
                    qty.setText(intqtyminus.toString());
                } else if (intqtyminus == 1) {
                    Toast.makeText(getActivity(),
                            "At least 1 item must be ordered", Toast.LENGTH_LONG)
                            .show();
                    btn_minus.setEnabled(false);
                }

                break;
            case R.id.button_plus:
                YoYo.with(Techniques.Flash).duration(500).playOn(qty);
                YoYo.with(Techniques.Pulse).duration(400).playOn(btn_plus);
                btn_minus.setEnabled(true);
                Integer intqtyplus = Integer.parseInt(qty.getText().toString());
                if (intqtyplus < available_qty) {
                    btn_plus.setEnabled(true);
                    intqtyplus++;
                    qty.setText(intqtyplus.toString());
                } else if (Integer.parseInt(qty.getText().toString()) == available_qty) {
                    btn_plus.setEnabled(false);
                    Toast.makeText(getActivity(),
                            "Maximum available quantity reached.",
                            Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.button_finish_ordering:
                YoYo.with(Techniques.Pulse).duration(400).playOn(btn_finish);
                i = new Intent(getActivity(), OrderSummaryActivity.class);
                i.putExtra("user_id", userid);
                startActivity(i);

                break;
            case R.id.button_buy:
                YoYo.with(Techniques.Pulse).duration(400).playOn(btn_buy);
                new AlertDialog.Builder(getActivity())
                        // Alert dialog for confirmation
                        .setIcon(android.R.drawable.ic_input_add)
                        .setTitle("Confirm")
                        .setMessage(
                                "Are you sure you want to buy "
                                        + qty.getText().toString()
                                        + " "
                                        + sp_prodname.getSelectedItem().toString() // alert
                                        // dialog
                                        // message
                                        + "(s) for P"
                                        + tv_subTotal.getText().toString() + "?")
                        .setPositiveButton("Yes",

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        checkOrder();
                                    }

                                }).setNegativeButton("No", null).show();

                break;
        }
    }

    public void checkOrder() {
        Product product = new Product();
        product.id = prodid;
        product.product_name = sp_prodname.getSelectedItem().toString();
        product.product_qty = Integer.parseInt(qty.getText().toString());
        product.product_price = Double.parseDouble(tv_price.getText().toString());
        product.subtotal = Double.parseDouble(tv_subTotal.getText().toString());
        product.product_imgpath = prodimg;

        Order order = new Order();
        order.product = product;
        order.total = Double.parseDouble(tv_subTotal.getText().toString());
        if (
                orderManager.checkOrders(order) == true) {
            toastMessage("New orders placed. Thank you!");
        } else if (orderManager.checkOrders(order) == false) {
            toastMessage("Added " + product.product_qty + " piece(s) to previously ordered " + product.product_name + "(s)");
        }

    }

    public void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        qty.setText("1");
        Product product = null;
        try {
            product = getSpecificProduct.getSpecificProduct(sp_prodname.getSelectedItem().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_price.setText(product.product_price.toString());
        prodimg = product.product_imgpath;
        prodid = product.id;
        available_qty = product.product_qty;
        getSubTotal();

        Picasso.with(getActivity()).load(product.product_imgpath).resize(150, 120).into(img);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        getSubTotal();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
