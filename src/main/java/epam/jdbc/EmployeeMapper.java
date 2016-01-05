package epam.jdbc;

import epam.model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {

    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setDepartmentId(rs.getInt("departmentId"));
        employee.setNameDepartment(rs.getString("nameDepartment"));
        employee.setFullName(rs.getString("fullName"));
        employee.setBirthDate(rs.getDate("birthDate"));
        employee.setSalary(rs.getInt("salary"));
        return employee;
    }
}

