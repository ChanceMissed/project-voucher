package com.example.project_voucher.app.controller.employee;

import com.example.project_voucher.app.controller.employee.request.EmployeeCreateRequest;
import com.example.project_voucher.app.controller.employee.response.EmployeeResponse;
import com.example.project_voucher.domain.employee.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 회원 생성
    @PostMapping("/api/v1/employee")
    public Long create(@RequestBody EmployeeCreateRequest request) {
        return employeeService.create(request.name(), request.position(), request.department());
    }

    // 회원 조회
    @GetMapping("/api/v1/employee/{no}")
    public EmployeeResponse get(@PathVariable(value = "no") final Long no) {
        return employeeService.get(no);
    }
}

