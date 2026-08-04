package com.example.Exception_Library_App.actions;

import java.util.Map;

// When X error happens, what actions must we take?
/** Simple, side-effect oriented action contract. */
public interface Action {
	/**
	 * Execute action with attributes parsed from XML and optional context map.
	 */
	void doAction(Map<String, String> attributes, Map<String, Object> context);
}