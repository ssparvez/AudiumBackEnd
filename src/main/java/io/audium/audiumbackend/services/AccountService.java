package io.audium.audiumbackend.services;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.audium.audiumbackend.entities.CustomerAccount;
import io.audium.audiumbackend.repositories.AccountRepository;
import io.audium.audiumbackend.repositories.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private CustomerAccountRepository customerAccountRepo;

    public void registerAccount(CustomerAccount customerAccount) {
        customerAccount.setRole("BasicUser");
         customerAccount.setIsactive(new Long(1));
        customerAccountRepo.save(customerAccount);
       // System.out.println(customerAccount.getAccountid().toString());

    }

    public void deleteAccount(Long id) {

        //CustomerAccount ca = customerAccountRepo.findOne(new Long(7));
        //System.out.println("hello");
        //System.out.println(ca.getSongs().get(0).getTitle());
       accountRepo.deleteById(id);
    }


}
