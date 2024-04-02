package com.example.splitwise1.repositories;

import com.example.splitwise1.models.Group;
import com.example.splitwise1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByAdmin(User user) ;
    @Query("select users from swgroup where id = ?1")
    List<User> findByGroup(Long groupId);
}
