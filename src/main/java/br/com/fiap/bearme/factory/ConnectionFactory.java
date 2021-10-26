package br.com.fiap.bearme.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe respons�vel por obter as conex�es com o banco de dados
 * 
 * @author Bear Me
 * @version 1.0
 */
public class ConnectionFactory {

	/**
	 * Obtem uma conex�o com o banco de dados
	 * 
	 * @return Connection conex�o com o banco
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		System.out.println("Conex�o gerada com sucesso!");

		 Connection connection = DriverManager.getConnection(
				"jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL", 
				"RM86692", 
				"171189"
				);
		 
		 return connection;
	}

}
