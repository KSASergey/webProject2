package epam.dao;

import epam.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for processing department
 */
@Repository("iDepartmentDao")
//@Service("iDepartmentDao")
@Transactional
public class DepartmentDaoImpl implements IDepartmentDao {

    private static final Logger logger =  LoggerFactory.getLogger(IDepartmentDao.class);
//    protected static Logger logger = Logger.getLogger("IDepartmentDao");

    @Autowired
    private SessionFactory sessionFactory;

//    @Autowired
//    private DataSource dataSourceJDBC;
//
//    private JdbcTemplate jdbcTemplateObject;
//
//    public void setDataSource(DataSource dataSourceJDBC) {
//        this.dataSourceJDBC = dataSourceJDBC;
//        this.jdbcTemplateObject = new JdbcTemplate(dataSourceJDBC);
//    }

    /**
     * Retrieves all department
     * @return a list of department
     */
	@Transactional(readOnly = true)
    public List<Department> getDepartmentList() {
//        String SQL = "SELECT *, (SELECT  COALESCE(AVG(Salary),0) FROM employee WHERE ID_Department=department.ID) AS AverageSalary FROM department";
//        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSourceJDBC);
//        List<Department> listDepartment = jdbcTemplateObject.query(SQL, new DepartmentMapper());

        logger.debug("Received request to show all department");
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Department");
        List<Department> listDepartment = query.list();
        for (Department department : listDepartment) {
            logger.debug("Department - Id:  {},  department:  {}, average salary:  {}",
                    department.getId(), department.getDepartment(), department.getAverageSalary());
        }
        return listDepartment;
    }

    /**
     * Adds a new department
     * @param department the a new department
     */
    @Transactional
    public void insertDepartment(Department department) {
//        String SQL = "INSERT into department (department) values (?)";
//        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSourceJDBC);
//        jdbcTemplateObject.update(SQL, department.getDepartment());
//        System.out.println("Created Record department = " + department);

        logger.debug("Add new department");
        logger.debug("Department - Id:  {},  department:  {}, average salary:  {}",
                department.getId(), department.getDepartment(), department.getAverageSalary());
        Session session = sessionFactory.getCurrentSession();
        // Save
        session.save(department);
    }

    /**
     * Deletes an existing department
     * @param id the id of the existing department
    */
    @Transactional
    public void deleteDepartment(Integer id) {
//        String SQL = "DELETE FROM department WHERE id = ?";
//        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSourceJDBC);
//        jdbcTemplateObject.update(SQL, id);
//        System.out.println("Deleted Record with ID = " + id);

        logger.debug("Delete department where id = {}", id);
        Session session = sessionFactory.getCurrentSession();
        // Retrieve existing department first
        Department department = (Department) session.get(Department.class, id);
        // Delete
        session.delete(department);
    }

    /**
     * Retrieves a single department
     * @param id the id of the existing department
     * @return the existing department
     */
    @Transactional(readOnly = true)
    public Department getDepartment(Integer id) {
//        String SQL = "SELECT *, 0 AS AverageSalary FROM department WHERE id = ?";
//        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSourceJDBC);
//        List<Department> listDepartment = jdbcTemplateObject.query(SQL, new DepartmentMapper(), id);
//        return listDepartment.get(0);

        logger.debug("Retrieve existing department first where id = {}", id);
        Session session = sessionFactory.getCurrentSession();
        Department department = (Department) session.get(Department.class, id);
        logger.debug("Department - Id:  {},  department:  {}, average salary:  {}",
                department.getId(), department.getDepartment(), department.getAverageSalary());
        return department;
    }

    /**
     * Edits an existing department
     * @param department the editing department
     */
    @Transactional
    public void updateDepartment(Department department) {
//        String SQL = "UPDATE department SET department = ? WHERE id = ?";
//        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSourceJDBC);
//        jdbcTemplateObject.update(SQL, department.getDepartment(), department.getId());
//        System.out.println("Updated Record with ID = " + department.getId());

        logger.debug("Edits an existing department where id = {}", department.getId());
        logger.debug("Department - Id:  {},  department:  {}, average salary:  {}",
                department.getId(), department.getDepartment(), department.getAverageSalary());
        Session session = sessionFactory.getCurrentSession();
        Department existingDepartment = (Department) session.get(Department.class, department.getId());
        // Assign updated values to this department
        existingDepartment.setDepartment(department.getDepartment());
        session.save(existingDepartment);
    }
}