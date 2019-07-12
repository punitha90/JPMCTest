package com.JPMCTest.NotificationProcessor;

import java.util.ArrayList;
import java.util.HashMap;

public class Logger {

	private HashMap<String, Product> lineItems = new HashMap();

	private double totalSalesValue;

	private ArrayList normalReports;

	private ArrayList adjustmentReports;

	public Logger() {
		this.normalReports = new ArrayList();
		this.adjustmentReports = new ArrayList();
		this.totalSalesValue = 0.0;
	}

	public Product getProduct(String type) {
		return lineItems.getOrDefault(type, new Product(type));
	}

	public void updateProduct(Product product) {
		lineItems.put(product.getProductType(), product);
	}

	public ArrayList getNormalReports() {
		return normalReports;
	}

	public void setNormalReports(String normalReport) {
		this.normalReports.add(normalReport);
	}

	public ArrayList getAdjustmentReports() {
		return adjustmentReports;
	}

	public void setAdjustmentReports(String adjustmentReport) {
		this.adjustmentReports.add(adjustmentReport);
	}

	public double getTotalSalesValue() {
		return totalSalesValue;
	}

	public void appendTotalSalesValue(double productTotalPrice) {
		totalSalesValue += productTotalPrice;
	}

	public void setTotalSalesValue(double productTotalPrice) {
		totalSalesValue = productTotalPrice;
	}

	public void report() {

		if ((normalReports.size() % 10) == 0 && normalReports.size() != 0) {
			setTotalSalesValue(0.0);
			System.out.println("***** REPORT AFTER PROCESSING 10 NOTIFICATIONS   ******");
			System.out.println("|PRODUCT NAME         |QUANTITY   |PRICE |");
			lineItems.forEach((k, v) -> formatReports(k, v));
			System.out.println("********************************************************");
			System.out.println(String.format("|%-30s|%-11.2f|", "TOTAL PRICE", getTotalSalesValue()));
			System.out.println("********************************************************");
			System.out.println("End\n\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if ((normalReports.size() % 50) == 0 && normalReports.size() != 0) {
			System.out.println(
					"50 MESSAGES HAS BEEN REACHED.IN ORDER TO PROCESS FURTHER FOLLOWING ADJUSTMENTS HAS BEEN MADE.;\n");

			getAdjustmentReports().forEach(System.out::println);
			System.exit(1);
		}
	}

	public void formatReports(String type, Product product) {
		String lineItem = String.format("|%-18s|%-11d|%-11.2f|", product.getProductType(), product.getTotalQuantity(),
				product.getTotalPrice());
		appendTotalSalesValue(product.getTotalPrice());
		System.out.println(lineItem);
	}

}
