package org.example.DZ3V3;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Department {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(departmentName, that.departmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(departmentName);
    }
}
