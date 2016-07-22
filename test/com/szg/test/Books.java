package com.szg.test;

/**
 * @author SZG
 * 
 */
public class Books {
	public Books(String title, int num) {
		this.title = title;
		this.num = num;
	}

	public Books() {
		// TODO Auto-generated constructor stub
	}

	private String title;

	private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	private int num;

	@Override
	public String toString() {
		return "Books [title=" + title + ", price=" + price + ", num=" + num
				+ "]";
	}

}
