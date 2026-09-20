package org.example.laraib.accounts.repository;

import org.example.laraib.accounts.entity.Accounts;
import org.example.laraib.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByCustomerId(Customer customer);

//    @Transactional
//    @Modifying
    void deleteByCustomerId(Customer customer);
}
