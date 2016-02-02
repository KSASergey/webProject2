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

    @Formula("(select COALESCE(AVG(e.Salary),0) FROM Employee e WHERE e.ID_Department=ID)")
    private Double averageSalary;

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

    @Override
    public String toString() {
        return "Department - Id:  " + id + ",  department:  " + department + ", average salary:  " + averageSalary;
    }
}
