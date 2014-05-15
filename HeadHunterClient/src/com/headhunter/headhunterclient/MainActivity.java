package com.headhunter.headhunterclient;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private HeadHunterHelper dataHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dataHelper = new HeadHunterHelper();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void searchVacancyByText(View view){
		EditText etSearch = (EditText)findViewById(R.id.editTextSearch);
		String textString = etSearch.getText().toString();
		dataHelper.execute(textString);
	}
}
