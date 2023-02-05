package com.tech.api.storage.repository;

import com.tech.api.storage.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

    Account findAccountByUsername(String username);
    Account findAccountByEmail(String email);
    Long countAccountByUsernameOrEmailOrPhone(String username, String email, String phone);
    Long countAccountByUsername(String username);
    Long countAccountByPhoneOrEmail(String phone, String email);
    @Query("SELECT a FROM Account a WHERE a.username = ?1 OR a.phone = ?1")
    Account findAccountByUsernameOrPhone(String usernameOrPhone);

}
