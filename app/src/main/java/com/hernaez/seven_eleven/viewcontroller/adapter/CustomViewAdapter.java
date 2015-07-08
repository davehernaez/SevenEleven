package com.hernaez.seven_eleven.viewcontroller.adapter;

import java.util.List;

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
import com.hernaez.seven_eleven.domain.RowItem;
import com.squareup.picasso.Picasso;

public class CustomViewAdapter extends ArrayAdapter<RowItem> {
 
    Context context;
 
    public CustomViewAdapter(Context context, int resourceId,
            List<RowItem> items) {
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
        RowItem rowItem = getItem(position);
         
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
                 
        holder.prodname.setText(rowItem.getProdName());
        holder.prodqty.setText(rowItem.getProdQty());
        if(Integer.parseInt(rowItem.getProdQty())<=0){
        	holder.prodqty.setTextColor(Color.RED);
        }
        System.out.println(""+holder.prodqty.getText().toString());
        holder.prodprice.setText(rowItem.getProdPrice());
        
        String url;
		url = new String(rowItem.getImageURL());
		Picasso.with(context).load(url).resize(150, 150).into(holder.imageView);             
              
		// holder.imageView.setImageURI(rowItem.getImageId());
         
        return convertView;
    }
}
