package com.example.Exception_Library_App.exceptions;

//These represent custom-defined problem scenarios..
public class InvalidCardException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidCardException() {
		super();
	}

	public InvalidCardException(String msg) {
		super(msg);
	}
}