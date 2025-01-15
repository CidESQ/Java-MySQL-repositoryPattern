package org.cid.main;

import org.cid.model.Employee;
import org.cid.repository.EmployeeRepository;
import org.cid.repository.IRepository;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        System.out.println("Listando todos");
        IRepository<Employee> repository = new EmployeeRepository();
        repository.findAll().forEach(System.out::println);

        System.out.println("Buscando por ID=2");
        System.out.println(repository.getById(2));
    }
}