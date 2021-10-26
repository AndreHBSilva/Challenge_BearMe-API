package br.com.fiap.bearme.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por obter as conexões com o banco de dados
 * 
 * @author Bear Me
 * @version 1.0
 */
public class ConnectionFactory {

	/**
	 * Obtem uma conexão com o banco de dados
	 * 
	 * @return Connection conexão com o banco
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		System.out.println("Conexão gerada com sucesso!");

		 Connection connection = DriverManager.getConnection(
				"jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL", 
				"RM86692", 
				"171189"
				);
		 
		 return connection;
	}

}
