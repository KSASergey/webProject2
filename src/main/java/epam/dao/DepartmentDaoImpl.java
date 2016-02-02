package epam.dao;

import epam.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Repository for processing department
 */
@Repository("iDepartmentDao")
//@Service("iDepartmentDao")
@Transactional
public class DepartmentDaoImpl implements IDepartmentDao {

    private static final Logger logger =  LoggerFactory.getLogger(IDepartmentDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieves all department
     * @return a list of department
     */
	@Transactional(readOnly = true)
    public List<Department> getDepartmentList() {
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
        logger.debug("Retrieve existing department first where id = {}", id);
        Session session = sessionFactory.getCurrentSession();
        Department department = (Department) session.get(Department.class, id);
        if (department != null) {
            logger.debug("Department - Id:  {},  department:  {}, average salary:  {}",
                    department.getId(), department.getDepartment(), department.getAverageSalary());
        }
        return department;
    }

    /**
     * Edits an existing department
     * @param department the editing department
     */
    @Transactional
    public void updateDepartment(Department department) {
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