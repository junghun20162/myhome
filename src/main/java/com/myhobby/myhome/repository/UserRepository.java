package com.myhobby.myhome.repository;

import com.myhobby.myhome.model.Board;
import com.myhobby.myhome.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, CustomizedUserRepository{

    @EntityGraph(attributePaths = {"boards"})
    List<User> findAll();
    User findByUsername(String username);

    @Query("select u from User u where u.username like %?1%")
    List<User> findByUsernameQuery(String username);

    @Query(value = "select * from User u where u.username like %?1%", nativeQuery = true)
    List<User> findByUsernameNativeQuery(String username);


}
