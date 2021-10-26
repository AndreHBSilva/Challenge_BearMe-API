package br.com.fiap.bearme.exception;

public class IdNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdNotFoundException() {
		super("C�digo n�o encontrado");
	}
	
	public IdNotFoundException(String msg) {
		super(msg);
	}

}
