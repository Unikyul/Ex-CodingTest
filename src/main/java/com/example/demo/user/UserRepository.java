package com.example.demo.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.name=:name")
    Optional<User> FindByName(@Param("name") String name);


}
