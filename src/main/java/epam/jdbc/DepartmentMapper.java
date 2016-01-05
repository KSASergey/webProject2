package epam.jdbc;

import epam.model.Department;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper implements RowMapper<Department> {

    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("id"));
        department.setDepartment(rs.getString("department"));
        department.setAverageSalary(rs.getDouble("averageSalary"));
        return department;
    }
}

