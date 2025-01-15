package org.cid.util;

// Singleton: Garantiza que solo exista una instancia de conexion en todo el programa se accede desde el metodo ESTATICO

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class DatabaseConnection {

    private static final String url = "jdbc:mysql://localhost:3306/project";
    private static final String user = "cidesgon";
    private static final String pass = "Emmanuel";

    private static BasicDataSource pool;

    public static BasicDataSource getInstance() throws SQLException {
        if (pool == null){
            pool = new BasicDataSource();
            // log pool
            pool.setUrl(url);
            pool.setUsername(user);
            pool.setPassword(pass);

            //Config pool
            pool.setInitialSize(3);
            pool.setMinIdle(3);
            pool.setMaxIdle(10);
            pool.setMaxTotal(10);

        }
        return pool;
    }

    //Metodo para obtener solo una conexion del pool
    public static Connection getConnection () throws SQLException {
        return getInstance().getConnection();
    }
}
