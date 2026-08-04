package com.example.Exception_Library_App.actions;

import java.util.Map;

public final class EmailAction implements Action {
	@Override
	public void doAction(Map<String, String> attributes, Map<String, Object> context) {
		// Basic version: print; later replace with SMTP provider.
		System.out.println("EMAIL --> " + attributes);
	}
}