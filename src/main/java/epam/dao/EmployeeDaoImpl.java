package epam.dao;

import epam.jdbc.EmployeeMapper;
import epam.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

public class EmployeeDaoImpl implements IEmployeeDao {

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public List<Employee> getEmployeeList(Integer idDepartment) {
        String SqlDepartmenName = "SELECT department FROM department WHERE id = employee.departmentId";
        String SQL = "SELECT *, (" + SqlDepartmenName + ") AS NameDepartment FROM employee WHERE departmentId = ?";
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        List <Employee> listEmployee = jdbcTemplateObject.query(SQL, new EmployeeMapper(), idDepartment);
        return listEmployee;
    }

    public void insertEmployee(Employee employee) {
        String SQL = "INSERT into employee (departmentId, fullName, birthDate, salary) values (?, ?, ?, ?)";
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
//        Object parameters = new Object[] { employee.getDepartmentId(), employee.getFullName(),
//                employee.getBirthDate(), employee.getSalary() };
//        jdbcTemplateObject.update( SQL, parameters);
        jdbcTemplateObject.update( SQL, employee.getDepartmentId(), employee.getFullName(),
                employee.getBirthDate(), employee.getSalary());
        System.out.println("Created Record employee = " + employee);
        return;
    }

    public void deleteEmployee(Integer id){
        String SQL = "DELETE FROM employee WHERE id = ?";
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        jdbcTemplateObject.update( SQL, id );
        System.out.println("Deleted Record with ID = " + id );
        return;
    }

    public Employee getEmployee(Integer id) {
        String SQL = "SELECT * FROM employee WHERE id = ?";
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        List <Employee> listEmployee = jdbcTemplateObject.query(SQL, new EmployeeMapper(), id);
        return listEmployee.get(0);
    }

    public void updateEmployee(Employee employee){
        String SQL = "UPDATE employee SET departmentId = ?, fullName = ?, birthDate = ?, salary = ? WHERE id = ?";
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
//        Object parameters = new Object[] { employee.getDepartmentId(),
//                employee.getFullName(), employee.getBirthDate(), employee.getSalary(), employee.getId() };
        jdbcTemplateObject.update(SQL, employee.getDepartmentId(), employee.getFullName(),
                 employee.getBirthDate(), employee.getSalary(), employee.getId());
        System.out.println("Updated R5ecord with ID = " + employee.getId() );
        return;
    }

    public List<Employee> findEmployee(Date firstBirthDate, Date lastBirthDate){
        if (lastBirthDate == null) {lastBirthDate = firstBirthDate;}
        String SqlDepartmenName = "SELECT department FROM department WHERE id = employee.departmentId";
        String SQLRangeDate  = "SELECT *, (" + SqlDepartmenName + ") AS NameDepartment FROM employee WHERE (birthDate >= ?) AND (birthDate <= ?) ";
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        List <Employee> listEmployee = jdbcTemplateObject.query(SQLRangeDate, new EmployeeMapper(), firstBirthDate, lastBirthDate);
        return listEmployee;
    }
}