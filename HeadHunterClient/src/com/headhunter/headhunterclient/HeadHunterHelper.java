package com.headhunter.headhunterclient;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Debug;
import android.text.format.DateFormat;

public class HeadHunterHelper extends
		AsyncTask<String, ArrayList<VacancyItemModel>, VacancyModel> {
	public static final String SERVICE_URL = "https://api.hh.ru/";
	public static final String SERVICE_VACANCIES = "vacancies";
	public static final String SERVICE_PARAMETER_TEXT = "text";
	public static final String SERVICE_PARAMETER_PAGE = "page";
	public static final String JSON_PARAMETER_NAME = "name";
	public static final String JSON_PARAMETER_ITEMS = "items";
	public static final String JSON_PARAMETER_ID = "id";
	public static final String JSON_PARAMETER_URL = "url";
	public static final String JSON_PARAMETER_EMPLOYER = "employer";
	public static final String JSON_PARAMETER_ORIGINAL = "original";
	public static final String JSON_PARAMETER_LOGO = "logo_urls";
	public static final String JSON_PARAMETER_PAGE = "page";
	public static final String JSON_PARAMETER_PAGES = "pages";
	public static final String JSON_PARAMETER_CREATED_AT = "created_at";
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	MainActivity activity;

	public HeadHunterHelper(MainActivity activity) {
		this.activity = activity;
	}

	@Override
	protected VacancyModel doInBackground(String... params) {
		Debug.waitForDebugger();
		return getDataFormService(params[0], params[1]);
	}

	private VacancyModel getDataFormService(String searchText, String page) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair(SERVICE_PARAMETER_TEXT,
					searchText));
			if (page == "") {
				page = "0";
			}
			parameters
					.add(new BasicNameValuePair(SERVICE_PARAMETER_PAGE, page));
			String requestUrl = createRequestString(SERVICE_VACANCIES,
					parameters);
			HttpGet httpget = new HttpGet(requestUrl);
			HttpResponse response = httpclient.execute(httpget);
			String xmlString = EntityUtils.toString(response.getEntity());
			return parseVacancyInfo(xmlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new VacancyModel();
	}

	private VacancyModel parseVacancyInfo(String jsonResponceString) {
		VacancyModel model = new VacancyModel();
		ArrayList<VacancyItemModel> data = new ArrayList<VacancyItemModel>();
		try {
			JSONObject jObj = new JSONObject(jsonResponceString);
			JSONArray jItems = jObj.getJSONArray(JSON_PARAMETER_ITEMS);
			model.setPage(jObj.getInt(JSON_PARAMETER_PAGE));
			model.setPages(jObj.getInt(JSON_PARAMETER_PAGES));
			for (int i = 0; i < jItems.length(); i++) {
				JSONObject item = jItems.getJSONObject(i);
				VacancyItemModel modelItem = new VacancyItemModel();
				modelItem.setId(item.getInt(JSON_PARAMETER_ID));
				modelItem.setName(item.getString(JSON_PARAMETER_NAME));
				modelItem.setUrl((item.getString(JSON_PARAMETER_URL)));
				PublisherItemModel publisher = new PublisherItemModel();
				JSONObject jPublisher = item
						.getJSONObject(JSON_PARAMETER_EMPLOYER);
				publisher.setId(jPublisher.getInt(JSON_PARAMETER_ID));
				publisher.setName(jPublisher.getString(JSON_PARAMETER_NAME));
				SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, activity.getResources().getConfiguration().locale);
				modelItem.setDate(formatter.parse(item.getString(JSON_PARAMETER_CREATED_AT)));
				if (!jPublisher.has(JSON_PARAMETER_LOGO)) {
					JSONObject jIcons = jPublisher
							.getJSONObject(JSON_PARAMETER_LOGO);
					String iconUrl = jIcons.getString(JSON_PARAMETER_LOGO);
					publisher.setIconUrl(iconUrl);
					Drawable icon = LoadImageFromWebOperations(new URL(iconUrl));
					modelItem.setPublisherIcon(icon);
				}
				modelItem.setPublisher(publisher);
				data.add(modelItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.setItems(data);
		return model;
	}

	@Override
	protected void onPostExecute(VacancyModel result) {
		activity.setData(result);
		activity.setDataFlag(false);
	};

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

	private Drawable LoadImageFromWebOperations(URL url) {
		try {
			InputStream is = (InputStream) url.getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			return null;
		}
	}
}
