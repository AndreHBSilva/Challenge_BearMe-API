package br.com.fiap.bearme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.bearme.bean.Tarefa;
import br.com.fiap.bearme.exception.IdNotFoundException;

/**
 * Classe que realiza as operações básicas com a Tarefa no banco de dados
 * @author Bear Me
 */
public class TarefaDao {
	
	private Connection connection;

	/**
	 * Construtor que recebe a conexão como injeção de dependência
	 * @param conexao
	 */
	public TarefaDao(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Cadastra uma nova tarefa no banco de dados
	 * @param Tarefa tarefa com os valores que serão cadastrados
	 * @throws SQLException
	 */
	public void cadastrar(Tarefa tarefa) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"INSERT INTO T_BM_TAREFA "
				+ "(CD_TAREFA, DS_TAREFA, NR_PONTUACAO) "
				+ "VALUES "
				+ "(SQ_BM_TAREFA.nextval, ?, ?)");
		
		stmt.setString(1, tarefa.getDescricao());
		stmt.setInt(2, tarefa.getPontuacao());
		
		stmt.executeUpdate();
	}
	
	/**
	 * Atualiza uma tarefa no banco de dados 
	 * @param Tarefa tarefa = com os valores que serão atualizados
	 * @throws SQLException
	 * @throws IdNotFoundException 
	 */
	public void atualizar(Tarefa tarefa) throws SQLException, IdNotFoundException {
		
		PreparedStatement stmt = connection.prepareStatement("UPDATE T_BM_TAREFA SET DS_TAREFA = ?, NR_PONTUACAO = ? WHERE CD_TAREFA = ?");
		
		stmt.setString(1, tarefa.getDescricao());
		stmt.setInt(2, tarefa.getPontuacao());
		stmt.setInt(3, tarefa.getCodigo());
		
		int qtd = stmt.executeUpdate();
		
		if (qtd == 0)
			throw new IdNotFoundException("Tarefa não encontrada");
	}
	
	/**
	 * Pesquisa uma tarefa pela PK
	 * @param codigo PK da tarefa
	 * @return Tarefa tarefa com os valores encontrados
	 * @throws SQLException
	 * @throws IdNotFoundException Não foi encontrada nenhuma tarefa com a chave informada
	 */
	public Tarefa pesquisar(int codigo) throws SQLException, IdNotFoundException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM T_BM_TAREFA WHERE CD_TAREFA = ?");
		
		stmt.setInt(1, codigo);
		
		ResultSet result = stmt.executeQuery();
		
		if (!result.next()) {
			throw new IdNotFoundException("Tarefa não encontrada");
		}
		
		int cd = result.getInt("CD_TAREFA");
		String descricao = result.getString("DS_TAREFA");
		int pontuacao = result.getInt("NR_PONTUACAO");
		
		return new Tarefa(cd, descricao, pontuacao);
	}
	
	/**
	 * Retorna todos as tarefas do banco de dados
	 * @return List<Tarefa> Lista de tarefas
	 * @throws SQLException 
	 */
	public List<Tarefa> listar() throws SQLException {
		
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM T_BM_TAREFA");	
		
		ResultSet result = stmt.executeQuery();
		
		List<Tarefa> tarefas = new ArrayList<>();
		
		while (result.next()) {
			int codigo = result.getInt("CD_TAREFA");
			String descricao = result.getString("DS_TAREFA");
			int pontuacao = result.getInt("NR_PONTUACAO");
			
			Tarefa tarefa = new Tarefa(codigo, descricao, pontuacao);
			tarefas.add(tarefa);
		}
		
		return tarefas;
	}
	
	/**
	 * Remove uma tarefa do banco de dados 
	 * @param codigo PK da tarefa
	 * @throws SQLException 
	 * @throws IdNotFoundException Id não encontrado para ser removido
	 */
	public void remover(int codigo) throws SQLException, IdNotFoundException {
		PreparedStatement stmt = connection.prepareStatement("DELETE FROM T_BM_TAREFA WHERE CD_TAREFA = ?");

		stmt.setInt(1, codigo);

		int qtd = stmt.executeUpdate();
		
		if (qtd == 0)
			throw new IdNotFoundException("Tarefa não encontrada para ser removida");
	}
	

}
