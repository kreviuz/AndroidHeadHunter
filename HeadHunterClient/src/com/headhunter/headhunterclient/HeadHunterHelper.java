package com.headhunter.headhunterclient;

import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Debug;

public class HeadHunterHelper extends AsyncTask<String, Object, Object> {
	public static final String SERVICE_URL = "https://api.hh.ru/";
	public static final String SERVICE_VACANCIES = "vacancies";
	public static final String SERVICE_PARAMETER_TEXT = "text";
	public static final String SERVICE_PARAMETER_PERIOD = "period";

	@Override
	protected VacancyInfoModel doInBackground(String... params) {
		Debug.waitForDebugger();
		return getDataFormService(params[0]);
	}

	private VacancyInfoModel getDataFormService(String searchText) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair(SERVICE_PARAMETER_PERIOD,
					searchText));
			String requestUrl = createRequestString(SERVICE_VACANCIES,
					parameters);
			HttpGet httpget = new HttpGet(requestUrl);
			httpget.addHeader("User-Agent", "MyApp/1.0 (benderbroun@gmail.com)");
			HttpResponse response = httpclient.execute(httpget);
			String xmlString = EntityUtils.toString(response.getEntity());
			return parseVacancyInfo(xmlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new VacancyInfoModel();
	}

	private VacancyInfoModel parseVacancyInfo(String xmlResponceString) {
		return new VacancyInfoModel();
	}

	private String createRequestString(String serviceName,
			ArrayList<BasicNameValuePair> parameters) {
		StringBuilder sb = new StringBuilder(SERVICE_URL);
		sb.append(serviceName);
		sb.append("?");
		for (BasicNameValuePair parameter : parameters) {
			sb.append(parameter.getName());
			sb.append("=");
			sb.append(parameter.getValue());
			sb.append("&");
		}
		return sb.toString();
	}
}
