package com.JPMCTest.NotificationProcessor;

public class NotificationParser {

	private String type;

	private double price;

	private int quantity;

	private String operatorType;

	public NotificationParser(String message) {
		this.type = "";
		this.price = 0.0;
		this.quantity = 0;
		this.operatorType = "";
		parseNotification(message);
	}

	private boolean parseNotification(String message) {
		if (message == null || message.isEmpty()) {
			return false;
		}
		String[] messages = message.trim().split("\\s+");
		String begginingWord = messages[0];
		if (begginingWord.matches("Add|Subtract|Multiply")) {
			return parseMessageType3(messages);
		} else if (begginingWord.matches("^\\d+")) {
			return parseMessageType2(messages);
		} else if (messages.length == 3 && messages[1].contains("at")) {
			return parseMessageType1(messages);
		} else {
			System.out.println("Wrong sales notice");
		}
		return true;
	}


	private boolean parseMessageType1(String[] messages) {
		if (messages.length > 3 || messages.length < 3)
			return false;
		type = typeParser(messages[0]);
		price = priceParser(messages[2]);
		quantity = 1;
		return true;
	}


	private boolean parseMessageType2(String[] messages) {
		if (messages.length > 7 || messages.length < 7)
			return false;
		type = typeParser(messages[3]);
		price = priceParser(messages[5]);
		quantity = Integer.parseInt(messages[0]);
		return true;
	}


	private boolean parseMessageType3(String[] messages) {
		if (messages.length > 3 || messages.length < 3)
			return false;
		operatorType = messages[0];
		type = typeParser(messages[2]);
		quantity = 0;
		price = priceParser(messages[1]);
		return true;
	}


	public String typeParser(String type) {
		String parsedType = "";
		String typeWithoutLastChar = type.substring(0, type.length() - 1);

		if (type.endsWith("o")) {
			parsedType = String.format("%soes", typeWithoutLastChar);
		} else if (type.endsWith("y")) {
			parsedType = String.format("%sies", typeWithoutLastChar);
		} else if (type.endsWith("h")) {
			parsedType = String.format("%shes", typeWithoutLastChar);
		} else if (!type.endsWith("s")) {
			parsedType = String.format("%ss", type);
		} else {
			parsedType = String.format("%s", type);
		}
		return parsedType.toLowerCase();
	}

	public double priceParser(String rawPrice) {
		double price = Double.parseDouble(rawPrice.replaceAll("£|p", ""));
		if (!rawPrice.contains(".")) {
			price = Double.valueOf(Double.valueOf(price) / Double.valueOf("100"));
		}
		return price;
	}


	public String getType() {
		return type;
	}


	public double getPrice() {
		return price;
	}


	public String getOperatorType() {
		return operatorType;
	}

	public int getQuantity() {
		return quantity;
	}

}
