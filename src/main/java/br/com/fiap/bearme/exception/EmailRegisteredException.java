package br.com.fiap.bearme.exception;

public class EmailRegisteredException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmailRegisteredException() {
		super("Usuário já cadastrado!");
	}
	
	public EmailRegisteredException(String msg) {
		super(msg);
	}
}
