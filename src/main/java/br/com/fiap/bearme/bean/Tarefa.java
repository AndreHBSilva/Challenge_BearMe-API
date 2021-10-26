package br.com.fiap.bearme.bean;

import java.util.List;
import java.util.Objects;

/**
 * Classe que contém as informacoes do dominio de Tarefa
 * 
 * @author TechCare Team - BearMe
 */
public class Tarefa {

	private Integer codigo;
	private String descricao;
	private Integer pontuacao;
	private List<TarefaRealizada> tarefas;

	/**
	 * Construtor do objeto Tarefa
	 * 
	 * @param codigo da tarefa
	 * @param descricao da tarefa
	 * @param pontuacao da tarefa
	 */
	public Tarefa(Integer codigo, String descricao, Integer pontuacao) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.pontuacao = pontuacao;
	}

	/**
	 * Construtor do objeto Tarefa
	 * 
	 * @param codigo da tarefa
	 * @param descricao da tarefa
	 * @param pontuacao da tarefa
	 * @param lista de tarefas realizadas
	 */
	public Tarefa(Integer codigo, String descricao, Integer pontuacao, List<TarefaRealizada> tarefas) {
		this(codigo, descricao, pontuacao);
		this.tarefas = tarefas;
	}

	public Tarefa() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getPontuacao() {
		return pontuacao;
	}

	public List<TarefaRealizada> getTarefas() {
		return tarefas;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	public void setTarefas(List<TarefaRealizada> tarefas) {
		this.tarefas = tarefas;
	}

	@Override
	public String toString() {
		return "Tarefa [codigo=" + codigo + ", descricao=" + descricao + ", pontuacao=" + pontuacao + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, descricao, pontuacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarefa other = (Tarefa) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(pontuacao, other.pontuacao);
	}

}
