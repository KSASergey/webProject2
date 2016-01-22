package epam.dao;

import epam.model.Department;

import javax.sql.DataSource;
import java.util.List;

public interface IDepartmentDao {

//    public void setDataSource(DataSource dataSource);

    public void insertDepartment(Department department);

    public Department getDepartment(Integer id);

    public List<Department> getDepartmentList();

    public void deleteDepartment(Integer id);

    public void updateDepartment(Department department);

}