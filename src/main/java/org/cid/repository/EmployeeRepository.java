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

    private Connection myConnection;

    public EmployeeRepository(Connection myConnection) {
        this.myConnection = myConnection;
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();

        try(
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

        try(PreparedStatement myStatement = myConnection.prepareStatement("SELECT * FROM employees WHERE id=?")) {
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

        String sqlInsert = "INSERT INTO employees (first_name, pa_surname, ma_surname, email, salary) " +
                "VALUES (?, ?, ?, ?, ?)";

        String sqlUpdate = "UPDATE employees set first_name = ?, pa_surname = ?, ma_surname = ?, email = ?, salary = ?" +
                " WHERE id = ?";

        // Actualizacion
        if (employee.getId() > 0 && employee.getId() != null){
            try(
                    PreparedStatement myPreparedStatement = myConnection.prepareStatement(sqlUpdate);
            ){
                myPreparedStatement.setString(1, employee.getFirstName());
                myPreparedStatement.setString(2, employee.getPaSurname());
                myPreparedStatement.setString(3, employee.getMaSurname());
                myPreparedStatement.setString(4, employee.getEmail());
                myPreparedStatement.setFloat(5, employee.getSalary());
                myPreparedStatement.setInt(6, employee.getId());
                myPreparedStatement.executeUpdate();
            }
        }else {
            try(
                    PreparedStatement myPreparedStatement = myConnection.prepareStatement(sqlInsert);
            ){
                myPreparedStatement.setString(1, employee.getFirstName());
                myPreparedStatement.setString(2, employee.getPaSurname());
                myPreparedStatement.setString(3, employee.getMaSurname());
                myPreparedStatement.setString(4, employee.getEmail());
                myPreparedStatement.setFloat(5, employee.getSalary());
                myPreparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {

        try(
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
        return employee;
    }
}
