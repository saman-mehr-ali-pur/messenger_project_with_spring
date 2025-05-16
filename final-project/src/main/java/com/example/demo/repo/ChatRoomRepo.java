package com.example.demo.repo;

import com.example.demo.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ChatRoomRepo extends JpaRepository<ChatRoom,Integer> {

    ChatRoom save(ChatRoom room);
    List<ChatRoom> findAll();
    Optional<ChatRoom> findById(int id);
    @Query("SELECT cr FROM ChatRoom cr INNER JOIN FETCH cr.members u WHERE cr.id =:chatRoomId")
    List<ChatRoom> findByUser(@Param("chatRoomId") Long chatRoomId);


}
