package com.example.Exception_Library_App.core;

import java.util.List;
import java.util.Map;
import com.example.Exception_Library_App.actions.Action;
import com.example.Exception_Library_App.config.ActionSpec;
import com.example.Exception_Library_App.config.ConfigView;

/** Facade: single entry point for exception handling.
  	When a business module throws an exception:
	-It looks up the matching configuration.
	-Runs each action: Email → Log → SMS.
 *  */
public final class ExceptionHandler {
	private final ConfigView config;

	public ExceptionHandler(ConfigView config) {
		this.config = config;
	}

	/**
	 * Resolve and run configured actions for the given exception in project/module.
	 * Uses simple class name (e.g., "PaymentFailedException") to match XML.
	 */
	public void handle(Exception e, String project, String module, Map<String, Object> context) {
		String exceptionName = e.getClass().getSimpleName();
		List<ActionSpec> actions = config.getActions(project, module, exceptionName);

		if (actions.isEmpty()) {
			System.out.println("No configured actions for " + project + "/" + module + "/" + exceptionName);
			return;
		}

		for (ActionSpec spec : actions) {
			try {
				Action action = ActionFactory.create(spec.getType());
				action.doAction(spec.getAttributes(), context);
			} catch (Exception ex) {
				// Log & continue with next action
				System.err.println("Action failed: " + spec.getType() + " due to " + ex.getMessage());
			}
		}
	}
}