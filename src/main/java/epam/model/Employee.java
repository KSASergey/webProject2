package epam.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "ID_Department")
    private Department department;

//    @Column(name = "ID_Department")
//    private Integer iD_Department;

//    @ManyToOne
//    @JoinColumn(name = "ID_Department")
//    @Column(name="NameDepartment")
//    @Column(insertable=false, updatable=false)
//    private String nameDepartment;

    @Column(name="FullName")
    private String fullName;

    @Column(name="BirthDate")
    private Date birthDate;

    @Column(name="Salary")
    private Integer salary;

    public void setId (Integer id) {
        this.id = id;
    }

    public  Integer getId() {
        return this.id;
    }

//    public void setID_Department (Integer  iD_Department) {
//        this.iD_Department = iD_Department;
//    }

//    public Integer  getID_Department() {
//        return this.iD_Department;
//    }

    public void setDepartment (Department department) {
        this.department = department;
    }

    public Department  getDepartment() {
        return this.department;
    }

//    public void setNameDepartment(String  nameDepartment) {
//        this.nameDepartment = nameDepartment;
//    }

//    public String  getNameDepartment() {
//        return this.nameDepartment;
//    }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String  getFullName() { return this.fullName; }

    public void setBirthDate(Date BirthDate) {
        this.birthDate = BirthDate;
    }

    public Date  getBirthDate() {
        return this.birthDate;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Integer  getSalary() {
        return this.salary;
    }

    public String toString() {
        return "Employee - Id:  " + id + ",  nameDepartment:  " + getDepartment().getDepartment() + ",  ID_Department:  " +
                getDepartment().getId() + ", fullName: " + fullName + ", ЬirthDate: " + birthDate + ", salary: " + salary;
    }
}
