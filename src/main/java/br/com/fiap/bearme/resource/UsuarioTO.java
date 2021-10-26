package br.com.fiap.bearme.resource;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class UsuarioTO {
	
	private int codigo;
	private String nickname;
	private String email;
	private String senha;
	private String dataNascimento;
	
	public UsuarioTO() {}

	public UsuarioTO(String nickname, String email, String senha, String dataNascimento) {
		this.setNickname(nickname);
		this.setEmail(email);
		this.setSenha(senha);
		this.setDataNascimento(dataNascimento);
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
}
