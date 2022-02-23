package br.com.fiap.bearme.resource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.fiap.bearme.bean.RespostaAPI;
import br.com.fiap.bearme.bean.Usuario;
import br.com.fiap.bearme.bo.UsuarioBO;
import br.com.fiap.bearme.exception.DadoInvalidoException;
import br.com.fiap.bearme.exception.EmailNotFoundException;
import br.com.fiap.bearme.exception.EmailRegisteredException;
import br.com.fiap.bearme.exception.IdNotFoundException;
import br.com.fiap.bearme.factory.ConnectionFactory;

@Path("/usuario")
public class UsuarioResource {
	
	Connection connection = null;
	private UsuarioBO getUsuarioBO() throws ClassNotFoundException, SQLException {
		connection = ConnectionFactory.getConnection();
		UsuarioBO usuarioBO = new UsuarioBO(connection);
		return usuarioBO;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(UsuarioTO usuarioTO, @Context UriInfo uriInfo) {
		@SuppressWarnings("unused")
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		Usuario usuario = new Usuario(usuarioTO.getNickname(), usuarioTO.getEmail(), usuarioTO.getSenha(), LocalDate.parse(usuarioTO.getDataNascimento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		RespostaAPI resposta = new RespostaAPI();
		try {
			getUsuarioBO().logar(usuario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resposta.setMensagem(e.getMessage());
			return Response.status(Response.Status.UNAUTHORIZED).entity(resposta).build();
		} finally {
			try {
				if (connection != null) connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		resposta.setMensagem("Login efetuado com sucesso!");
		return Response.status(Response.Status.OK).entity(resposta).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrar(UsuarioTO usuarioTO, @Context UriInfo uriInfo) {
		@SuppressWarnings("unused")
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		Usuario usuario = new Usuario(usuarioTO.getNickname(), usuarioTO.getEmail(), usuarioTO.getSenha(), LocalDate.parse(usuarioTO.getDataNascimento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		RespostaAPI resposta = new RespostaAPI();
		try {
			getUsuarioBO().cadastrar(usuario);
		} catch (ClassNotFoundException | SQLException | EmailRegisteredException | DadoInvalidoException e) {
			// TODO Auto-generated catch block
			resposta.setMensagem(e.getMessage());
			return Response.status(Response.Status.NOT_MODIFIED).entity(resposta).build();
		} finally {
			try {
				if (connection != null) connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resposta.setMensagem(e.getMessage());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resposta).build();
			}
		}
		resposta.setMensagem("Usuário criado com sucesso");
		return Response.status(Response.Status.CREATED).entity(resposta).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editar(UsuarioTO usuarioTO, @Context UriInfo uriInfo) {
		Usuario usuario = new Usuario(usuarioTO.getNickname(), usuarioTO.getEmail(), usuarioTO.getSenha(), LocalDate.parse(usuarioTO.getDataNascimento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		RespostaAPI resposta = new RespostaAPI();
		try {
			getUsuarioBO().atualizar(usuario);
		} catch (ClassNotFoundException | SQLException | IdNotFoundException | DadoInvalidoException e) {
			// TODO Auto-generated catch block
			resposta.setMensagem(e.getMessage());
			return Response.status(Response.Status.NOT_MODIFIED).entity(resposta).build();
		} finally {
			try {
				if (connection != null ) connection.close();
			} catch (SQLException e) {
				resposta.setMensagem(e.getMessage());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resposta).build();
			}
		}
		resposta.setMensagem("Usuário editado!");
		return Response.status(Response.Status.OK).entity(resposta).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletar(UsuarioTO usuarioTO, @Context UriInfo uriInfo) {
		Usuario usuario = new Usuario(usuarioTO.getNickname(), usuarioTO.getEmail(), usuarioTO.getSenha(), LocalDate.parse(usuarioTO.getDataNascimento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		RespostaAPI resposta = new RespostaAPI();
		try {
			getUsuarioBO().remover(usuario.getEmail());
		} catch (ClassNotFoundException | SQLException | EmailNotFoundException e) {
			// TODO Auto-generated catch block
			resposta.setMensagem(e.getMessage());
			return Response.status(Response.Status.NOT_MODIFIED).entity(resposta).build();
		} finally {
			try {
				if (connection != null) connection.close();
			} catch (SQLException e) {
				resposta.setMensagem(e.getMessage());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resposta).build();
			}
		}
		resposta.setMensagem("Usuário deletado");
		return Response.status(Response.Status.OK).entity(resposta).build();
	}
	

}
