package com.headhunter.headhunterclient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VacancyAdapter extends BaseAdapter {

	private ArrayList<VacancyItemModel> data;
	private int page = 0;
	private int pageCount = 1;
	private Context context;
	public static final String DATE_FORMAT = "dd-MM-yyyy ss:mm:HH";

	public VacancyAdapter(VacancyModel model, Context context) {
		this.data = model.getItems();
		this.page = model.getPage();
		this.pageCount = model.getPages();
		this.context = context;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public VacancyItemModel getItem(int position) {
		return data.get(position);
	}

	public void addItem(VacancyItemModel item) {
		data.add(item);
	}

	public void clearData() {
		data.clear();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_item, parent, false);

			TextView text = (TextView) convertView.findViewById(R.id.text_item);
			ImageView image = (ImageView) convertView
					.findViewById(R.id.image_item);

			ViewHolder vh = new ViewHolder(text, image);

			convertView.setTag(vh);
		}
		ViewHolder vh = (ViewHolder) convertView.getTag();
		VacancyItemModel item = data.get(position);
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, context.getResources().getConfiguration().locale);
		sb.append(sdf.format(item.getDate()));
		sb.append(":\n");
		sb.append(item.getPublisher().getName());
		sb.append(":\n");
		sb.append(item.getName());
		vh.text.setText(sb.toString());
		vh.image.setImageDrawable(item.getPublisherIcon());
		
		return convertView;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	private class ViewHolder {
		public final TextView text;
		public final ImageView image;

		public ViewHolder(TextView text, ImageView image) {
			this.text = text;
			this.image = image;
		}
	}
}
