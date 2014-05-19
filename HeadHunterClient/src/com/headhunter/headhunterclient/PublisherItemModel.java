package com.headhunter.headhunterclient;

import java.io.Serializable;

public class PublisherItemModel implements Serializable {
	private static final long serialVersionUID = -7910044420112483544L;
	private String name;
	private int id;
	private String iconUrl;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
}
