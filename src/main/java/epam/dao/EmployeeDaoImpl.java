package epam.dao;

import epam.model.Department;
import epam.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * Repository for processing dpartment
 */
@Repository("iEmployeeDao")
//@Service("iDepartmentDao")
@Transactional
public class EmployeeDaoImpl implements IEmployeeDao {

    private static final Logger logger =  LoggerFactory.getLogger(IEmployeeDao.class);

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
     * Retrieves all employee for the selected department
     * @param idDepartment - the id of the selected department
     * @return a list of employee
     */
    @Transactional(readOnly = true)
    public List<Employee> getEmployeeList(Integer idDepartment) {
//        String SqlDepartmenName = "SELECT department FROM department WHERE id = employee.ID_Department";
//        String SQL = "SELECT *, (" + SqlDepartmenName + ") AS NameDepartment FROM employee WHERE ID_Department = ?";
//        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSourceJDBC);
//        List <Employee> listEmployee = jdbcTemplateObject.query(SQL, new EmployeeMapper(), idDepartment);
//        return listEmployee;

        logger.debug("Received request to show all employee for the selected department id - {}", idDepartment);
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Employee e Left JOIN FETCH e.department department where department.id = :id");
        query.setParameter("id", idDepartment);
        List<Employee> listEmployee = query.list();
        for (Employee employee : listEmployee) {
            logger.debug("Employee - Id:  {}, nameDepartment: {}, id_department:  {}, fullName:  {}, birthDate: {}",
                    employee.getId(), employee.getDepartment().getDepartment(), employee.getDepartment().getId(),
                    employee.getFullName(), employee.getBirthDate());
        }
        return listEmployee;
    }

    /**
     * Adds a new employee
     * @param employee the a new employee
     */
    @Transactional
    public void insertEmployee(Employee employee) {
//        String SQL = "INSERT into employee (ID_Department, fullName, birthDate, salary) values (?, ?, ?, ?)";
//        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSourceJDBC);
//        jdbcTemplateObject.update( SQL, employee.getID_Department(), employee.getFullName(),
//                employee.getBirthDate(), employee.getSalary());
//        System.out.println("Created Record employee = " + employee);

        logger.debug("Add new employee");
        logger.debug("Employee - Id:  {}, nameDepartment: {}, id_department:  {}, fullName:  {}, birthDate: {}",
                employee.getId(), employee.getDepartment().getDepartment(), employee.getDepartment().getId(),
                employee.getFullName(), employee.getBirthDate());
        Session session = sessionFactory.getCurrentSession();
        // Save
        session.save(employee);
    }

    /**
     * Deletes an existing employee
     * @param id the id of the existing employee
     */
    @Transactional
    public void deleteEmployee(Integer id){
//        String SQL = "DELETE FROM employee WHERE id = ?";
//        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSourceJDBC);
//        jdbcTemplateObject.update( SQL, id );
//        System.out.println("Deleted Record with ID = " + id );

        logger.debug("Delete employee where  id - {}", id);
        Session session = sessionFactory.getCurrentSession();
        // Retrieve existing employee first
        Employee employee = (Employee) session.get(Employee.class, id);
        // Delete
        session.delete(employee);
    }

    /**
     * Retrieves a single employee
     * @param id the id of the existing employee
     * @return the existing employee
     */
    @Transactional(readOnly = true)
    public Employee getEmployee(Integer id) {
//        String SQL = "SELECT *, '' AS NameDepartment FROM employee WHERE id = ?";
//        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSourceJDBC);
//        List <Employee> listEmployee = jdbcTemplateObject.query(SQL, new EmployeeMapper(), id);
//        return listEmployee.get(0);

        logger.debug("Retrieve existing employee first id - {}", id);
        Session session = sessionFactory.getCurrentSession();
        Employee employee = (Employee) session.get(Employee.class, id);
        logger.debug("Employee - Id:  {}, nameDepartment: {}, id_department:  {}, fullName:  {}, birthDate: {}",
                employee.getId(), employee.getDepartment().getDepartment(), employee.getDepartment().getId(),
                employee.getFullName(), employee.getBirthDate());
        return employee;
    }

    /**
     * Edits an existing employee
     * @param employee the editing employee
     */
    @Transactional
    public void updateEmployee(Employee employee){
//        String SQL = "UPDATE employee SET ID_Department = ?, fullName = ?, birthDate = ?, salary = ? WHERE id = ?";
//        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSourceJDBC);
//        jdbcTemplateObject.update(SQL, employee.getID_Department(), employee.getFullName(),
//                 employee.getBirthDate(), employee.getSalary(), employee.getId());
//        System.out.println("Updated R5ecord with ID = " + employee.getId() );

        logger.debug("Edits an existing employee id - {}", employee.getId());
        Session session = sessionFactory.getCurrentSession();
        Employee existingEmployee = (Employee) session.get(Employee.class, employee.getId());
        logger.debug("Employee - Id:  {}, nameDepartment: {}, id_department:  {}, fullName:  {}, birthDate: {}",
                employee.getId(), existingEmployee.getDepartment().getDepartment(), existingEmployee.getDepartment().getId(),
                employee.getFullName(), employee.getBirthDate());
        // Assign updated values to this employee
        existingEmployee.setFullName(employee.getFullName());
        existingEmployee.setBirthDate(employee.getBirthDate());
        existingEmployee.setSalary(employee.getSalary());
        session.save(existingEmployee);
    }

    /**
     * Seach employee between the two dates including their
     * @param firstBirthDate - the start date
     * @param lastBirthDate - the end Date
     * @return a list of employee is responsible search criteria
     */
    public List<Employee> findEmployee(Date firstBirthDate, Date lastBirthDate){
//        if (lastBirthDate == null) {lastBirthDate = firstBirthDate;}
//        String SqlDepartmenName = "SELECT department FROM department WHERE id = employee.ID_Department";
//        String SQLRangeDate  = "SELECT *, (" + SqlDepartmenName + ") AS NameDepartment FROM employee WHERE (birthDate >= ?) AND (birthDate <= ?) ";
//        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSourceJDBC);
//        List <Employee> listEmployee = jdbcTemplateObject.query(SQLRangeDate, new EmployeeMapper(), firstBirthDate, lastBirthDate);
//        return listEmployee;

        logger.debug("Received request to show all employee between the two dates: {} - {}", firstBirthDate, lastBirthDate);
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Employee e LEFT JOIN FETCH e.department " +
                "WHERE e.birthDate >=:firstBirthDate AND e.birthDate <=:lastBirthDate ORDER BY e.department");
        query.setParameter("firstBirthDate", firstBirthDate);
        query.setParameter("lastBirthDate", lastBirthDate);
        List<Employee> listEmployee = query.list();
        for (Employee employee : listEmployee) {
            logger.debug("Employee - Id:  {}, nameDepartment: {}, id_department:  {}, fullName:  {}, birthDate: {}",
                    employee.getId(), employee.getDepartment().getDepartment(), employee.getDepartment().getId(),
                    employee.getFullName(), employee.getBirthDate());
        }
        return listEmployee;
    }
}