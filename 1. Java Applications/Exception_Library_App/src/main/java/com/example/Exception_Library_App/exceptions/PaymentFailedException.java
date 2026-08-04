package com.example.Exception_Library_App.exceptions;

//These represent custom-defined problem scenarios.
public class PaymentFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PaymentFailedException() {
		super();
	}

	public PaymentFailedException(String msg) {
		super(msg);
	}
}