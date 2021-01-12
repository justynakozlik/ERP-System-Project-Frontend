package sample.table;

import javafx.beans.property.SimpleStringProperty;
import sample.dto.EmployeeDto;

public class EmployeeTableModel {

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty salary;

    public EmployeeTableModel(String firstName, String lastName, String salary) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.salary = new SimpleStringProperty(salary);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getSalary() {
        return salary.get();
    }

    public SimpleStringProperty salaryProperty() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary.set(salary);
    }

    public static EmployeeTableModel of(EmployeeDto dto) {
        EmployeeTableModel model = new EmployeeTableModel(
                dto.getFirstName(), dto.getLastName(), dto.getSalary()
        );
        return model;
    }
}
