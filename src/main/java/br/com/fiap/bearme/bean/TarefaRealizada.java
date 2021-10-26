package br.com.fiap.bearme.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe que contém as informacoes do dominio de TarefaRealizada
 * 
 * @author TechCare Team - BearMe
 */
public class TarefaRealizada {

	private Integer codigo;
	private Usuario usuario;
	private Tarefa tarefa;
	private LocalDate dataRealizacao;
	private Integer horaRealizacao;
	private Integer concluida;
	private List<Conquista> conquistas = new ArrayList<Conquista>();

	/**
	 * Construtor do objeto TarefaRealizada
	 * 
	 * @param codigo da tarefa realizada
	 * @param Usuario da tarefa realizada
	 * @param Tarefa da tarefa realizada
	 * @param dataRealizacao da tarefa realizada
	 * @param horaRealizacao da tarefa realizada
	 * @param concluida 
	 */
	public TarefaRealizada(Integer codigo, Usuario usuario, Tarefa tarefa, LocalDate dataRealizacao,
			Integer horaRealizacao, Integer concluida) {
		this.codigo = codigo;
		this.usuario = usuario;
		this.tarefa = tarefa;
		this.dataRealizacao = dataRealizacao;
		this.horaRealizacao = horaRealizacao;
		this.concluida = concluida;
	}

	/**
	 * Construtor do objeto TarefaRealizada
	 * 
	 * @param codigo da tarefa realizada
	 * @param horaRealizacao da tarefa realizada
	 * @param concluida 
	 */
	public TarefaRealizada(Integer codigo, Integer horaRealizacao, Integer concluida) {
		this.codigo = codigo;
		this.horaRealizacao = horaRealizacao;
		this.concluida = concluida;
	}

	public TarefaRealizada() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public LocalDate getDataRealizacao() {
		return dataRealizacao;
	}

	public Integer getHoraRealizacao() {
		return horaRealizacao;
	}

	public Integer isConcluida() {
		return concluida;
	}

	public List<Conquista> getConquistas() {
		return conquistas;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public void setDataRealizacao(LocalDate dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public void setHoraRealizacao(Integer horaRealizacao) {
		this.horaRealizacao = horaRealizacao;
	}

	public void setConcluida(Integer concluida) {
		this.concluida = concluida;
	}

	public void add(Conquista conquista) {
		conquistas.add(conquista);
	}

	@Override
	public String toString() {
		return "TarefaRealizada [codigo=" + codigo + ", usuario=" + usuario + ", tarefa=" + tarefa + ", dataRealizacao="
				+ dataRealizacao + ", horaRealizacao=" + horaRealizacao + ", concluida=" + concluida + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, concluida, dataRealizacao, horaRealizacao, tarefa, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TarefaRealizada other = (TarefaRealizada) obj;
		return Objects.equals(codigo, other.codigo) && concluida == other.concluida
				&& Objects.equals(dataRealizacao, other.dataRealizacao)
				&& Objects.equals(horaRealizacao, other.horaRealizacao) && Objects.equals(tarefa, other.tarefa)
				&& Objects.equals(usuario, other.usuario);
	}

}
