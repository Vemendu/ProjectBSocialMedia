package com.example.ProjectB.repositories;

import com.example.ProjectB.Entities.Client;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByUsername(String username);
    @Modifying
    @Query("update Client c set c.age = ?1, c.address = ?2, c.email = ?3, c.phoneNumber = ?4 where c.id = ?5")
    void setUserInfoById(Integer age, String address, String email, String phoneNumber, Long userId);

}
