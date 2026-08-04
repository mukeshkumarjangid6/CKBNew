package com.example.Exception_Library_App.actions;

import java.util.Map;

public final class SmsAction implements Action {
	@Override
	public void doAction(Map<String, String> attributes, Map<String, Object> context) {
		System.out.println("SMS --> " + attributes);
	}
}