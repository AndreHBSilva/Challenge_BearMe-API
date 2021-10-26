package br.com.fiap.bearme.bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Classe que contém as informacoes do dominio de Usuario
 * 
 * @author TechCare Team - BearMe
 */
public class Usuario {

	private int codigo;
	private String nickname;
	private String email;
	private String senha;
	private LocalDate dataNascimento;
	private List<TarefaRealizada> tarefas;

	private static Integer totalUsuarios = 0;

	// Construtor com todas as informações obrigatórias a serem persistidas de
	// acordo com nosso MER
	
	public Usuario(int codigo, String nickname, String email, String senha, LocalDate dataNascimento) {
		this.codigo = codigo;
		this.nickname = nickname;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}
	
	public Usuario(String nickname, String email, String senha, LocalDate dataNascimento) {
		this.nickname = nickname;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}

	/**
	 * Construtor do objeto Usuario
	 * 
	 * @param id do usuario
	 * @param nickname do usuario
	 * @param email do usuario
	 * @param senha do usuario
	 * @param dataNascimento do usuario
	 * @param tarefas do usuario
	 */
	public Usuario(int id, String nickname, String email, String senha, LocalDate dataNascimento,
			List<TarefaRealizada> tarefas) {
		this(nickname, email, senha, dataNascimento);
		this.tarefas = tarefas;
	}

	public Usuario() {
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<TarefaRealizada> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<TarefaRealizada> tarefas) {
		this.tarefas = tarefas;
	}

	public static Integer getTotalUsuarios() {
		return totalUsuarios;
	}

	public static void setTotalUsuarios(Integer totalUsuarios) {
		Usuario.totalUsuarios = totalUsuarios;
	}

	@Override
	public String toString() {
		return "Usuario [codigo=" + codigo + ", nickname=" + nickname + ", email=" + email + ", senha=" + senha
				+ ", dataNascimento=" + dataNascimento + ", tarefas=" + tarefas + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, dataNascimento, email, nickname, senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(dataNascimento, other.dataNascimento)
				&& Objects.equals(email, other.email) && Objects.equals(nickname, other.nickname)
				&& Objects.equals(senha, other.senha);
	}

}
