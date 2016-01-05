package epam.controller;


import epam.dao.IDepartmentDao;
import epam.dao.IEmployeeDao;
import epam.model.Department;
import epam.model.Employee;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

   @Autowired
   private IDepartmentDao iDepartmentDao;
   @Autowired
   private IEmployeeDao iEmployeeDao;

    /*First method on start application*/
    /*Попадаем сюда на старте приложения (см. параметры аннтоции и настройки пути после деплоя) */
    @RequestMapping(value = "/")
    public ModelAndView main() {
        return new ModelAndView("redirect:/table_Department");
    }

    @RequestMapping("/table_Department")
    public ModelAndView getDepartmentLIst() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tableDepartment");
        List<Department> departmentList = iDepartmentDao.getDepartmentList();
        modelAndView.addObject("modelDepartmentList", departmentList);
        return modelAndView;
    }

    @RequestMapping(value = "/add_Department", method = RequestMethod.GET)
    public ModelAndView addDepartment() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("modelDepartment", new Department());
        modelAndView.setViewName("addDepartment");
        return modelAndView;
    }

    @RequestMapping(value = "/delete_Department", method = RequestMethod.POST)
    public ModelAndView deleteDepartment(@ModelAttribute("modelDepartment") Department department) {
        iDepartmentDao.deleteDepartment(department.getId());
        return new ModelAndView("redirect:/table_Department");
    }

    @RequestMapping(value = "/insert_Department", method = RequestMethod.POST)
    public ModelAndView insertDepartment(@ModelAttribute("modelDepartment") Department department) {
        iDepartmentDao.insertDepartment(department);
        return new ModelAndView("redirect:/table_Department");
    }

    @RequestMapping(value = "/edit_Department", method = RequestMethod.POST)
    public ModelAndView editDepartment(@ModelAttribute("modelDepartment") Department department) {
        ModelAndView modelAndView = new ModelAndView();
        int idDepartment = department.getId();
        department = iDepartmentDao.getDepartment(idDepartment);
        modelAndView.addObject("modelDepartment", department);
        modelAndView.setViewName("editDepartment");
        return modelAndView;
    }

   @RequestMapping(value = "/update_Department", method = RequestMethod.POST)
    public ModelAndView updateDepartment(@ModelAttribute("modelDepartment") Department department) {
        iDepartmentDao.updateDepartment(department);
        return new ModelAndView("redirect:/table_Department");
    }

    @RequestMapping(value = "/table_Employee", method = RequestMethod.GET)
    public ModelAndView getEmployeeLIst(@ModelAttribute("modelDepartment") Department department) {
        ModelAndView modelAndView = new ModelAndView();
        int idDepartment = department.getId();
        department = iDepartmentDao.getDepartment(idDepartment);
        modelAndView.addObject("modelDepartment", department);
        List<Employee> employeeList = iEmployeeDao.getEmployeeList(idDepartment);
        modelAndView.addObject("modelEmployeeList", employeeList);
        modelAndView.setViewName("tableEmployee");
        return modelAndView;
    }

    @RequestMapping(value = "/add_Employee", method = RequestMethod.GET)
    public ModelAndView addEmployee(@ModelAttribute("modelEmployee") Employee employee) {
        ModelAndView modelAndView = new ModelAndView();
        int idDepartment = employee.getDepartmentId();
        Department department = iDepartmentDao.getDepartment(idDepartment);
        modelAndView.addObject("modelDepartment", department);
        modelAndView.addObject("modelEmployee", new Employee());
        modelAndView.setViewName("addEmployee");
        return modelAndView;
    }

    @RequestMapping(value = "/insert_Employee", method = RequestMethod.POST)
    public ModelAndView insertEmployee(@ModelAttribute("modelEmployee") Employee employee) {
        iEmployeeDao.insertEmployee(employee);
        int idDepartment = employee.getDepartmentId();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/table_Employee?id=" + idDepartment);
        return modelAndView;
    }

    @RequestMapping(value = "/delete_Employee", method = RequestMethod.POST)
    public ModelAndView deleteEmployee(@ModelAttribute("modelEmployee") Employee employee) {
        int idEmployee = employee.getId();
        employee = iEmployeeDao.getEmployee(idEmployee);
        iEmployeeDao.deleteEmployee(idEmployee);
        return new ModelAndView("redirect:/table_Employee?id=" + employee.getDepartmentId());
    }

    @RequestMapping(value = "/edit_Employee", method = RequestMethod.POST)
    public ModelAndView editEmployee(@ModelAttribute("modelEmployee") Employee employee) {
        ModelAndView modelAndView = new ModelAndView();
        int idEmployee = employee.getId();
        employee = iEmployeeDao.getEmployee(idEmployee);
        int idDepartment = employee.getDepartmentId();
        Department department = iDepartmentDao.getDepartment(idDepartment);
        modelAndView.addObject("modelDepartment", department);
        modelAndView.addObject("modelEmployee", employee);
        modelAndView.setViewName("editEmployee");
        return modelAndView;
    }

    @RequestMapping(value = "/update_Employee", method = RequestMethod.POST)
    public ModelAndView updateEmployee(@ModelAttribute("modelEmployee") Employee employee) {
        iEmployeeDao.updateEmployee(employee);
        return new ModelAndView("redirect:/table_Employee?id=" + employee.getDepartmentId());
    }

    @RequestMapping(value = "/find_Employee", method = RequestMethod.GET)
    public ModelAndView findEmployee(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstBirthDate,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") Date lastBirthDate) {
        String searshRange;
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (lastBirthDate == null) {
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
