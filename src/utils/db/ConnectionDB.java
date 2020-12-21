package utils.db;

import java.sql.*;

/**
 * @author davichois
 */
public class ConnectionDB {

    public static Connection conexion;

    //variable de conexion a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/distribuidora";
    private static final String user = "root";
    private static final String password = "davidprada27";

    //Metodo para abrir conexion
    public Connection openConexion() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, user, password);
            return conexion;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e);
        }

        return conexion;
    }

    //Metodo para cerrar la conexion
    public void cerrarConnection() throws SQLException {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e);
            conexion.close();
        } finally {
            conexion.close();
        }
    }
}
