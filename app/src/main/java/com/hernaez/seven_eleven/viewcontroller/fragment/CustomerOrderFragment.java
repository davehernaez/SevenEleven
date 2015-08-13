package com.hernaez.seven_eleven.viewcontroller.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.businesslayer.OrderDaoManager;
import com.hernaez.seven_eleven.model.businesslayer.ProductsRetrotfitManager;
import com.hernaez.seven_eleven.model.dataaccesslayer.greendao.OrderTable;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;
import com.hernaez.seven_eleven.viewcontroller.activity.MainActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by TAS on 7/21/2015.
 */
public class CustomerOrderFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher {
    volatile Integer position;
    Integer available_qty;
    String prodimg;
    Integer prodid;

    ArrayAdapter<String> adapter;


    @Inject
    ProductsRetrotfitManager productsRetrotfitManager;
    @Inject
    OrderDaoManager orderDaoManager;
    @Inject
    AndroidUtils androidUtils;

    @InjectView(R.id.button_plus)
    Button btn_plus;
    @InjectView(R.id.button_minus)
    Button btn_minus;

    @InjectView(R.id.button_buy)
    Button btn_buy;

    @InjectView(R.id.imageView_Product_chosen)
    ImageView img;

    @InjectView(R.id.textView_product_display_price)
    TextView tv_price;
    @InjectView(R.id.textView_subTotal)
    TextView tv_subTotal;

    @InjectView(R.id.spinner_product_name)
    public Spinner sp_prodname;

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
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sp_prodname.setAdapter(adapter());
            }
        });

        adapter = null;

        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_buy.setOnClickListener(this);
        img.setOnClickListener(this);

        qty.addTextChangedListener(this);

        qty.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        sp_prodname.setOnItemSelectedListener(this);


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

    public int layoutId;

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState2(Bundle outState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_Product_chosen:
                YoYo.with(Techniques.Pulse).duration(400).playOn(img);
                sp_prodname.setSelection(3, false);
                break;

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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "At least 1 item must be ordered", Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Maximum available quantity reached.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });


                }
                break;
            case R.id.button_buy:
                YoYo.with(Techniques.Pulse).duration(400).playOn(btn_buy);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
                                                order();
                                                refresh();
                                                //androidUtils.loadFragment((ActionBarActivity) getActivity(), R.id.container, new CustomerOrderFragment());
                                            }

                                        }).setNegativeButton("No", null).create().show();
                    }
                });


                break;
        }
    }

    public ArrayAdapter adapter() {
        try {
            adapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, productsRetrotfitManager.getAllNames());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return adapter;
    }

    public void order() {
        OrderTable orderTable = new OrderTable();
        orderTable.setId(Long.parseLong(prodid.toString()));
        orderTable.setProductName(sp_prodname.getSelectedItem().toString());
        orderTable.setProductQty(Integer.parseInt(qty.getText().toString()));
        orderTable.setProductPrice(Double.parseDouble(tv_price.getText().toString()));
        orderTable.setProductSubtotal(Double.parseDouble(tv_subTotal.getText().toString()));
        orderTable.setProductImgPath(prodimg);

        try {
            if (orderDaoManager.getOrderProductName(getActivity(), orderTable) == true) {
                toastMessage("Your orders were updated successfully!");
            } else {
                toastMessage("New orders placed. Thank you!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void toastMessage(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                androidUtils.alert(message);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        qty.setText("1");
        Product product = null;
        try {
            product = productsRetrotfitManager.getSpecificProduct(sp_prodname.getSelectedItem().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_price.setText(product.productPrice.toString());
        prodimg = product.productImgpath;
        prodid = product.productId;
        available_qty = product.productQty;
        getSubTotal();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        Picasso.with(getActivity()).load(product.productImgpath).resize(150, 120).into(img);
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

    public void refresh() {
        qty.setText("1");
    }


    public static CustomerOrderFragment newInstance() {
        return newInstance(R.layout.customer_order);
    }

    public static CustomerOrderFragment newInstance(int layoutId) {
        CustomerOrderFragment customerOrderFragment = new CustomerOrderFragment();
        customerOrderFragment.layoutId = layoutId;
        customerOrderFragment.setRetainInstance(false);
        return customerOrderFragment;
    }
}
