package epam.controller;

import epam.dao.IDepartmentDao;
import epam.dao.IEmployeeDao;
import epam.model.Department;
import epam.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *  Class controler for spring MVC
 */
@Controller
@SessionAttributes("tempDepartment") // temp department for insert new employee
public class MainController {

   private static final Logger logger = LoggerFactory.getLogger(MainController.class);

   // for processing department in datebase
   @Autowired
   private IDepartmentDao iDepartmentDao;

    // for processing employeedao in datebase
    @Autowired
   private IEmployeeDao iEmployeeDao;

    // Create new Depatment for tempDepartment
    @ModelAttribute("tempDepartment")
    public Department createTempDepartment(){
        return new Department();
    }

    /**
     * First method on start application
     * @return redirict for maping '/table_Department'
     */
    @RequestMapping(value = "/")
    public ModelAndView main() {
        logger.info("Fist start '/'");
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("index");
         modelAndView.setViewName("redirect:/table_Department");
        return modelAndView;
    }

    /**
     * Metod View table to show all department     *
     * @return full list department - view tableDepartment.jsp
     */
    @RequestMapping("/table_Department")
    public ModelAndView getDepartmentLIst() {
        logger.info("View table to show all department");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tableDepartment");
        List<Department> departmentList = iDepartmentDao.getDepartmentList();
        modelAndView.addObject("modelDepartmentList", departmentList);
        return modelAndView;
    }

    /**
     * Metod View form to add new department
     * @return form add new department - view addDepartment.jsp
     */
    @RequestMapping(value = "/add_Department", method = RequestMethod.GET)
    public ModelAndView addDepartment() {
        logger.info("View form add new department");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("modelDepartment", new Department());
        modelAndView.setViewName("addDepartment");
        return modelAndView;
    }

    /**
     * Metod delete existing department
     * @return redirict for maping '/table_Department'
     */
    @RequestMapping(value = "/delete_Department", method = RequestMethod.POST)
    public ModelAndView deleteDepartment(@ModelAttribute("modelDepartment") Department department) {
        logger.info("Delete department");
        iDepartmentDao.deleteDepartment(department.getId());
        ModelAndView modelAndView = new ModelAndView("redirect:/table_Department");
        return modelAndView;
    }

    /**
     * Metod insert new department to datebase
     * @param department the new department
     * @return redirict for maping '/table_Department'
     */
    @RequestMapping(value = "/insert_Department", method = RequestMethod.POST)
    public ModelAndView insertDepartment(@ModelAttribute("modelDepartment") Department department) {
        logger.info("Insert new department");
        iDepartmentDao.insertDepartment(department);
        ModelAndView modelAndView = new ModelAndView("redirect:/table_Department");
        return modelAndView;
    }

    /**
     * Metod view form edits an existing department
     * @param department the editing department
     * @return form edit existing department - view editDepartment.jsp
     */
    @RequestMapping(value = "/edit_Department", method = RequestMethod.POST)
    public ModelAndView editDepartment(@ModelAttribute("modelDepartment") Department department) {
        logger.info("View form editing new department");
        ModelAndView modelAndView = new ModelAndView();
        int idDepartment = department.getId();
        department = iDepartmentDao.getDepartment(idDepartment);
        modelAndView.addObject("modelDepartment", department);
        modelAndView.setViewName("editDepartment");
        return modelAndView;
    }

    /**
     * Metod update existing department to datebase
     * @param department the editing existing department
     * @return redirict for maping '/table_Department'
     */
    @RequestMapping(value = "/update_Department", method = RequestMethod.POST)
    public ModelAndView updateDepartment(@ModelAttribute("modelDepartment") Department department) {
        logger.info("Update the editing existing department");
        iDepartmentDao.updateDepartment(department);
        ModelAndView modelAndView = new ModelAndView("redirect:/table_Department");
        return modelAndView;
    }

    /**
     * Metod View table to show all employee in selecting department
     * @param department the selecting department (only id depatment)
     * @return list employee - view tableEmployee.jsp
     */
    @RequestMapping(value = "/table_Employee", method = RequestMethod.GET)
    public ModelAndView getEmployeeLIst(@ModelAttribute("modelDepartment") Department department) {
        logger.info("View table to show all employee in selecting department");
        ModelAndView modelAndView = new ModelAndView();
        int idDepartment = department.getId();
//         full depatment
        department = iDepartmentDao.getDepartment(idDepartment);
        modelAndView.addObject("modelDepartment", department);
        List<Employee> employeeList = iEmployeeDao.getEmployeeList(idDepartment);
        modelAndView.addObject("modelEmployeeList", employeeList);
        modelAndView.setViewName("tableEmployee");
        return modelAndView;
    }

    /**
     * Metod View form to add new employee in selecting department
     * @param department the selecting department (only id depatment)
     * @return form add new addEmployee - view addEmployee.jsp
     */
    @RequestMapping(value = "/add_Employee", method = RequestMethod.GET)
    public ModelAndView addEmployee(@ModelAttribute("modelDepartment") Department department){
        logger.info("View form add new employee in selecting department");
        int idDepartment = department.getId();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tempDepartment", iDepartmentDao.getDepartment(idDepartment));
        modelAndView.addObject("modelEmployee", new Employee());
        modelAndView.setViewName("addEmployee");
        return modelAndView;
    }

    /**
     * Metod insert new employee in selecting department to datebase
     * @param employee the new employee
     * @param tempDepartment depatment get SessionAttributes
     * @return redirict for maping '/table_Employee' + param 'id' - selecting department
     */
    @RequestMapping(value = "/insert_Employee", method = RequestMethod.POST)
    public ModelAndView insertEmployee(@ModelAttribute("modelEmployee") Employee employee,
                                       @ModelAttribute("tempDepartment") Department tempDepartment) {
        logger.info("Insert new employee in selecting department");
        employee.setDepartment(tempDepartment);
        iEmployeeDao.insertEmployee(employee);
        int idDepartment = employee.getDepartment().getId();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/table_Employee?id=" + idDepartment);
        return modelAndView;
    }

    /**
     * Metod delete an existing employee
     * @param employee deleting employee
     * @return redirict for maping '/table_Employee' + param 'id' - selecting department
     */
    @RequestMapping(value = "/delete_Employee", method = RequestMethod.POST)
    public ModelAndView deleteEmployee(@ModelAttribute("modelEmployee") Employee employee) {
        logger.info("Delete an existing employee in selecting department");
        int idEmployee = employee.getId();
        employee = iEmployeeDao.getEmployee(idEmployee);
        iEmployeeDao.deleteEmployee(idEmployee);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/table_Employee?id=" + employee.getDepartment().getId());
        return modelAndView;
    }

    /**
     * Metod view form edits an existing employee
     * @param employee the editing employee
     * @return form edit existing employee - view editEmployee.jsp
     */
    @RequestMapping(value = "/edit_Employee", method = RequestMethod.POST)
    public ModelAndView editEmployee(@ModelAttribute("modelEmployee") Employee employee) {
        logger.info("View form edit an existing employee in selecting department");
        ModelAndView modelAndView = new ModelAndView();
        int idEmployee = employee.getId();
        employee = iEmployeeDao.getEmployee(idEmployee);
        modelAndView.addObject("tempDepartment", employee.getDepartment());
        modelAndView.addObject("modelEmployee", employee);
        modelAndView.setViewName("editEmployee");
        return modelAndView;
    }

    /**
     * Metod Update the editing existing employee to datebase
     * @param employee the editing employee
     * @return redirict for maping '/table_Employee' + param 'id' - selecting department
     */
    @RequestMapping(value = "/update_Employee", method = RequestMethod.POST)
    public ModelAndView updateEmployee(@ModelAttribute("modelEmployee") Employee employee){
        logger.info("Update the editing existing employee in selecting department");
        iEmployeeDao.updateEmployee(employee);
        employee = iEmployeeDao.getEmployee(employee.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/table_Employee?id=" + employee.getDepartment().getId());
        return modelAndView;
    }

    /**
     * Metod seach employee between the two dates including their
     * @param firstBirthDate - the start date
     * @param lastBirthDate - the end Date
     * @return form add new addEmployee - view addEmployee.jsp
     */
    @RequestMapping(value = "/find_Employee", method = RequestMethod.GET)
    public ModelAndView findEmployee(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstBirthDate,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") Date lastBirthDate) {
        logger.info("View table to show all employee in selecting department");
        String searshRange;
        DateFormat myDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (lastBirthDate == null) {
            lastBirthDate = firstBirthDate;
            searshRange = "for date: '" + myDateFormat.format(firstBirthDate) + "'";
        }
        else {
            searshRange = "for range date: '" + myDateFormat.format(firstBirthDate) + "' - '"
                                              + myDateFormat.format(lastBirthDate) + "'";
        }
        ModelAndView modelAndView = new ModelAndView();
        List<Employee> employeeList = iEmployeeDao.findEmployee(firstBirthDate, lastBirthDate);
        modelAndView.addObject("modelEmployeeList", employeeList);
        modelAndView.addObject("searshRange", searshRange);
        modelAndView.setViewName("findEmployee");
        return modelAndView;
    }
}
