package com.example.Exception_Library_App.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
Provides a structured, immutable representation of business exception policies.
Converts XML into an enterprise-friendly nested hierarchy:
Project → Module → Exception → Actions

Example questions answered by ConfigView:
What should OMS do when PaymentFailedException occurs?
Which actions are configured for CRM → LeadModule → LeadImportFailure?
 */
/**
 * Immutable view of parsed configuration. Data shape: project -> module ->
 * exception -> [actions...]
 */
public final class ConfigView {
	private final Map<String, Map<String, Map<String, List<ActionSpec>>>> data;

	public ConfigView(Map<String, Map<String, Map<String, List<ActionSpec>>>> data) {
		// shallow copy for safety; keep order for readability
		this.data = new LinkedHashMap<String, Map<String, Map<String, List<ActionSpec>>>>(data);
	}

	/** Lookup actions for (project, module, exception). */
	public List<ActionSpec> getActions(String project, String module, String exceptionName) {
		Map<String, Map<String, List<ActionSpec>>> modules = data.getOrDefault(project,
				Collections.<String, Map<String, List<ActionSpec>>>emptyMap());

		Map<String, List<ActionSpec>> exceptions = modules.getOrDefault(module,
				Collections.<String, List<ActionSpec>>emptyMap());
		List<ActionSpec> list = exceptions.get(exceptionName);

		return list == null ? Collections.<ActionSpec>emptyList() : list;
	}

	/**
	 * Build the required nested Map view: project -> module -> exception -> (action
	 * -> actionMessage) Since XML has no explicit "message", we synthesize it from
	 * attributes (e.g., "to=...").
	 */
	public Map<String, Map<String, Map<String, Map<String, String>>>> toNestedMap() {
		Map<String, Map<String, Map<String, Map<String, String>>>> out = new LinkedHashMap<String, Map<String, Map<String, Map<String, String>>>>();

		for (Map.Entry<String, Map<String, Map<String, List<ActionSpec>>>> pEntry : data.entrySet()) {
			Map<String, Map<String, Map<String, String>>> moduleMap = new LinkedHashMap<String, Map<String, Map<String, String>>>();
			for (Map.Entry<String, Map<String, List<ActionSpec>>> mEntry : pEntry.getValue().entrySet()) {
				Map<String, Map<String, String>> exceptionMap = new LinkedHashMap<String, Map<String, String>>();
				for (Map.Entry<String, List<ActionSpec>> eEntry : mEntry.getValue().entrySet()) {
					Map<String, String> actionMap = new LinkedHashMap<String, String>();
					List<ActionSpec> specs = eEntry.getValue();
					for (ActionSpec spec : specs) {
						actionMap.put(spec.getType(), synthesizeMessage(spec.getAttributes()));
					}
					exceptionMap.put(eEntry.getKey(), actionMap);
				}
				moduleMap.put(mEntry.getKey(), exceptionMap);
			}
			out.put(pEntry.getKey(), moduleMap);
		}
		return out;
	}

	private String synthesizeMessage(Map<String, String> attrs) {
		if (attrs == null || attrs.isEmpty())
			return "";
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Map.Entry<String, String> e : attrs.entrySet()) {
			if (!first)
				sb.append(", ");
			sb.append(e.getKey()).append('=').append(e.getValue());
			first = false;
		}
		return sb.toString();
	}
}