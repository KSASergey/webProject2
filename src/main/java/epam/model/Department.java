package epam.model;

import org.hibernate.annotations.Formula;
import javax.persistence.*;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="Department")
    private String department;

//    @Column(insertable=false, updatable=false)
//    @Transient

    @Formula("(select COALESCE(AVG(e.Salary),0) FROM Employee e WHERE e.ID_Department=ID)")
    private Double averageSalary;

//    @OneToMany(cascade=CascadeType.ALL, mappedBy="department")
//    @JoinColumn(name="ID_Department")
//    private Set<Employee> employee;

//    public Department() { }
//
//    public Department(Integer id, String department, Double averageSalary) {
//        this.id = id;
//        this.department = department;
//        this.averageSalary = averageSalary;
//    }

//    @OneToMany(mappedBy="department",cascade=CascadeType.PERSIST)
//    private List<Employee> employees = new ArrayList<Employee>();

//    @OneToMany(mappedBy = "contact", cascade=CascadeType.ALL,orphanRemoval=true)
//    public Set<ContactTelDetail> getContactTelDetails() {
//        return this.contactTelDetails;
//    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() { return this.id;}

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() { return this.department; }

    public void setAverageSalary(Double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public Double getAverageSalary() { return this.averageSalary; }

//    public Set<Employee> getEmployee() {
//        return employee;
//    }

//    public void setEmployee(Set<Employee> employee) {
//        this.employee = employee;
//    }

    @Override
    public String toString() {
        return "Department - Id:  " + id + ",  department:  " + department + ", average salary:  " + averageSalary;
    }
}
