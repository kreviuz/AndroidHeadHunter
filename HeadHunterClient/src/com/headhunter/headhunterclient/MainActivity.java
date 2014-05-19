package com.headhunter.headhunterclient;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Context context;
	private Boolean dataFlag = false;
	private ProgressDialog dialog;

	public static final String EXTRA_VACANCY = "vacancy";
	public static final String TAG = "Main activity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_main);
		VacancyModel model = new VacancyModel();
		dialog = new ProgressDialog(this);
		dialog.setMessage(getText(R.string.message_load));
		model.setItems(new ArrayList<VacancyItemModel>());
		VacancyAdapter adapter = new VacancyAdapter(model, context);
		ListView lv = (ListView) findViewById(R.id.listViewVacancies);
		lv.setAdapter(adapter);
		lv.setOnScrollListener(new EndlessScrollListener());
		searchVacancyByText();
		EditText etSearch = (EditText) findViewById(R.id.editTextSearch);
		etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					ListView lv = (ListView) findViewById(R.id.listViewVacancies);
					VacancyAdapter adapter = (VacancyAdapter) lv.getAdapter();
					lv.setSelectionAfterHeaderView();
					adapter.clearData();
					searchVacancyByText();
					return true;
				}
				return false;
			}
		});
		lv.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListView lv = (ListView) findViewById(R.id.listViewVacancies);
				VacancyAdapter adapter = (VacancyAdapter) lv.getAdapter();
				VacancyItemModel item = adapter.getItem(position);
				Intent intent = new Intent(MainActivity.this,
						DetailActivity.class);
				intent.putExtra(EXTRA_VACANCY, item);
				try {
					startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void searchVacancyByText() {
		if (dataFlag == false) {
			ListView lv = (ListView) findViewById(R.id.listViewVacancies);
			VacancyAdapter adapter = (VacancyAdapter) lv.getAdapter();
			EditText etSearch = (EditText) findViewById(R.id.editTextSearch);
			String textString = etSearch.getText().toString();
			int page = adapter.getPage();
			int pages = adapter.getPageCount();
			if (page <= pages && page >= 0) {
				setDialog(true);
				try {
					HeadHunterHelper dataHelper = new HeadHunterHelper(this);
					dataHelper.execute(textString, Integer.toString(page));
				} catch (Exception e) {
					e.printStackTrace();
					Log.e(TAG, getString(R.string.message_error));
				}
			}
		}
	}

	public void setData(VacancyModel data) {
		ListView lv = (ListView) findViewById(R.id.listViewVacancies);
		VacancyAdapter adapter = (VacancyAdapter) lv.getAdapter();
		ArrayList<VacancyItemModel> items = data.getItems();
		for (VacancyItemModel item : items) {
			adapter.addItem(item);
		}
		adapter.setPage(data.getPage());
		adapter.setPageCount(data.getPages());
		adapter.notifyDataSetChanged();
	}

	public Boolean getDataFlag() {
		return dataFlag;
	}

	public void setDialog(Boolean dataFlag) {
		if (dataFlag) {
			dialog.show();
		} else {
			dialog.dismiss();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public class EndlessScrollListener implements OnScrollListener {

		private int visibleThreshold = 5;
		private int previousTotal = 0;
		private boolean loading = true;

		public EndlessScrollListener() {
		}

		public EndlessScrollListener(int visibleThreshold) {
			this.visibleThreshold = visibleThreshold;
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (loading) {
				if (totalItemCount > previousTotal) {
					loading = false;
					previousTotal = totalItemCount;
				}
			}
			if (!loading
					&& (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
				ListView lv = (ListView) findViewById(R.id.listViewVacancies);
				VacancyAdapter adapter = (VacancyAdapter) lv.getAdapter();
				adapter.setPage(adapter.getPage() + 1);
				searchVacancyByText();
				loading = true;
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		}
	}
}
