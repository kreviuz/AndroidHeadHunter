package com.headhunter.headhunterclient;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageAsync extends AsyncTask<String, Object, Drawable> {

	ImageView imageView;

	public ImageAsync(ImageView imageView) {
		this.imageView = imageView;
	}

	@Override
	protected Drawable doInBackground(String... params) {
		Drawable image = null;
		if (params[0] != null) {
			try {
				image = LoadImageFromWebOperations(new URL(params[0]));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return image;
	}

	private Drawable LoadImageFromWebOperations(URL url) {
		try {
			InputStream is = (InputStream) url.getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(Drawable result) {
		if (result != null) {
			imageView.setImageDrawable(result);
		}
		super.onPostExecute(result);
	}
}
