package epam.model;

public class Department {

    private Integer id;
    private String department;
    private Double averageSalary;


    public Department() { }

    public Department(Integer id, String department, Double averageSalary) {
        this.id = id;
        this.department = department;
        this.averageSalary = averageSalary;
    }

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
