package com.game_scheduler.exceptions;

/** Thrown when inputs are null, blank or structurally invalid. */
public class ValidationException extends SchedulerException {
	private static final long serialVersionUID = 1L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message) {
		super(message);
	}
}