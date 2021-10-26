package br.com.fiap.bearme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.fiap.bearme.bean.Usuario;
import br.com.fiap.bearme.exception.EmailNotFoundException;
import br.com.fiap.bearme.exception.EmailRegisteredException;
import br.com.fiap.bearme.exception.IdNotFoundException;

/**
 * Classe que realiza as operações básicas com o Usuario no banco de dados
 * @author Bear Me
 */
public class UsuarioDao {
	
	private Connection connection;

	/**
	 * Construtor que recebe a conexão como injeção de dependência
	 * @param conexao
	 */
	public UsuarioDao(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Cadastra um novo usuario no banco de dados
	 * @param Usuario com os valores que serão cadastrados
	 * @throws SQLException
	 * @throws  
	 */
	public void cadastrar(Usuario usuario) throws SQLException, EmailRegisteredException {
		PreparedStatement stmt = connection.prepareStatement(
				"INSERT INTO T_BM_USUARIO "
				+ "(CD_USUARIO, DS_EMAIL, NM_USUARIO, DT_NASCIMENTO, DS_SENHA) "
				+ "VALUES "
				+ "(SQ_BM_USUARIO.nextval, ?, ?, ?, ?)");
		
		PreparedStatement stmtEmail = connection.prepareStatement("SELECT * FROM T_BM_USUARIO WHERE DS_EMAIL = ?");
		stmtEmail.setString(1, usuario.getEmail());
		ResultSet result = stmtEmail.executeQuery();
		
		if(result.next()) {
			throw new EmailRegisteredException("Usuário já cadastrado!");
		}
		
		stmt.setString(1, usuario.getEmail());
		stmt.setString(2, usuario.getNickname());
		Date date = java.sql.Date.valueOf(usuario.getDataNascimento().toString());
		stmt.setDate(3, (java.sql.Date) date);
		stmt.setString(4, usuario.getSenha());
		
		stmt.executeUpdate();
	}
	
	/**
	 * Atualiza um usuario no banco de dados 
	 * @param Usuario usuario = com os valores que serão atualizados
	 * @throws SQLException
	 * @throws IdNotFoundException 
	 */
	public void atualizar(Usuario usuario) throws SQLException, IdNotFoundException {
		
		PreparedStatement stmt = connection.prepareStatement(
				"UPDATE T_BM_USUARIO "
				+ "SET"
				+ " DS_EMAIL = ?, "
				+ " NM_USUARIO = ?,"
				+ " DT_NASCIMENTO = ?, "
				+ " DS_SENHA = ? "
				+ "WHERE DS_EMAIL = ?");
		
		stmt.setString(1, usuario.getEmail());
		stmt.setString(2, usuario.getNickname());
		Date date = java.sql.Date.valueOf(usuario.getDataNascimento().toString());
		stmt.setDate(3, (java.sql.Date) date);
		stmt.setString(4, usuario.getSenha());
		stmt.setString(5, usuario.getEmail());
		
		int qtd = stmt.executeUpdate();
		
		if (qtd == 0)
			throw new IdNotFoundException("Usuario não encontrado");
	}
	
	/**
	 * Pesquisa um usuario pela PK
	 * @param codigo PK do usuario
	 * @return Usuario usuario com os valores encontrados
	 * @throws SQLException
	 * @throws IdNotFoundException Não foi encontrado nenhum usuario com a chave informada
	 */
	public Usuario pesquisar(int codigo) throws SQLException, IdNotFoundException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM T_BM_USUARIO WHERE CD_USUARIO = ?");
		
		stmt.setInt(1, codigo);
		
		ResultSet result = stmt.executeQuery();
		
		if (!result.next()) {
			throw new IdNotFoundException("Usuario não encontrado");
		}
		
		int cd = result.getInt("CD_USUARIO");
		String email = result.getString("DS_EMAIL");
		String nickname = result.getString("NM_USUARIO");
		LocalDate dataNascimento = result.getDate("DT_NASCIMENTO").toLocalDate();
		String senha = result.getString("DS_SENHA");
		
		Usuario usuario = new Usuario(cd, email, nickname, senha, dataNascimento);
		
		return usuario;
	}
	
	public Usuario logar(Usuario usuario) throws Exception {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM T_BM_USUARIO WHERE DS_EMAIL = ?");
		
		stmt.setString(1, usuario.getEmail());
		
		ResultSet result = stmt.executeQuery();
		
		if (!result.next()) {
			throw new EmailNotFoundException("Usuario não encontrado");
		}
		
		if(!result.getString("DS_SENHA").contains(usuario.getSenha())) {
			throw new Exception("Senha incorreta.");
		}
		
		int cd = result.getInt("CD_USUARIO");
		String email = result.getString("DS_EMAIL");
		String nickname = result.getString("NM_USUARIO");
		LocalDate dataNascimento = result.getDate("DT_NASCIMENTO").toLocalDate();
		String senha = result.getString("DS_SENHA");
		
		usuario = new Usuario(cd, email, nickname, senha, dataNascimento);
		
		return usuario;
	}
	
	/**
	 * Retorna todos os usuarios do banco de dados
	 * @return List<Usuario> Lista de usuarios
	 * @throws SQLException 
	 */
	public List<Usuario> listar() throws SQLException {
		
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM T_BM_USUARIO");	
		
		ResultSet result = stmt.executeQuery();
		
		List<Usuario> usuarios = new ArrayList<>();
		
		while (result.next()) {
			int codigo = result.getInt("CD_USUARIO");
			String email = result.getString("DS_EMAIL");
			String nickname = result.getString("NM_USUARIO");
			LocalDate dataNascimento = result.getDate("DT_NASCIMENTO").toLocalDate();
			String senha = result.getString("DS_SENHA");
			
			Usuario usuario = new Usuario(codigo, nickname, email, senha, dataNascimento);
			usuarios.add(usuario);
		}
		
		return usuarios;
	}
	
	/**
	 * Remove um usuario do banco de dados 
	 * @param codigo PK do usuario
	 * @throws SQLException 
	 * @throws IdNotFoundException Id não encontrado para ser removido
	 */
	public void remover(int codigo) throws SQLException, IdNotFoundException {
		PreparedStatement stmt = connection.prepareStatement("DELETE FROM T_BM_USUARIO WHERE CD_USUARIO = ?");

		stmt.setInt(1, codigo);

		int qtd = stmt.executeUpdate();
		
		if (qtd == 0)
			throw new IdNotFoundException("Usuario não encontrado para ser removido");
	}
	
	public void remover(String email) throws SQLException, EmailNotFoundException {
		PreparedStatement stmt = connection.prepareStatement("DELETE FROM T_BM_USUARIO WHERE DS_EMAIL = ?");

		stmt.setString(1, email);

		int qtd = stmt.executeUpdate();
		
		if (qtd == 0)
			throw new EmailNotFoundException("Usuario não encontrado para ser removido");
	}
	

}
