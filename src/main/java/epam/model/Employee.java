package epam.model;

import java.sql.Date;

public class Employee {

    private Integer id;
    private Integer departmentId;
    private String nameDepartment;
    private String fullName;
    private Date birthDate;
    private Integer salary;

    public void setId (Integer id) {
        this.id = id;
    }

    public  Integer getId() {
        return this.id;
    }

    public void setDepartmentId (Integer  DepartmentId) {
        this.departmentId = DepartmentId;
    }

    public Integer  getDepartmentId() {
        return this.departmentId;
    }

    public void setNameDepartment(String  nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public String  getNameDepartment() {
        return this.nameDepartment;
    }

    public void setFullName(String  fullName) { this.fullName = fullName; }

    public String  getFullName() { return this.fullName; }

    public void setBirthDate(Date  BirthDate) {
        this.birthDate = BirthDate;
    }

    public Date  getBirthDate() {
        return this.birthDate;
    }

    public void setSalary(int  salary) {
        this.salary = salary;
    }

    public Integer  getSalary() {
        return this.salary;
    }

    public String toString() {
        return "Employee - Id:  " + id + ",  nameDepartment:  " + nameDepartment + ",  departmentId:  " +
                departmentId + ", fullName: " + fullName + ", ЬirthDate: " + birthDate + ", salary: " + salary;
    }
}
