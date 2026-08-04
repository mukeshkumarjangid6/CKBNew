package com.game_scheduler.exceptions;

/** Thrown when a requested entity (Game/Player/Day) does not exist. */
public class NotFoundException extends SchedulerException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message);
	}
}