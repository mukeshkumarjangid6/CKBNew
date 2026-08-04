package com.example.Exception_Library_App.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

// For this exception, execute this action with these attributes.
/** A single configured action (e.g., <email to="..."/>) under an exception. */
public final class ActionSpec {

	// type → "email", "log", "sms"
	// attributes → {to=..., file=..., phoneNumber=...}
	private final String type;
	private final Map<String, String> attributes;

	public ActionSpec(String type, Map<String, String> attributes) {
		this.type = type;
		// keep insertion order for predictable prints
		this.attributes = attributes == null ? Collections.emptyMap() : new LinkedHashMap<>(attributes);
	}

	public String getType() {
		return type;
	}

	public Map<String, String> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}
}