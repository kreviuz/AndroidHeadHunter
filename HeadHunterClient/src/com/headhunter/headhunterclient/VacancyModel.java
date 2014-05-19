package com.headhunter.headhunterclient;

import java.util.ArrayList;

public class VacancyModel {
	private ArrayList<VacancyItemModel> items;
	private int page;
	private int pages;
	public ArrayList<VacancyItemModel> getItems() {
		return items;
	}
	public void setItems(ArrayList<VacancyItemModel> items) {
		this.items = items;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
}
