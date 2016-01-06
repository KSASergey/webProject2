package epam.dao;

import epam.jdbc.DepartmentMapper;
import epam.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


public class DepartmentDaoImpl implements IDepartmentDao {

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public List<Department> getDepartmentList() {
        //String SQL = "select * from Department";
        String SQL = "SELECT *, (SELECT  COALESCE(AVG(Salary),0) FROM employee WHERE DepartmentID=department.ID) AS AverageSalary FROM department";
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        List<Department> listDepartment = jdbcTemplateObject.query(SQL, new DepartmentMapper());
        //List listDepartment = new ArrayList();
        //listDepartment = jdbcTemplate.query(SQL, new DepartmentMapper());
        return listDepartment;
    }

    public void insertDepartment(Department department) {
        String SQL = "INSERT into department (department) values (?)";
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        jdbcTemplateObject.update(SQL, department.getDepartment());
        System.out.println("Created Record department = " + department);
        return;
    }

    public void deleteDepartment(Integer id) {
        String SQL = "DELETE FROM department WHERE id = ?";
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Deleted Record with ID = " + id);
        return;
    }

    public Department getDepartment(Integer id) {
        String SQL = "SELECT *, 0 AS AverageSalary FROM department WHERE id = ?";
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        List<Department> listDepartment = jdbcTemplateObject.query(SQL, new DepartmentMapper(), id);
        return listDepartment.get(0);
    }

    public void updateDepartment(Department department) {
        String SQL = "UPDATE department SET department = ? WHERE id = ?";
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        jdbcTemplateObject.update(SQL, department.getDepartment(), department.getId());
        System.out.println("Updated Record with ID = " + department.getId());
        return;
    }
}