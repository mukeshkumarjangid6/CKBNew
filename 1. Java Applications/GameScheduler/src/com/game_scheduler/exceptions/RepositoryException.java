package com.game_scheduler.exceptions;

/** Wraps unexpected repository/storage layer errors. */
public class RepositoryException extends SchedulerException {
	private static final long serialVersionUID = 1L;

	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
	}
}