package com.example.project_voucher.domain.employee;

import com.example.project_voucher.app.controller.response.EmployeeResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final Map<Long, EmployeeResponse> employeeResponseMap = new HashMap<>();

    // 사원 생성
    public Long create(final String name, final String position, final String department) {
        Long no = employeeResponseMap.size() + 1L;
        employeeResponseMap.put(no,
            new EmployeeResponse(employeeResponseMap.size() + 1L, name, position, department));

        return no;
    }

    // 사원 조회
    public EmployeeResponse get(final Long no) {
        return employeeResponseMap.get(no);
    }


}
