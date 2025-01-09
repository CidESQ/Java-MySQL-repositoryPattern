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

            IRepository<Employee> repository = new EmployeeRepository();

            System.out.println("---------Listando empleados----------");
            repository.findAll().forEach(System.out::println);
            System.out.println("-------------------------------------");

            repository.delete(8);
            System.out.println("Empleado eliminado");

            System.out.println("---------Listando empleados----------");
            repository.findAll().forEach(System.out::println);
            System.out.println("-------------------------------------");
        }
    }
}