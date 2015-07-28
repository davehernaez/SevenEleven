package com.hernaez.seven_eleven.viewcontroller.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.model.businesslayer.ProductManager;
import com.hernaez.seven_eleven.model.businesslayer.ReOrder;
import com.hernaez.seven_eleven.viewcontroller.adapter.CustomViewAdapter;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by TAS on 7/28/2015.
 */
public class ReOrderFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    EditText dialog_qty;
    TextView tv_prodname;

    Thread thread;
    Integer userid;


    @Inject
    ReOrder reOrder;
    @Inject
    ProductManager productManager;

    @InjectView(R.id.listView_reorder)
    protected ListView lv;

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {

        populate();

        lv.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reorder, container, false);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState2(Bundle outState) {

    }

    public static ReOrderFragment newInstance() {
        return new ReOrderFragment();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        tv_prodname = (TextView) view.findViewById(R.id.textViewProduct_name);

        dialog();
    }

    public void populate() {


        CustomViewAdapter myadapter = null;
        try {
            myadapter = new CustomViewAdapter(getActivity(),
                    R.layout.list_item, productManager.getReorderProducts());
            lv.setAdapter(myadapter);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_reorder, null);
        builder.setView(dialog);

        final AlertDialog ad = builder.create();
        ad.show();
        ad.setCancelable(false);

        Button btn_minus = (Button) dialog
                .findViewById(R.id.buttondialog_minus);
        Button btn_plus = (Button) dialog.findViewById(R.id.buttondialog_plus);
        Button btn_ok = (Button) dialog.findViewById(R.id.buttondialog_ok);
        Button btn_cancel = (Button) dialog.findViewById(R.id.buttondialog_cancel);

        TextView tv_title = (TextView) dialog.findViewById(R.id.textView_dialog_title);
        tv_title.setText("How many " + tv_prodname.getText().toString() + "(s) would you like to order from supplier?");

        dialog_qty = (EditText) dialog
                .findViewById(R.id.editText_dialog_qty);

        btn_minus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (Integer.parseInt(dialog_qty.getText().toString()) > 1) {
                    Integer qty = Integer.parseInt(dialog_qty.getText()
                            .toString());
                    qty--;
                    dialog_qty.setText(qty.toString());
                }

            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (Integer.parseInt(dialog_qty.getText().toString()) < 1000) {
                    Integer qty = Integer.parseInt(dialog_qty.getText()
                            .toString());
                    qty++;
                    dialog_qty.setText(qty.toString());
                }

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                reorder(tv_prodname.getText().toString(), dialog_qty.getText().toString());
                populate();
                ad.dismiss();
                Toast.makeText(getActivity(), "Order completed. Your product's quantity has been updated.", Toast.LENGTH_LONG).show();

            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });


    }

    public void reorder(String product_name, String product_qty) {

        try {
            reOrder.reOrder(product_name, product_qty);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
