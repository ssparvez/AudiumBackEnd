package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.PaymentInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentInfoRepository extends CrudRepository<PaymentInfo, Long> {


    @Transactional(readOnly = true)
    @Query( "SELECT P FROM PaymentInfo P WHERE P.accountId = ?1")
    public PaymentInfo findPaymentInfoByAccountId(Long id);

  @Transactional
  @Modifying
  @Query("DELETE FROM PaymentInfo P WHERE P.accountId = ?1")
  public int deletePaymentInfo(Long accountId);

}
