package br.com.fiap.bearme.bean;

import java.util.Objects;

/**
 * Classe que contém as informacoes do dominio de Conquista
 * 
 * @author TechCare Team - BearMe
 */
public class Conquista {

	private Integer codigo;
	private TarefaRealizada tarefaRealizada;
	private String descricao;
	private Integer pontos = 0;

	/**
	 * Construtor do objeto Conquista
	 * 
	 * @param codigo da conquista
	 * @param tarefaRealizada da conquista
	 * @param descricao da conquista
	 * @param pontos da conquista
	 */
	public Conquista(Integer codigo, TarefaRealizada tarefaRealizada, String descricao, Integer pontos) {
		this.codigo = codigo;
		this.tarefaRealizada = tarefaRealizada;
		this.descricao = descricao;
		this.pontos = pontos;
	}

	/**
	 * Construtor do objeto Conquista
	 * 
	 * @param codigo da conquista
	 * @param descricao da conquista
	 * @param pontos da conquista
	 */
	public Conquista(Integer codigo, String descricao, Integer pontos) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.pontos = pontos;
	}

	public Conquista() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public TarefaRealizada getTarefaRealizada() {
		return tarefaRealizada;
	}

	public void setTarefaRealizada(TarefaRealizada tarefaRealizada) {
		this.tarefaRealizada = tarefaRealizada;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getPontos() {
		return pontos;
	}

	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}

	@Override
	public String toString() {
		return "Conquista [codigo=" + codigo + ", tarefaRealizada=" + tarefaRealizada + ", descricao=" + descricao
				+ ", pontos=" + pontos + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, descricao, pontos, tarefaRealizada);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conquista other = (Conquista) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(pontos, other.pontos) && Objects.equals(tarefaRealizada, other.tarefaRealizada);
	}

}
