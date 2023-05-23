<<<<<<<< HEAD:src/main/java/com/indekos/controller/repository/AccountRepository.java
package com.indekos.controller.repository;
========
package com.indekos.repository;
>>>>>>>> paul:src/main/java/com/indekos/repository/AccountRepository.java

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indekos.model.Account;
import com.indekos.model.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByUsername(String username);
    Account findByUser(User user);
}
