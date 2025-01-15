package org.cid.repository;

import org.cid.model.Employee;
import org.cid.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements IRepository<Employee> {

//    private Connection getConnection() throws SQLException {
//        return DatabaseConnection.getInstance();
//    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();

        try(
            Connection myConnection = getConnection();
            Statement myStatement = myConnection.createStatement();
            ResultSet myResulset = myStatement.executeQuery("SELECT * FROM employees");
        ) {
            while (myResulset.next()){
                employees.add(createEmployee(myResulset));
            }
        }
        return employees;
    }


    @Override
    public Employee getById(Integer id) throws SQLException {

        Employee employee = null;

        try(
            Connection myConnection = getConnection();
            PreparedStatement myStatement = myConnection.prepareStatement("SELECT * FROM employees WHERE id=?")
        ) {
            myStatement.setInt(1, id);
            try(
                    ResultSet myResulset = myStatement.executeQuery();
                    ){
                if (myResulset.next()){
                    employee = createEmployee(myResulset);
                }
            }
        }
        return employee;
    }

    @Override
    public void save(Employee employee) throws SQLException {

        String query;
        //? Update employee
        if (employee.getId() > 0 && employee.getId() != null){
            query = "UPDATE employees set first_name = ?, pa_surname = ?, ma_surname = ?, email = ?, salary = ?, curp = ?" +
                " WHERE id = ?";
        }else{ //? Insert employee
            query = "INSERT INTO employees (first_name, pa_surname, ma_surname, email, salary, curp) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        }

        try (
            Connection myConnection = getConnection();
            PreparedStatement myPreparedStatement = myConnection.prepareStatement(query)
        ) {
            myPreparedStatement.setString(1, employee.getFirstName());
            myPreparedStatement.setString(2, employee.getPaSurname());
            myPreparedStatement.setString(3, employee.getMaSurname());
            myPreparedStatement.setString(4, employee.getEmail());
            myPreparedStatement.setFloat(5, employee.getSalary());
            myPreparedStatement.setString(6, employee.getCurp());
            if (employee.getId() > 0 && employee.getId() != null)
                    myPreparedStatement.setInt(7, employee.getId());
            myPreparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {

        try(
            Connection myConnection = getConnection();
            PreparedStatement myPreparedStatement = myConnection.prepareStatement("DELETE FROM employees WHERE id=?");
        ){
            myPreparedStatement.setInt(1, id);
            myPreparedStatement.executeUpdate();
        }
    }

    private Employee createEmployee(ResultSet myResulset) throws SQLException {
        Employee employee = new Employee();
        employee.setId(myResulset.getInt("id"));
        employee.setFirstName(myResulset.getString("first_name"));
        employee.setPaSurname(myResulset.getString("pa_surname"));
        employee.setMaSurname(myResulset.getString("ma_surname"));
        employee.setEmail(myResulset.getString("email"));
        employee.setSalary(myResulset.getFloat("salary"));
        employee.setCurp(myResulset.getString("curp"));
        return employee;
    }
}
