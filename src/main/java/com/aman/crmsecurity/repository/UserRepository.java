package com.aman.crmsecurity.repository;

import com.aman.crmsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Integer> {

//    Optional<User>  findByEmailid(String email);

    Optional<User> findByEmail(String email);

}
