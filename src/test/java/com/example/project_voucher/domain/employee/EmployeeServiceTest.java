package com.example.project_voucher.domain.employee;

import com.example.project_voucher.app.controller.response.EmployeeResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @DisplayName("회원 생성 후 조회가 가능하다.")
    @Test
    public void test1(){
        // given
        String name = "홍길동";
        String position = "사원";
        String department = "개발팀";

        // when
        Long no = employeeService.create(name, position, department);
        EmployeeResponse response = employeeService.get(no);

        // then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.no()).isEqualTo(no);
        Assertions.assertThat(response.name()).isEqualTo(name);
        Assertions.assertThat(response.position()).isEqualTo(position);
        Assertions.assertThat(response.department()).isEqualTo(department);

        System.out.println(response);
    }

}