package com.example.demo.repo;

import com.example.demo.model.ChatRoom;
import com.example.demo.model.enums.RoomType;
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
    @Query("SELECT c FROM ChatRoom c JOIN c.members m WHERE m.id = :userId")
    List<ChatRoom> findAllByUserId(@Param("userId") Long userId);
    @Query("SELECT cr\n" +
            "FROM ChatRoom cr\n" +
            "JOIN cr.members m\n" +
            "WHERE cr.type = :type\n" +
            "  AND m.id IN (:id1, :id2)\n" +
            "GROUP BY cr.id\n" +
            "HAVING COUNT(DISTINCT m.id) = 2\n")
    Optional<ChatRoom> findByUserId(@Param("id1") Long id1,@Param("id2") Long id2, @Param("type") RoomType type);



    ChatRoom findByRoomName(String roomName);


}
