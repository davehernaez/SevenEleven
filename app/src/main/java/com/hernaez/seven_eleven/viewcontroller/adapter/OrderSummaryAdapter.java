package com.hernaez.seven_eleven.viewcontroller.adapter;

import java.util.List;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.domain.RowItem;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderSummaryAdapter extends ArrayAdapter<Product> {

	Context context;

	public OrderSummaryAdapter(Context context, int resourceId,
							   List<Product> items) {
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
		Product rowItem = getItem(position);

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

		holder.prodname.setText(rowItem.productName);
		holder.prodname.setTextColor(Color.parseColor("#AAAAAA"));
		holder.prodqty.setText(rowItem.productQty.toString());
		holder.prodqty.setTextColor(Color.parseColor("#AAAAAA"));
		holder.prodprice.setText(rowItem.productPrice.toString());
		holder.prodprice.setTextColor(Color.parseColor("#AAAAAA"));
		holder.prodsubtotal.setText(rowItem.subtotal.toString());
		holder.prodsubtotal.setTextColor(Color.parseColor("#FF0000"));

		String url;
		url = new String(rowItem.productImgpath);
		Picasso.with(context).load(url).resize(150, 150).centerCrop()
				.into(holder.imageView);

		return convertView;
	}
}
