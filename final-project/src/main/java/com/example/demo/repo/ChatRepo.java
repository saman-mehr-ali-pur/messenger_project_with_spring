package com.example.demo.repo;

import com.example.demo.model.Chat;
import com.example.demo.model.ChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepo extends JpaRepository<Chat,Integer> {

    List<Chat> findAll();
    List<Chat> findByRoom(ChatRoom room, Pageable pageable);
    Chat save(Chat chat);
    Optional<Chat> findById(Integer id);
//    List<Chat> findByRoom(ChatRoom room);
}
