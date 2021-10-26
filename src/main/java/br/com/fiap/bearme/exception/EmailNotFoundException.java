package br.com.fiap.bearme.exception;

public class EmailNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public EmailNotFoundException() {
		super("E-mail não encontrado");
	}
	
	public EmailNotFoundException(String msg) {
		super(msg);
	}

}
