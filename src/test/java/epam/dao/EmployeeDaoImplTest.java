package epam.dao;

import epam.model.Department;
import epam.model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=true, transactionManager="transactionManager")
public class EmployeeDaoImplTest {

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    private IDepartmentDao iDepartmentDao;

    @Autowired
    private IEmployeeDao iEmployeeDao;

    @Test
    @Transactional
//    @Rollback(true)
    public void testGetEmployeeList() throws Exception {
        List<Employee> employees1 = iEmployeeDao.getEmployeeList(1);
        Assert.assertTrue(employees1.size() > 0);
        List<Employee> employees2 = iEmployeeDao.getEmployeeList(2);
        Assert.assertTrue(employees2.size() == 0);
    }

    @Test
    @Transactional
//    @DirtiesContext
    @Rollback(true)
    public void testInsertEmployee() throws Exception {
        Employee employee = new Employee();
        Department department = iDepartmentDao.getDepartment(2);
        employee.setDepartment(department);
        employee.setFullName("New Employee");
        java.util.Date birthDate = formatter.parse("10-02-1989");
        employee.setBirthDate(new Date(birthDate.getTime()));
        employee.setSalary(228);

        List<Employee> employees1 = iEmployeeDao.getEmployeeList(2);
        int sizeList = employees1.size();
        iEmployeeDao.insertEmployee(employee);
        List<Employee> employees2 = iEmployeeDao.getEmployeeList(2);
        Assert.assertTrue(employees2.size() > sizeList);

//        iEmployeeDao.deleteEmployee(employee.getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteEmployee() throws Exception {
        Employee employee1 = iEmployeeDao.getEmployee(2);
        Assert.assertNotNull(employee1);
        iEmployeeDao.deleteEmployee(2);
        Employee employee2 = iEmployeeDao.getEmployee(2);
        Assert.assertNull(employee2);
    }

    @Test
    @Transactional
//    @Rollback(true)
    public void testGetEmployee() throws Exception {
        Employee employee = iEmployeeDao.getEmployee(3);
        Assert.assertNotNull(employee);
    }

    @Test
    @Transactional
//    @DirtiesContext
    @Rollback(true)
    public void testUpdateEmployee() throws Exception {
        List<Employee> employees1 = iEmployeeDao.getEmployeeList(2);
        Assert.assertEquals(0, employees1.size());

        Employee employee = iEmployeeDao.getEmployee(4);
        Department department = iDepartmentDao.getDepartment(2);
        employee.setDepartment(department);
        employee.setFullName("Update Employee");
        java.util.Date birthDate = formatter.parse("10-02-1989");
        employee.setBirthDate(new Date(birthDate.getTime()));
        employee.setSalary(100);

        iEmployeeDao.updateEmployee(employee);
        List<Employee> employees2 = iEmployeeDao.getEmployeeList(2);
        Assert.assertTrue(employees2.size() > 0);
        Assert.assertEquals(employees2.get(0).getDepartment().getDepartment(), department.getDepartment());
        Assert.assertEquals(employees2.get(0).getId(), employee.getId());
    }

    @Test
    @Transactional
//    @DirtiesContext
    @Rollback(true)
    public void testFindEmployee() throws Exception {
        java.util.Date firstBirthDate = formatter.parse("10-11-1982");
        java.util.Date lastBirthDate = formatter.parse("10-11-1982");
        List<Employee> employees1 = iEmployeeDao.findEmployee(firstBirthDate, lastBirthDate);
        Assert.assertTrue(employees1.size() == 0);

        Employee employee = new Employee();
        Department department = iDepartmentDao.getDepartment(2);
        employee.setDepartment(department);
        employee.setFullName("Find Employee");
        java.util.Date birthDate = formatter.parse("10-11-1982");
        employee.setBirthDate(new Date(birthDate.getTime()));
        employee.setSalary(352);
        iEmployeeDao.insertEmployee(employee);
        List<Employee> employees2 = iEmployeeDao.findEmployee(firstBirthDate, lastBirthDate);
        Assert.assertTrue(employees2.size() == 1);
        iEmployeeDao.deleteEmployee(employee.getId());

        firstBirthDate = formatter.parse("01-01-1985");
        lastBirthDate = formatter.parse("31-12-2015");
        List<Employee> employees3 = iEmployeeDao.findEmployee(firstBirthDate, lastBirthDate);
        Assert.assertTrue(employees3.size() > 1);
    }
}