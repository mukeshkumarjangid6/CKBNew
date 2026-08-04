package com.example.Exception_Library_App.actions;

import java.util.Map;

public final class LogAction implements Action {
	@Override
	public void doAction(Map<String, String> attributes, Map<String, Object> context) {
		System.out.println("LOG --> " + attributes);
	}
}