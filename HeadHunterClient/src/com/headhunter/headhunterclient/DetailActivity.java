package com.headhunter.headhunterclient;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class DetailActivity extends Activity {

	public static final String DATE_FORMAT = "dd-MM-yyyy ss:mm:HH";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		VacancyItemModel item = (VacancyItemModel) getIntent().getSerializableExtra(MainActivity.EXTRA_VACANCY);
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, getResources().getConfiguration().locale);
		sb.append(sdf.format(item.getDate()));
		sb.append(":\n");
		sb.append(item.getPublisher().getName());
		sb.append(":\n");
		sb.append(item.getName());
		TextView tv = (TextView)findViewById(R.id.text_detail);
		tv.setText(sb.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

}
