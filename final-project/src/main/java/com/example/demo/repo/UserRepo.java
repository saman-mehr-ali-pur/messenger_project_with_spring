package com.example.demo.repo;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {

    List<User> findAll(Pageable pageable);
    User findByUsername(String username);
    User findById(int id);
    User save(User user);

}
