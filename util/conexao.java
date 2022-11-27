package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class conexao {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	final private String host = "jdbc:mysql://localhost:3306/bancodedigitais?useTimezone=true&serverTimezone=UTC";
	final private String user = "root";
	final private String passwd = "admin";

	public String readDataBase(String aux) throws Exception {
		String aux1 = "select * from digitais where digital = " + aux;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(host, user, passwd);

			statement = connect.createStatement();
			resultSet = statement.executeQuery(aux1);
			String resultado = writeResultSet(resultSet);
			return resultado;

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}
	
	public void writeDataBase(String nome, int nivel , String digital) throws Exception {
		String aux1 = "insert into digitais (digital, nivel_acesso, nome) values ('"+digital+"','"+nivel+"','"+nome+"')";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(host, user, passwd);

			statement = connect.prepareStatement(aux1);
			statement.execute(aux1);
			
//			String aux = readDataBase(digital);
//			String sucesso = "Sucesso";
//			String erro = "Erro...";
//			
//			if (aux != "Sem informações") {
//				return sucesso;
//			}
//			else return erro;

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}

	private String writeResultSet(ResultSet resultSet) throws SQLException {
		if (resultSet.next() == true) {
			String meuID = resultSet.getString("nivel_acesso");
			return meuID;
		}
		else {
			return ("Sem informações");
		}
	}

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
		}
	}
	
	public static void main(String[] args) throws Exception {
		conexao dao = new conexao();
		dao.writeDataBase("aaa",5,"marco");
	}
}

