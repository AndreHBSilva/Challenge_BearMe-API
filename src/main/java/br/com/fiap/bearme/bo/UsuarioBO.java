package br.com.fiap.bearme.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.fiap.bearme.bean.Usuario;
import br.com.fiap.bearme.dao.UsuarioDao;
import br.com.fiap.bearme.exception.DadoInvalidoException;
import br.com.fiap.bearme.exception.EmailNotFoundException;
import br.com.fiap.bearme.exception.EmailRegisteredException;
import br.com.fiap.bearme.exception.IdNotFoundException;

/**
 * Classe que contém as regras de negócio e validações da interface com o Usuario
 * 
 * @author TechCare Team - BearMe
 */
public class UsuarioBO {

	private UsuarioDao usuarioDao;

	/**
	 * Construtor que recebe a conexão
	 * 
	 * @param conexao conexão com o banco de dados
	 */
	public UsuarioBO(Connection conexao) {
		usuarioDao = new UsuarioDao(conexao);
	}

	/**
	 * Invoca o metodo cadastrar do usuarioDao e cadastra um novo usuario no banco de dados
	 * @param Usuario com os valores que serão cadastrados
	 * @throws SQLException
	 * @throws DadoInvalidoException 
	 */
	public void cadastrar(Usuario usuario) throws SQLException, EmailRegisteredException, DadoInvalidoException {
		validar(usuario);
		usuarioDao.cadastrar(usuario);
	}

	/**
	 * Invoca o metodo atualizar do usuarioDao e atualiza um usuario no banco de dados 
	 * @param Usuario usuario = com os valores que serão atualizados
	 * @throws SQLException
	 * @throws IdNotFoundException 
	 * @throws DadoInvalidoException 
	 */
	public void atualizar(Usuario usuario) throws SQLException, IdNotFoundException, DadoInvalidoException {
		validar(usuario);
		usuarioDao.atualizar(usuario);
	}

	/**
	 * Invoca o metodo pesquisar do usuarioDao que pesquisa um usuario pela PK
	 * @param codigo PK do usuario
	 * @return Usuario com os valores encontrados
	 * @throws SQLException
	 * @throws IdNotFoundException: Não foi encontrado nenhum usuario com a chave informada
	 */
	public Usuario pesquisar(int codigo) throws SQLException, IdNotFoundException {
		return usuarioDao.pesquisar(codigo);
	}
	
	public Usuario logar(Usuario usuario) throws Exception {
		return usuarioDao.logar(usuario);
	}

	/**
	 * Invoca o metodo listar do usuarioDao e retorna todos os usuarios do banco de dados
	 * @return List<Usuario> Lista de usuarios
	 * @throws SQLException 
	 */
	public List<Usuario> listar() throws SQLException {
		return usuarioDao.listar();
	}

	/**
	 * Invoca o metodo remover do usuarioDao e remove um usuario do banco de dados 
	 * @param codigo PK do usuario
	 * @throws SQLException 
	 * @throws IdNotFoundException: Id não encontrado para ser removido
	 */
	public void remover(int codigo) throws SQLException, IdNotFoundException {
		usuarioDao.remover(codigo);
	}
	
	/**
	 * Invoca o metodo remover do usuariodao e remove um usuario do banco de dados, utilizando o nickname.
	 * @param email
	 * @throws SQLException
	 * @throws EmailNotFoundException
	 */
	public void remover(String email) throws SQLException, EmailNotFoundException {
		usuarioDao.remover(email);
	}
	
	// validacoes de acordo com nosso Bando de dados
	private void validar(Usuario usuario) throws DadoInvalidoException {
		if (usuario.getNickname() == null || usuario.getNickname().length() > 90)
			throw new DadoInvalidoException("Nickname é obrigatório e deve conter menos de 90 caracteres");

		if (usuario.getEmail() == null || usuario.getEmail().length() > 90)
			throw new DadoInvalidoException("E-mail é obrigatório e deve conter menos de 90 caracteres");
		
		if (usuario.getDataNascimento() == null)
			throw new DadoInvalidoException("Data de nascimento é obrigatória");
		
		// futuramente iremos alterar este tamanho de senha, de momento deixamos somente para bater com o banco de dados
		if (usuario.getSenha() == null || (usuario.getSenha().length() > 255 && usuario.getSenha().length() < 1))
			throw new DadoInvalidoException("A senha é obrigatória e deve contar de 1 a 255 elementos");
	}

}
