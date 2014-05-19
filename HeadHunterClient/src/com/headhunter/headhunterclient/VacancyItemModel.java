package com.headhunter.headhunterclient;

import java.io.Serializable;
import java.util.Date;

import android.graphics.drawable.Drawable;

public class VacancyItemModel implements Serializable {
	private static final long serialVersionUID = 6623580287362063222L;
	private int id;
	private Date date;
	private String name;
	private PublisherItemModel publisher;
	private String url;
	private String description;
	private transient Drawable publisherIcon;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Drawable getPublisherIcon() {
		return publisherIcon;
	}

	public void setPublisherIcon(Drawable publisherIcon) {
		this.publisherIcon = publisherIcon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PublisherItemModel getPublisher() {
		return publisher;
	}

	public void setPublisher(PublisherItemModel publisher) {
		this.publisher = publisher;
	}
}
