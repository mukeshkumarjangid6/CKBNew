package com.example.Exception_Library_App;

import java.io.InputStream;
import java.util.Map;
import com.example.Exception_Library_App.config.ConfigView;
import com.example.Exception_Library_App.core.ExceptionHandler;
import com.example.Exception_Library_App.exceptions.PaymentFailedException;
import com.example.Exception_Library_App.exceptions.TransactionTimeoutException;
import com.example.Exception_Library_App.parser.XmlConfigLoader;

public class App {

	public static void main(String[] args) throws Exception {
		// Load existing XML from classpath: src/main/resources/exceptionlib.xml
		InputStream xml = App.class.getClassLoader().getResourceAsStream("exceptionlib.xml");
		if (xml == null) {
			throw new IllegalStateException("Could not find exceptionlib.xml on classpath");
		}

		ConfigView view = XmlConfigLoader.load(xml);

		// Print nested Map (project -> module -> exception -> (action -> message))
		System.out.println(view.toNestedMap());

		// Demo: simulate PaymentFailedException under
		// OrderManagementSystem/PaymentModule
		ExceptionHandler handler = new ExceptionHandler(view);
		try {
			throw new PaymentFailedException("Card gateway timeout");
		} catch (Exception e) {
			handler.handle(e, "OrderManagementSystem", "PaymentModule", Map.<String, Object>of("orderId", "O-10001"));
		}
		// --------------------------------------------------------
		try {
			throw new TransactionTimeoutException("Card gateway timeout");
		} catch (Exception e) {
			handler.handle(e, "OrderManagementSystem", "PaymentModule", Map.<String, Object>of("orderId", "O-10002") // optional
																														// context
			);
		}
	}
}