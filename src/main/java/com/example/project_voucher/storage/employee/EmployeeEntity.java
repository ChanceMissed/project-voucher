package com.example.project_voucher.storage.employee;

import com.example.project_voucher.storage.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "employee")
@Entity
public class EmployeeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String position;
    private String department;

    public EmployeeEntity(){}

    public EmployeeEntity(String name, String position, String department) {
        this.name = name;
        this.position = position;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }
}
