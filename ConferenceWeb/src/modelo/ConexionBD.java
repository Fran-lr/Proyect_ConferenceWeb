package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	
	private Connection conn;
	private String nombreBd = "crmbd";
	private String usuario = "root";
	private String password = "1111";
	private String url = "jdbc:mysql://localhost:3306/" + nombreBd+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	public ConexionBD() {

		try {
			// Cargamos el driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		}  catch (ClassNotFoundException e) {
			System.out.println("Ocurre una ClassNotFoundException dentro del driver: "+e.getMessage());
		}
	}

	public void cerrarConexion() {
		try {// Cerrar la conexion
			conn.close();
		} catch (SQLException e) {
			System.out.println("Ocurre una SQLException al cerrar la Conexion: "+e.getMessage());
		}
	}

	public Connection conseguirConexion() {
		try {// Conectamos a la base de datos
			conn = DriverManager.getConnection(url, usuario, password);
			
			if (conn != null) {
				System.out.println("Conexion Exitosa a la BS : " + nombreBd);
			} else {
				System.out.println("No se conecto a la BD");
			}
			
		} catch (SQLException e) {
			System.out.println("Ocurre una SQLException al abrir la Conexion: "+e.getMessage());
		}
		return conn;
	}
}
