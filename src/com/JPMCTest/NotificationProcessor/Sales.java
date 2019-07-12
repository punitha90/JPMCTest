package com.JPMCTest.NotificationProcessor;

public class Sales {
	public Logger logger;
	private PriceAdjustment priceAdjustment;
	private Product product;

	public Sales() {
		logger = new Logger();
	}

	public boolean processMessage(String message) {

		NotificationParser notificationParser = new NotificationParser(message);

		String type = notificationParser.getType();
		if (type.isEmpty()) {
			return false;
		}
		this.product = logger.getProduct(type);

		this.priceAdjustment = new PriceAdjustment(product);

		this.product.setProductQuantity(notificationParser.getQuantity());
		this.product.setTotalQuantity(notificationParser.getQuantity());
		this.product.setProductPrice(notificationParser.getPrice());
		this.product.setAdjustmentOperator(notificationParser.getOperatorType());
		setProductTotalPrice();
		logger.setNormalReports(message);
		logger.updateProduct(product);
		return true;
	}

	private void setProductTotalPrice() {
		double adjustedPrice;
		double productValue;

		if (!product.getAdjustmentOperator().isEmpty()) {
			adjustedPrice = priceAdjustment.getPriceAfterAdjust();
			logger.setAdjustmentReports(priceAdjustment.reportAfterAdjustment());
			product.setTotalPrice(adjustedPrice);
		} else {
			productValue = product.calculatePrice(product.getProductQuantity(), product.getProductPrice());
			product.appendTotalPrice(productValue);
		}
	}

}
