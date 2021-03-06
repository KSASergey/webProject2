package epam.dao;

import epam.model.Employee;
import java.util.Date;
import java.util.List;

public interface IEmployeeDao {

    public void insertEmployee(Employee employee);

    public Employee getEmployee(Integer id);

    public List<Employee> getEmployeeList(Integer idDepartment);

    public void deleteEmployee(Integer id);

    public void updateEmployee(Employee employee);

    public List<Employee> findEmployee(Date firstBirthDate, Date lastBirthDate);

}