package com.hernaez.seven_eleven.viewcontroller.adapter;

import java.util.List;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.RowItem;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomViewAdapter2 extends ArrayAdapter<RowItem> {

	Context context;

	public CustomViewAdapter2(Context context, int resourceId,
			List<RowItem> items) {
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
		RowItem rowItem = getItem(position);

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

		holder.prodname.setText(rowItem.getProdName());
		holder.prodqty.setText(rowItem.getProdQty());
		holder.prodprice.setText(rowItem.getProdPrice());
		holder.prodsubtotal.setText(rowItem.getSubtotal());

		String url;
		url = new String(rowItem.getImageURL());
		Picasso.with(context).load(url).resize(150, 150).centerCrop()
				.into(holder.imageView);

		return convertView;
	}
}
