package org.example.DZ3V2;

import java.util.concurrent.ThreadLocalRandom;

public class Department {

    private int departmentId;
    private String departmentName;
    private final int departmentNumber = 5;

    public Department() {
        setDepartmentName();
    }

    public void setDepartmentName() {
        this.departmentName = "departmentName #" + ThreadLocalRandom.current().nextInt(0, departmentNumber);
    }

    public String getDepartmentName() {
        return departmentName;
    }

}
