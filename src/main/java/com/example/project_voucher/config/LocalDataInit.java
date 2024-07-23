package com.example.project_voucher.config;

import com.example.project_voucher.storage.voucher.ContractEntity;
import com.example.project_voucher.storage.voucher.ContractRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import org.springframework.context.annotation.Configuration;

/**
 *  로컬 데이터를 미리 넣어놓고 테스트 하기위함
 */
@Configuration
public class LocalDataInit {

    private final ContractRepository contractRepository;

    public LocalDataInit(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @PostConstruct
    public void init(){
        // 계약 데이터 초기화
        contractRepository.save(new ContractEntity("CT0001", LocalDate.now().minusDays(7), LocalDate.now().plusYears(1), 366 * 5));
    }
}
