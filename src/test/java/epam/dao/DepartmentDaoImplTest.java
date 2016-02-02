package epam.dao;

import epam.model.Department;
import epam.model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;
import java.util.List;


@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=true, transactionManager="transactionManager")
public class DepartmentDaoImplTest {

    @Autowired
    private IDepartmentDao iDepartmentDao;

    @Autowired
    private IEmployeeDao iEmployeeDao;

    @Test
    @Transactional
//    @Rollback(true)
    public void testGetDepartmentList() throws Exception {
        List<Department> departments = iDepartmentDao.getDepartmentList();
        Assert.assertNotNull(departments);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testInsertDepartment() throws Exception {
        Department department = new Department();
        department.setDepartment("New test");

        List<Department> departments1 = iDepartmentDao.getDepartmentList();
        int sizeList = departments1.size();
        iDepartmentDao.insertDepartment(department);

        List<Department> departments2 = iDepartmentDao.getDepartmentList();
        Assert.assertTrue(departments2.size() > sizeList);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteDepartment() throws Exception {
        Department department1 = iDepartmentDao.getDepartment(1);
        Assert.assertNotNull(department1);

        List<Employee> employees1 = iEmployeeDao.getEmployeeList(1);
        Assert.assertTrue(employees1.size() > 0);

        iDepartmentDao.deleteDepartment(1);
        Department department2 = iDepartmentDao.getDepartment(1);
        Assert.assertNull(department2);

        List<Employee> employees2 = iEmployeeDao.getEmployeeList(1);
        Assert.assertTrue(employees2.size() == 0);
    }

    @Test
    @Transactional
//    @Rollback(true)
    public void testGetDepartment() throws Exception {
        Department department1 = iDepartmentDao.getDepartment(2);
        Assert.assertEquals(department1.getAverageSalary(), 0,0);

        Department department2 = iDepartmentDao.getDepartment(1);
        Assert.assertEquals(department2.getAverageSalary(), 1996,0);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateDepartment() throws Exception {
        Department department1 = iDepartmentDao.getDepartment(2);
        String nameDepartment = department1.getDepartment();
        department1.setDepartment("New name");

        iDepartmentDao.updateDepartment(department1);
        Department department2 = iDepartmentDao.getDepartment(2);
        Assert.assertTrue(department2.getDepartment() != nameDepartment);
        Assert.assertEquals("New name", department2.getDepartment());
    }
}