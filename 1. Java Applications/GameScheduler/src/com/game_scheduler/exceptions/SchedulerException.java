package com.game_scheduler.exceptions;

/**
 * Root unchecked exception for the GameScheduler domain. Lets the UI catch a
 * single type while preserving specific causes.
 */
public class SchedulerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SchedulerException() {
		super();
	}

	public SchedulerException(String message) {
		super(message);
	}

	public SchedulerException(String message, Throwable cause) {
		super(message, cause);
	}
}