package br.com.fiap.bearme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.fiap.bearme.bean.Tarefa;
import br.com.fiap.bearme.bean.TarefaRealizada;
import br.com.fiap.bearme.bean.Usuario;
import br.com.fiap.bearme.exception.IdNotFoundException;

/**
 * Classe que realiza as operações básicas com a TarefaRealizada no banco de dados
 * @author Bear Me
 */
public class TarefaRealizadaDao {
	
	private Connection connection;

	/**
	 * Construtor que recebe a conexão como injeção de dependência
	 * @param conexao
	 */
	public TarefaRealizadaDao(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Cadastra uma nova tarefa_realizada no banco de dados
	 * @param TarefaRealizada tarefaRealizada com os valores que serão cadastrados
	 * @throws SQLException
	 */
	public void cadastrar(TarefaRealizada tarefaRealizada) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"INSERT INTO T_BM_TAREFA_REALIZADA "
				+ "(CD_TAREFA_REALIZADA, CD_USUARIO, CD_TAREFA, DT_REALIZACAO_TAREFA, HR_REALIZACAO_TAREFA, DS_TAREFA_CONCLUIDA) "
				+ "VALUES "
				+ "(SQ_BM_TAREFA_REALIZADA.nextval, ?, ?, ?, ?, ?)");
		
		stmt.setInt(1, tarefaRealizada.getUsuario().getCodigo());
		stmt.setInt(2, tarefaRealizada.getTarefa().getCodigo());
		Date date = java.sql.Date.valueOf(tarefaRealizada.getDataRealizacao().toString());
		stmt.setDate(3, (java.sql.Date) date);
		stmt.setInt(4, tarefaRealizada.getHoraRealizacao());
		stmt.setInt(5, tarefaRealizada.isConcluida());
		
		stmt.executeUpdate();
	}
	
	/**
	 * Atualiza uma tarefa_realizada no banco de dados 
	 * @param TarefaRealizada tarefaRealizada com os valores que serão atualizados
	 * @throws SQLException
	 * @throws IdNotFoundException 
	 */
	public void atualizar(TarefaRealizada tarefaRealizada) throws SQLException, IdNotFoundException {
		
		PreparedStatement stmt = connection.prepareStatement("UPDATE T_BM_TAREFA_REALIZADA "
				+ "SET "
				+ "DT_REALIZACAO_TAREFA = ?, "
				+ "HR_REALIZACAO_TAREFA = ?, "
				+ "DS_TAREFA_CONCLUIDA = ? "
				+ "WHERE "
				+ "CD_TAREFA_REALIZADA = ?");
		
		Date date = java.sql.Date.valueOf(tarefaRealizada.getDataRealizacao().toString());
		stmt.setDate(1, (java.sql.Date) date);
		stmt.setInt(2, tarefaRealizada.getHoraRealizacao());
		stmt.setInt(3, tarefaRealizada.isConcluida());
		
		stmt.setInt(4, tarefaRealizada.getCodigo());
		
		int qtd = stmt.executeUpdate();
		
		if (qtd == 0)
			throw new IdNotFoundException("Tarefa Realizada não encontrada");
	}
	
	/**
	 * Pesquisa uma tarefa_realizada pela PK
	 * @param codigo PK da tarefa_realizada
	 * @return TarefaRealizada com os valores encontrados
	 * @throws SQLException
	 * @throws IdNotFoundException Não foi encontrada nenhuma tarefa realizada com a chave informada
	 */
	public TarefaRealizada pesquisar(int codigo) throws SQLException, IdNotFoundException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM T_BM_TAREFA_REALIZADA WHERE CD_TAREFA_REALIZADA = ?");
		
		stmt.setInt(1, codigo);
		
		ResultSet result = stmt.executeQuery();
		
		if (!result.next()) {
			throw new IdNotFoundException("Tarefa Realizada não encontrada");
		}
		
		int cd = result.getInt("CD_TAREFA_REALIZADA");
		
		int usuarioCodigo = result.getInt("CD_USUARIO");
		int tarefaCodigo = result.getInt("CD_TAREFA");
		
		UsuarioDao usuarioDao = new UsuarioDao(connection);
		Usuario user = usuarioDao.pesquisar(usuarioCodigo);
		
		TarefaDao tarefaDao = new TarefaDao(connection);
		Tarefa task = tarefaDao.pesquisar(tarefaCodigo);
		
		LocalDate dataNascimento = result.getDate("DT_REALIZACAO_TAREFA").toLocalDate();
		int hora = result.getInt("HR_REALIZACAO_TAREFA");
		int concluida = result.getInt("DS_TAREFA_CONCLUIDA");
		
		return new TarefaRealizada(cd, user, task, dataNascimento, hora, concluida);
	}
	
	/**
	 * Retorna todos as tarefas do banco de dados
	 * @return List<TarefaRealizada> Lista de tarefas realizadas
	 * @throws SQLException 
	 * @throws IdNotFoundException 
	 */
	public List<TarefaRealizada> listar() throws SQLException, IdNotFoundException {
		
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM T_BM_TAREFA_REALIZADA");	
		
		ResultSet result = stmt.executeQuery();
		
		List<TarefaRealizada> tarefasRealizadas = new ArrayList<>();
		
		while (result.next()) {
			int cd = result.getInt("CD_TAREFA_REALIZADA");
			int usuarioCodigo = result.getInt("CD_USUARIO");
			int tarefaCodigo = result.getInt("CD_TAREFA");
			LocalDate dataNascimento = result.getDate("DT_REALIZACAO_TAREFA").toLocalDate();
			int hora = result.getInt("HR_REALIZACAO_TAREFA");
			int concluida = result.getInt("DS_TAREFA_CONCLUIDA");
			
			UsuarioDao usuarioDao = new UsuarioDao(connection);
			Usuario user = usuarioDao.pesquisar(usuarioCodigo);
			
			TarefaDao tarefaDao = new TarefaDao(connection);
			Tarefa task = tarefaDao.pesquisar(tarefaCodigo);
			
			TarefaRealizada tarefaRealizada = new TarefaRealizada(cd, user, task, dataNascimento, hora, concluida);
			tarefasRealizadas.add(tarefaRealizada);
		}
		
		return tarefasRealizadas;
	}
	
	/**
	 * Remove uma tarefa realizada do banco de dados 
	 * @param codigo PK da tarefa
	 * @throws SQLException 
	 * @throws IdNotFoundException Id não encontrado para ser removido
	 */
	public void remover(int codigo) throws SQLException, IdNotFoundException {
		PreparedStatement stmt = connection.prepareStatement("DELETE FROM T_BM_TAREFA_REALIZADA WHERE CD_TAREFA_REALIZADA = ?");

		stmt.setInt(1, codigo);

		int qtd = stmt.executeUpdate();
		
		if (qtd == 0)
			throw new IdNotFoundException("Tarefa Realizada não encontrada para ser removida");
	}
	

}
