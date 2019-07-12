package com.JPMCTest;

import java.io.BufferedReader;
import java.io.FileReader;

import com.JPMCTest.NotificationProcessor.Sales;

public class SalesProcessor {
	public static void main(String[] args) {
		 Sales sales = new Sales();
		try {
			BufferedReader messageFile = new BufferedReader(new FileReader("C://Users//divya//IdeaProjects//JPMCTest//SalesNotificationProcessor//src//com//JPMCTest//NotificationProcessor//messages.txt"));
			while (messageFile.readLine() != null) {
				sales.processMessage(messageFile.readLine());
				sales.logger.report();
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
}
