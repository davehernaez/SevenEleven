package com.hernaez.seven_eleven.viewcontroller.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomViewAdapter extends ArrayAdapter<Product> {

    Context context;

    public CustomViewAdapter(Context context, int resourceId,
                             List<Product> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    class ViewHolder {
        ImageView imageView;
        TextView prodname;
        TextView prodqty;
        TextView prodprice;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        Product product = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.prodname = (TextView) convertView.findViewById(R.id.textViewProduct_name);
            holder.prodqty = (TextView) convertView.findViewById(R.id.textViewProduct_qty);
            holder.prodprice = (TextView) convertView.findViewById(R.id.textViewProduct_price);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageViewProduct_image);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.prodname.setText(product.product_name);

        holder.prodqty.setText(product.product_qty);
        if(Integer.parseInt(holder.prodqty.getText().toString())<10){
            holder.prodqty.setTextColor(Color.parseColor("#FF0000"));
        }


        System.out.println("" + holder.prodqty.getText().toString());
        holder.prodprice.setText(product.product_price);
        holder.prodprice.setTextColor(Color.parseColor("#AAAAAA"));

        String url;
        url = new String(product.product_imgpath);
        Picasso.with(context).load(url).resize(150, 150).into(holder.imageView);

        return convertView;
    }
}
