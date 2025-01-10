package org.cid.main;

import org.cid.model.Employee;
import org.cid.repository.EmployeeRepository;
import org.cid.repository.IRepository;
import org.cid.util.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        try(Connection connection = DatabaseConnection.getInstance()){
//TODO: Check connexion
            IRepository<Employee> repository = new EmployeeRepository(connection);

            System.out.println("---------Listando empleados----------");
            repository.findAll().forEach(System.out::println);
            System.out.println("-------------------------------------");

//            repository.delete(7);
//            System.out.println("Empleado eliminado");

            repository.save(new Employee( "Cid", "Esquivel", "Gonzalez", "cid@mail.com", 19291f, "adsasd"));

            System.out.println("Empleado creado");
            System.out.println("---------Listando empleados----------");
            repository.findAll().forEach(System.out::println);
            System.out.println("-------------------------------------");
        }
    }
}