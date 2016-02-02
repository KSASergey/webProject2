package epam.controller;

import epam.model.Department;
import epam.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.UUID;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:web/WEB-INF/dispatcher-servlet.xml",
                                   "classpath*:testApplicationContext.xml"})
@WebAppConfiguration
public class MainControllerTest {

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    protected MockHttpSession mockSession;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
    }

    @Test
    public void testCreateTempDepartment() throws Exception {
        MainController mainController = new MainController();
        Department department = mainController.createTempDepartment();
        Assert.assertTrue(department.getId() == null);
        Assert.assertTrue(department.getDepartment() == null);
        Assert.assertTrue(department.getAverageSalary() == null);
    }

    @Test
    public void testMain() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().is3xxRedirection());
        result.andExpect(redirectedUrl("/table_Department"));
    }

    @Test
    public void testGetDepartmentLIst() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/table_Department");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().isOk());
        result.andExpect(forwardedUrl("/WEB-INF/views/tableDepartment.jsp"));
        result.andExpect(view().name("tableDepartment"));
        result.andExpect(model().attributeExists("modelDepartmentList"));
//        result.andExpect(model().size(2));
        ModelAndView modelAndView = result.andReturn().getModelAndView();
        List<Department> departmentList = (List<Department>) modelAndView.getModel().get("modelDepartmentList");
        Assert.assertTrue(departmentList.size() == 3);

        mockMvc.perform(get("/")
                .session(mockSession)).andExpect(model().attributeExists("tempDepartment"));
    }

    @Test
    public void testAddDepartment() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/add_Department");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().isOk());
        result.andExpect(forwardedUrl("/WEB-INF/views/addDepartment.jsp"));
        result.andExpect(view().name("addDepartment"));
        result.andExpect(model().attributeExists("modelDepartment"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteDepartment() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/delete_Department")
                .param("id","3");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().is3xxRedirection());
        result.andExpect(view().name("redirect:/table_Department"));
        result.andExpect(redirectedUrl("/table_Department"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testInsertDepartment() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/insert_Department")
                .param("department", "Department99");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().is3xxRedirection());
        result.andExpect(view().name("redirect:/table_Department"));
        result.andExpect(redirectedUrl("/table_Department"));
        result.andExpect(model().attributeExists("modelDepartment"));

        Department department = (Department) result.andReturn().getModelAndView().getModel().get("modelDepartment");
        Assert.assertTrue(department.getId() == 4);
        Assert.assertTrue(department.getDepartment() == "Department99");
    }

    @Test
    public void testEditDepartment() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/edit_Department")
                .param("id", "1");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().isOk());
        result.andExpect(forwardedUrl("/WEB-INF/views/editDepartment.jsp"));
        result.andExpect(view().name("editDepartment"));
        result.andExpect(model().attributeExists("modelDepartment"));
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/update_Department")
                .param("id", "1")
                .param("department", "Department19");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().is3xxRedirection());
        result.andExpect(view().name("redirect:/table_Department"));
        result.andExpect(redirectedUrl("/table_Department"));
        result.andExpect(model().attributeExists("modelDepartment"));

        Department department = (Department) result.andReturn().getModelAndView().getModel().get("modelDepartment");
        Assert.assertTrue(department.getId() == 1);
        Assert.assertTrue(department.getDepartment() == "Department19");
    }

    @Test
    public void testGetEmployeeLIst() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/table_Employee")
                .param("id", "1");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().isOk());
        result.andExpect(view().name("tableEmployee"));
        result.andExpect(forwardedUrl("/WEB-INF/views/tableEmployee.jsp"));
        result.andExpect(model().attributeExists("modelDepartment"));
        result.andExpect(model().attributeExists("modelEmployeeList"));
        ModelAndView modelAndView = result.andReturn().getModelAndView();
        List<Employee> employeeList = (List<Employee>) modelAndView.getModel().get("modelEmployeeList");
        Assert.assertTrue(employeeList.size() == 2);
    }

    @Test
    public void testAddEmployee() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/add_Employee")
                .param("id", "1");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().isOk());
        result.andExpect(view().name("addEmployee"));
        result.andExpect(forwardedUrl("/WEB-INF/views/addEmployee.jsp"));
        result.andExpect(model().attributeExists("tempDepartment"));
        result.andExpect(model().attributeExists("modelEmployee"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testInsertEmployee() throws Exception {
        Department department = new Department();
        department.setId(2);
        department.setDepartment("Department2");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/insert_Employee")
                .param("FullName", "Control Name")
                .param("BirthDate", "2009-10-23")
                .param("Salary", "1050")
                .sessionAttr("tempDepartment", department);
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().is3xxRedirection());
        result.andExpect(view().name("redirect:/table_Employee?id=2"));
        result.andExpect(redirectedUrl("/table_Employee?id=2"));
        result.andExpect(model().attributeExists("modelEmployee"));

        Employee employee = (Employee) result.andReturn().getModelAndView().getModel().get("modelEmployee");
        Assert.assertTrue(employee.getId() == 6);
        Assert.assertTrue(employee.getDepartment() == department);
        Assert.assertTrue(employee.getFullName() == "Control Name");
        Assert.assertEquals(employee.getBirthDate(), formatter.parse("23-10-2009"));
        Assert.assertTrue(employee.getSalary() == 1050);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteEmployee() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/delete_Employee")
                .param("id","2");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().is3xxRedirection());
        result.andExpect(view().name("redirect:/table_Employee?id=1"));
        result.andExpect(redirectedUrl("/table_Employee?id=1"));
    }

    @Test
    public void testEditEmployee() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/edit_Employee")
                .param("id", "2");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().isOk());
        result.andExpect(forwardedUrl("/WEB-INF/views/editEmployee.jsp"));
        result.andExpect(view().name("editEmployee"));
        result.andExpect(model().attributeExists("tempDepartment"));
        result.andExpect(model().attributeExists("modelEmployee"));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/update_Employee")
                .param("id", "2")
                .param("FullName", "Edit Name")
                .param("BirthDate", "1989-01-17")
                .param("Salary", "150");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().is3xxRedirection());
        result.andExpect(view().name("redirect:/table_Employee?id=1"));
        result.andExpect(redirectedUrl("/table_Employee?id=1"));
        result.andExpect(model().attributeExists("modelEmployee"));

        Employee employee = (Employee) result.andReturn().getModelAndView().getModel().get("modelEmployee");
        Assert.assertTrue(employee.getId() == 2);
        Assert.assertTrue(employee.getFullName() == "Edit Name");
        Assert.assertEquals(employee.getBirthDate(), formatter.parse("17-01-1989"));
        Assert.assertTrue(employee.getSalary() == 150);
    }

    @Test
    public void testFindEmployee() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/find_Employee")
                .param("firstBirthDate", "1985-08-21")
                .param("lastBirthDate", "1985-08-21");
        ResultActions result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().isOk());
        result.andExpect(forwardedUrl("/WEB-INF/views/findEmployee.jsp"));
        result.andExpect(view().name("findEmployee"));
        result.andExpect(model().attributeExists("modelEmployeeList"));
        result.andExpect(model().attributeExists("searshRange"));
        List<Employee> employeeList = (List<Employee>) result.andReturn().getModelAndView().getModel().get("modelEmployeeList");
        Assert.assertTrue(employeeList.size() == 2);

        request = MockMvcRequestBuilders.get("/find_Employee")
                .param("firstBirthDate", "1985-08-21")
                .param("lastBirthDate", "2015-08-21");
        result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().isOk());
        result.andExpect(forwardedUrl("/WEB-INF/views/findEmployee.jsp"));
        result.andExpect(view().name("findEmployee"));
        result.andExpect(model().attributeExists("modelEmployeeList"));
        result.andExpect(model().attributeExists("searshRange"));
        employeeList = (List<Employee>) result.andReturn().getModelAndView().getModel().get("modelEmployeeList");
        Assert.assertTrue(employeeList.size() == 4);

        request = MockMvcRequestBuilders.get("/find_Employee")
                .param("firstBirthDate", "1985-08-21");
        result = mockMvc.perform(request);
        result.andDo(print());
        result.andExpect(status().isOk());
        result.andExpect(forwardedUrl("/WEB-INF/views/findEmployee.jsp"));
        result.andExpect(view().name("findEmployee"));
        result.andExpect(model().attributeExists("modelEmployeeList"));
        result.andExpect(model().attributeExists("searshRange"));
        employeeList = (List<Employee>) result.andReturn().getModelAndView().getModel().get("modelEmployeeList");
        Assert.assertTrue(employeeList.size() == 2);
    }

    @Test
    public void testSearshRange() throws Exception {
        MainController mainController = new MainController();
        Date firstBirthDate = new Date(formatter.parse("23-10-2009").getTime());
        Date lastBirthDate = new Date(formatter.parse("23-10-2009").getTime());
        String result = mainController.SearshRange(firstBirthDate, lastBirthDate);
        Assert.assertEquals(result, "for range date: '23-10-2009' - '23-10-2009'");

        firstBirthDate = new Date(formatter.parse("03-04-1985").getTime());
        lastBirthDate = new Date(formatter.parse("23-10-2009").getTime());
        result = mainController.SearshRange(firstBirthDate, lastBirthDate);
        Assert.assertEquals(result, "for range date: '03-04-1985' - '23-10-2009'");

        firstBirthDate = new Date(formatter.parse("14-12-2015").getTime());
        lastBirthDate = null;
        result = mainController.SearshRange(firstBirthDate, lastBirthDate);
        Assert.assertEquals(result, "for date: '14-12-2015'");
    }
}