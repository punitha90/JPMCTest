package com.JPMCTest.NotificationProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PriceAdjustment {

	private double priceAfterAdjust;

	private Product product;

	public PriceAdjustment(Product product) {
		this.product = product;
		this.priceAfterAdjust = 0.0;
	}

	public double getPriceAfterAdjust() {
		String adjustmentMethod = String.format("%sValue", product.getAdjustmentOperator().toLowerCase());
		try {
			Method method = this.getClass().getMethod(adjustmentMethod, null);
			method.invoke(this, null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return priceAfterAdjust;
	}

	public void addValue() {
		this.priceAfterAdjust = this.product.getTotalPrice()
				+ (this.product.getTotalQuantity() * this.product.getProductPrice());
	}

	public void subtractValue() {
		this.priceAfterAdjust = this.product.getTotalPrice()
				- (this.product.getTotalQuantity() * this.product.getProductPrice());
	}

	public void multiplyValue() {
		this.priceAfterAdjust = this.product.getTotalPrice()
				+ (this.product.getTotalPrice() * this.product.getProductPrice())
				+ (this.product.getTotalQuantity() * this.product.getProductPrice());
	}

	public String reportAfterAdjustment() {
		String reportAfterAdjustment = String.format("%sed %.2fp to %d %s and price has been changed to %.2fp to %.2fp",
				this.product.getAdjustmentOperator(), this.product.getProductPrice(), this.product.getTotalQuantity(),
				this.product.getProductType(), this.product.getTotalPrice(), this.priceAfterAdjust);
		return reportAfterAdjustment;
	}

}
