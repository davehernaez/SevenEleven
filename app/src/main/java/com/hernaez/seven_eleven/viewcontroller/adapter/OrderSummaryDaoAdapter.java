package com.hernaez.seven_eleven.viewcontroller.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.dataaccesslayer.greendao.OrderTable;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by TAS on 8/5/2015.
 */
public class OrderSummaryDaoAdapter extends ArrayAdapter<OrderTable>{
    Context context;

    public OrderSummaryDaoAdapter(Context context, int resourceId,
                                  List<OrderTable> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView imageView;
        TextView prodname;
        TextView prodqty;
        TextView prodprice;
        TextView prodsubtotal;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        OrderTable rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater
                    .inflate(R.layout.order_summary_holder, null);
            holder = new ViewHolder();
            holder.prodname = (TextView) convertView
                    .findViewById(R.id.textView_order_summary_productname);
            holder.prodqty = (TextView) convertView
                    .findViewById(R.id.textView_order_summary_qty);

            holder.prodprice = (TextView) convertView
                    .findViewById(R.id.textView_order_summary_price);
            holder.prodsubtotal = (TextView) convertView
                    .findViewById(R.id.textView_subtotal_summary);
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.imageView_order_summary_image);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.prodname.setText(rowItem.getProductName());
        holder.prodname.setTextColor(Color.parseColor("#AAAAAA"));
        holder.prodqty.setText(rowItem.getProductQty().toString());
        holder.prodqty.setTextColor(Color.parseColor("#AAAAAA"));
        holder.prodprice.setText(rowItem.getProductPrice().toString());
        holder.prodprice.setTextColor(Color.parseColor("#AAAAAA"));
        holder.prodsubtotal.setText(rowItem.getProductSubtotal().toString());
        holder.prodsubtotal.setTextColor(Color.parseColor("#FF0000"));

        String url;
        url = new String(rowItem.getProductImgPath());
        Picasso.with(context).load(url).resize(150, 150).centerCrop()
                .into(holder.imageView);

        return convertView;
    }
}
