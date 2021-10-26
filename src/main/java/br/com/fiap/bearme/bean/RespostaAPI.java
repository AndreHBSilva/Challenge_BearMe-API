package br.com.fiap.bearme.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class RespostaAPI {

	private String mensagem;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
