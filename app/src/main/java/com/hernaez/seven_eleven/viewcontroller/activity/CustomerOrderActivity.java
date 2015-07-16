package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.model.businesslayer.GetSpecificProduct;
import com.hernaez.seven_eleven.model.businesslayer.OrderManager;
import com.hernaez.seven_eleven.model.businesslayer.ProductList;
import com.hernaez.seven_eleven.model.dataaccesslayer.DBHelper;
import com.hernaez.seven_eleven.model.dataaccesslayer.OrderDao;
import com.hernaez.seven_eleven.other.dagger.Injector;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by TAS on 7/7/2015.
 */
public class CustomerOrderActivity extends Activity implements View.OnClickListener,
       /* TextWatcher,*/ AdapterView.OnItemSelectedListener, TextWatcher {
    DBHelper dbhelper;
    ImageView img;
    TextView tv_price, tv_subTotal;
    Spinner sp_prodname;
    EditText qty;
    Button btn_plus, btn_minus, btn_finish, btn_buy;
    Integer available_qty;
    String prodnamespecific;
    Intent i;
    String prodimg;
    SQLiteDatabase db;
    Integer overallqty;
    String userid, prodid;
    ArrayList<String> myList2;
    JSONObject jsonObject;
    /*ProductList productList;*/
    GetSpecificProduct getSpecificProduct;
    ArrayAdapter<String> adapter;
    OrderDao orderDao;
    OrderManager orderManager;
    @Inject
    ProductList productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.customer_order);
        adapter = null;
        Injector.inject(this);
        /*productList = new ProductList();*/
        getSpecificProduct = new GetSpecificProduct();


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userid = extras.getString("user_id");
            Log.e("userid", userid);
        }

        dbhelper = new DBHelper(this);
        orderDao = new OrderDao(dbhelper);
        orderManager = new OrderManager(orderDao);

        db = dbhelper.getWritableDatabase();

        img = (ImageView) findViewById(R.id.imageView_Product_chosen);

        tv_price = (TextView) findViewById(R.id.textView_product_display_price);
        tv_subTotal = (TextView) findViewById(R.id.textView_subTotal);

        sp_prodname = (Spinner) findViewById(R.id.spinner_product_name);

        qty = (EditText) findViewById(R.id.editText_qtydisplay);

        btn_plus = (Button) findViewById(R.id.button_plus);
        btn_minus = (Button) findViewById(R.id.button_minus);
        btn_finish = (Button) findViewById(R.id.button_finish_ordering);
        btn_buy = (Button) findViewById(R.id.button_buy);

        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        btn_buy.setOnClickListener(this);

        //method that populates spinner
        Thread thread = new Thread() {
            public void run() {
                getAll();
            }
        };


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

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
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
                    Toast.makeText(getApplicationContext(),
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
                    Toast.makeText(getApplicationContext(),
                            "Maximum available quantity reached.",
                            Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.button_finish_ordering:
                YoYo.with(Techniques.Pulse).duration(400).playOn(btn_finish);
                i = new Intent(this, OrderSummaryActivity.class);
                i.putExtra("user_id", userid);
                startActivity(i);

                break;
            case R.id.button_buy:
                YoYo.with(Techniques.Pulse).duration(400).playOn(btn_buy);
                new AlertDialog.Builder(this)
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

    public void getAll() {
        try {


            try {
                adapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item, productList.getAllProductsName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sp_prodname.setAdapter(adapter);
                }
            });
        } catch (Exception e) {
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                qty.setText("1");
                try {
                    Product product = getSpecificProduct.getSpecificProduct(sp_prodname.getSelectedItem().toString());
                    tv_price.setText(product.product_price.toString());
                    prodimg = product.product_imgpath;
                    prodid = product.id;
                    available_qty = Integer.parseInt(product.product_qty);
                    getSubTotal();

                    Picasso.with(getApplicationContext()).load(product.product_imgpath).resize(150, 150).into(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getSubTotal();
                // TODO Auto-generated method stub
            }
        });


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }


    public void checkOrder() {
        Product product = new Product();
        product.id = prodid;
        product.product_name = sp_prodname.getSelectedItem().toString();
        product.product_qty = qty.getText().toString();
        product.product_price = tv_price.getText().toString();
        product.subtotal = tv_subTotal.getText().toString();
        product.product_imgpath = prodimg;

        Order order = new Order();
        order.product = product;
        order.total = tv_subTotal.getText().toString();
        if (
                orderManager.checkOrders(order) == true) {
            toastMessage("New orders placed. Thank you!");
        } else if (orderManager.checkOrders(order) == false) {
            toastMessage("Added " + product.product_qty + " piece(s) to previously ordered " + product.product_name + "(s)");
        }

    }

    public void toastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        qty.setText("1");
        db.close();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        getAll();
    }

    public static void start(Activity me, User user) {
        Intent intent = new Intent(me, CustomerOrderActivity.class);
        intent.putExtra("user_id", user.userId);
        me.startActivity(intent);
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