package com.example.project_voucher.storage.voucher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, Long> {
    // 발행된 코드로 상품권을 찾겠다.
    Optional<VoucherEntity> findByCode(String code);
}
