package br.com.fiap.bearme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.bearme.bean.Conquista;
import br.com.fiap.bearme.bean.TarefaRealizada;
import br.com.fiap.bearme.exception.IdNotFoundException;

/**
 * Classe que realiza as operações básicas com a Conquista no banco de dados
 * @author Bear Me
 */
public class ConquistaDao {
	
	private Connection connection;

	/**
	 * Construtor que recebe a conexão como injeção de dependência
	 * @param conexao
	 */
	public ConquistaDao(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Cadastra uma nova conquista no banco de dados
	 * @param Conquista conquista com os valores que serão cadastrados
	 * @throws SQLException
	 */
	public void cadastrar(Conquista conquista) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"INSERT INTO T_BM_CONQUISTA_USUARIO "
				+ "(CD_CONQUISTA, CD_TAREFA_REALIZADA, DS_CONQUISTA, NR_PONTOS) "
				+ "VALUES "
				+ "(SQ_BM_CONQUISTA_USUARIO.nextval, ?, ?, ?)");
		
		stmt.setInt(1, conquista.getTarefaRealizada().getCodigo());
		stmt.setString(2, conquista.getDescricao());
		stmt.setInt(3, conquista.getPontos());
		
		stmt.executeUpdate();
	}
	
	/**
	 * Atualiza uma conquista no banco de dados 
	 * @param Conquista conquista = com os valores que serão atualizados
	 * @throws SQLException
	 * @throws IdNotFoundException 
	 */
	public void atualizar(Conquista conquista) throws SQLException, IdNotFoundException {
		
		PreparedStatement stmt = connection.prepareStatement(
				"UPDATE T_BM_CONQUISTA_USUARIO "
				+ "SET"
				+ " DS_CONQUISTA = ?, "
				+ " NR_PONTOS = ? "
				+ "WHERE CD_CONQUISTA = ?");
		
		stmt.setString(1, conquista.getDescricao());
		stmt.setInt(2, conquista.getPontos());
		stmt.setInt(3, conquista.getCodigo());
		
		int qtd = stmt.executeUpdate();
		
		if (qtd == 0)
			throw new IdNotFoundException("Conquista não encontrada");
	}
	
	/**
	 * Pesquisa uma conquista pela PK
	 * @param codigo PK do usuario
	 * @return Conquista conquista com os valores encontrados
	 * @throws SQLException
	 * @throws IdNotFoundException Não foi encontrado nenhum usuario com a chave informada
	 */
	public Conquista pesquisar(int codigo) throws SQLException, IdNotFoundException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM T_BM_CONQUISTA_USUARIO WHERE CD_CONQUISTA = ?");
		
		stmt.setInt(1, codigo);
		
		ResultSet result = stmt.executeQuery();
		
		if (!result.next()) {
			throw new IdNotFoundException("Conquista não encontrada");
		}
		
		int cd = result.getInt("CD_CONQUISTA");
		
		int tarefaRelizadaCodigo = result.getInt("CD_TAREFA_REALIZADA");
		
		TarefaRealizadaDao tarefaRealizadaDao = new TarefaRealizadaDao(connection);
		TarefaRealizada tarefaRealizada = tarefaRealizadaDao.pesquisar(tarefaRelizadaCodigo);
		
		String descricao = result.getString("DS_CONQUISTA");
		int pontos = result.getInt("NR_PONTOS");
		
		Conquista conquista = new Conquista(cd, tarefaRealizada, descricao, pontos);
		
		return conquista;
	}
	
	/**
	 * Retorna todos as conquistas do banco de dados
	 * @return List<Conquista> Lista de conquistas
	 * @throws SQLException 
	 * @throws IdNotFoundException 
	 */
	public List<Conquista> listar() throws SQLException, IdNotFoundException {
		
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM T_BM_CONQUISTA_USUARIO");	
		
		ResultSet result = stmt.executeQuery();
		
		List<Conquista> conquistas = new ArrayList<>();
		
		while (result.next()) {
			int codigo = result.getInt("CD_CONQUISTA");
			
			int tarefaRealizadaCodigo = result.getInt("CD_TAREFA_REALIZADA");
			
			TarefaRealizadaDao tarefaRealizadaDao = new TarefaRealizadaDao(connection);
			TarefaRealizada tarefaRealizada = tarefaRealizadaDao.pesquisar(tarefaRealizadaCodigo);
			
			String descricao = result.getString("DS_CONQUISTA");
			int pontos = result.getInt("NR_PONTOS");
			
			Conquista conquista = new Conquista(codigo, tarefaRealizada, descricao, pontos);
			conquistas.add(conquista);
		}
		
		return conquistas;
	}
	
	/**
	 * Remove uma conquista do banco de dados 
	 * @param codigo PK da conquista
	 * @throws SQLException 
	 * @throws IdNotFoundException Id não encontrado para ser removido
	 */
	public void remover(int codigo) throws SQLException, IdNotFoundException {
		PreparedStatement stmt = connection.prepareStatement("DELETE FROM T_BM_CONQUISTA_USUARIO WHERE CD_CONQUISTA = ?");

		stmt.setInt(1, codigo);

		int qtd = stmt.executeUpdate();
		
		if (qtd == 0)
			throw new IdNotFoundException("Conquista não encontrada para ser removida");
	}
	
	// criando metodo com Inner Join para evitar queries N + 1 (ainda não finalizado)
	public List<Conquista> listarComTarefasRealizadas() throws SQLException {
		
		List<Conquista> conquistas = new ArrayList<>();
		
		PreparedStatement stmt = connection.prepareStatement(
				"SELECT * FROM T_BM_CONQUISTA_USUARIO C "
				+ "INNER JOIN T_BM_TAREFA_REALIZADA T ON C.CD_CONQUISTA = T.CD_TAREFA_REALIZADA");
		
		ResultSet result = stmt.executeQuery();
		
		while (result.next()) {
			Conquista conquista = new Conquista(result.getInt(1), result.getString(3), result.getInt(4));
			TarefaRealizada tarefaRealizada = new TarefaRealizada(result.getInt(5), result.getInt(9), result.getInt(10));
			
			conquistas.add(conquista);
			tarefaRealizada.add(conquista);
		}
		
		return conquistas;
		
	}
	

}













