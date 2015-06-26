package com.hernaez.seven_eleven;

public class RowItem {
	private String prodnames;
	private String prodqty;
	private String prodprice;
	private String prodimg;
	private String prodsubtotal;

	public RowItem(String prodname, String prodprice, String prodqty,
			String subtotal, String prodimg) {
		this.prodnames = prodname;
		this.prodqty = prodqty;
		this.prodprice = prodprice;
		this.prodimg = prodimg;
		this.prodsubtotal = subtotal;
	}

	public String getProdName() {
		return prodnames;
	}

	public void setProdName(String name) {
		this.prodnames = name;
	}

	public String getImageURL() {
		return prodimg;
	}

	public void setImageId(String imageId) {
		this.prodimg = imageId;
	}

	public String getProdQty() {
		return prodqty;
	}

	public void setProdQty(String qty) {
		this.prodqty = qty;
	}

	public String getProdPrice() {
		return prodprice;
	}

	public void setTitle(String price) {
		this.prodprice = price;
	}

	public String getSubtotal() {
		return prodsubtotal;
	}

	public void setTotal(String price) {
		this.prodprice = price;
	}

	@Override
	public String toString() {
		return prodnames + "\n" + prodqty + "\n" + prodprice;
	}

}
