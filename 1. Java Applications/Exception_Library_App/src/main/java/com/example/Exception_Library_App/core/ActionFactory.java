package com.example.Exception_Library_App.core;

import com.example.Exception_Library_App.actions.Action;

/**
	Allows adding new business actions without code changes—only updating XML.
	Then create action java file.
 */
/**
 * Factory class responsible for creating Action instances based on action types
 * defined in the XML configuration. The action type (e.g., "email", "log",
 * "sms") is converted to a class name using the naming convention <Type>Action
 * (EmailAction, LogAction, etc.).
 */
public final class ActionFactory {

	/** Base package where all Action implementation classes are located. */
	private static final String ACTIONS_PACKAGE = "com.example.Exception_Library_App.actions";

	private ActionFactory() {
	}

	/**
	 * Creates an Action implementation by converting the XML action type into a
	 * matching class name and instantiating it.
	 *
	 * @param actionType XML action tag name (e.g., "email", "log", "sms")
	 * @return corresponding Action instance
	 */
	public static Action create(String actionType) {

		if (actionType == null || actionType.isBlank()) {
			throw new IllegalArgumentException("Action type cannot be null or empty.");
		}

		// Convert "email" -> "EmailAction"
		String actionClassName = capitalize(actionType) + "Action";
		String fullyQualifiedClassName = ACTIONS_PACKAGE + "." + actionClassName;

		try {
			Class<?> actionClass = Class.forName(fullyQualifiedClassName);
			Object instance = actionClass.getDeclaredConstructor().newInstance();

			if (!(instance instanceof Action)) {
				throw new IllegalStateException(fullyQualifiedClassName + " does not implement Action interface.");
			}

			return (Action) instance;

		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(
					"No Action class found for type: " + actionType + " (expected: " + fullyQualifiedClassName + ")",
					e);

		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(fullyQualifiedClassName + " must have a public no‑argument constructor.",
					e);

		} catch (ReflectiveOperationException e) {
			throw new RuntimeException("Failed to create Action instance: " + fullyQualifiedClassName, e);
		}
	}

	/** Capitalizes the first character of the string. */
	private static String capitalize(String value) {
		return (value == null || value.isEmpty()) ? value : Character.toUpperCase(value.charAt(0)) + value.substring(1);
	}
}