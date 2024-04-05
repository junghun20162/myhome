package com.myhobby.myhome.controller;

import com.myhobby.myhome.model.Board;
import com.myhobby.myhome.model.User;
import com.myhobby.myhome.repository.BoardRepository;

import com.myhobby.myhome.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
class UserApiController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/users")
    List<User> all(@RequestParam(required = false) String method,
                   @RequestParam(required = false) String text) {
        List<User> users = null;
        if ("query".equals(method)) {
            users = repository.findByUsernameQuery(text);
        } else if ("nativeQuery".equals(method)) {
            users = repository.findByUsernameNativeQuery(text);
        } else if ("querydsl".equals(method)) {
//            QCustomer customer = QCustomer.customer;
//            Predicate predicate = user.firstname.equalsIgnoreCase("dave")
//                    .and(user.lastname.startsWithIgnoreCase("mathews"));
//            repository.findAll(predicate);
        } else if ("jdbc".equals(method)) {
            users = repository.findByUsernameJdbc(text);
        }
        else {
            users = repository.findAll();
        }
        return users;
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return repository.findById(id)
                .orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
//                    User.setTitle(newUser.getTitle());
//                    User.setContent(newUser.getContent());
//                    user.setBoards(newUser.getBoards());
                    user.getBoards().clear();
                    user.getBoards().addAll(newUser.getBoards());
                    for(Board board: user.getBoards()) {
                        board.setUser(user);
                    }
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
