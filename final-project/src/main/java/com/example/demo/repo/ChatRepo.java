package com.example.demo.repo;

import com.example.demo.model.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepo extends JpaRepository<Chat,Integer> {

    List<Chat> findAll();
    List<Chat> findByChatRoom(Sort sort, Pageable pageable);
}
