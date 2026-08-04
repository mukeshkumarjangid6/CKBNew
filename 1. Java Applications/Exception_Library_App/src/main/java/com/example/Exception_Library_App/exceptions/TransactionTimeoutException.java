package com.example.Exception_Library_App.exceptions;

//These represent custom-defined problem scenarios.
public class TransactionTimeoutException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TransactionTimeoutException() {
		super();
	}

	public TransactionTimeoutException(String msg) {
		super(msg);
	}
}