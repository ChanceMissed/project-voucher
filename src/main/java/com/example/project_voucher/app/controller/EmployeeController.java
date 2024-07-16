package com.example.project_voucher.app.controller;

import com.example.project_voucher.app.controller.request.EmployeeCreateRequest;
import com.example.project_voucher.app.controller.response.EmployeeResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    // 사번 만 리턴해주는 Map
    private final Map<Long, EmployeeResponse> employeeResponseMap = new HashMap<>();

    // 회원 생성
    @PostMapping("/api/v1/employee")
    public Long create(@RequestBody EmployeeCreateRequest request){
        Long no = employeeResponseMap.size() + 1L;
        employeeResponseMap.put(no, new EmployeeResponse(employeeResponseMap.size() + 1L, request.name(), request.position(), request.department()));

        return no;
    }

    // 회원 조회
    @GetMapping("/api/v1/employee/{no}")
    public EmployeeResponse get(@PathVariable final Long no) {
        return employeeResponseMap.get(no);
    }
}

