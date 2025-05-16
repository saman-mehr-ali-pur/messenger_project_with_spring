package com.example.demo.repo;

import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    List<User> findByUsername(String username);
//    List<User> findByUsernameAndPassword(String username,String password);
    Page<User> findAll(Pageable pageable);
    Optional<User> findByUsernameAndPassword(String username,String password);
    Optional<User> findById(Integer id);
    User save(User user);

}
