package org.cid.util;

// Singleton: Garantiza que solo exista una instancia de conexion en todo el programa se accede desde el metodo ESTATICO

import java.sql.*;

public class DatabaseConnection {

    private static final String url = "jdbc:mysql://localhost:3306/project";
    private static final String user = "cidesgon";
    private static final String pass = "Emmanuel";

    private static Connection myConnection;

    public static Connection getInstance() throws SQLException {
        if (myConnection == null){
            myConnection = DriverManager.getConnection(url, user, pass);
        }
        return myConnection;
    }
}
