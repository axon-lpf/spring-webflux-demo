package com.spring.learning.springwebfluxdemo.controller;

import com.spring.learning.springwebfluxdemo.enties.UserDTO;
import com.spring.learning.springwebfluxdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/user/queryUserById")
    public Mono<UserDTO> queryUserById(@RequestParam(name = "id") Long id) {
        return userService.queryUserById(id);
    }

    @GetMapping("/user/queryAllUser")
    public Flux<UserDTO> queryAllUser() {
        return userService.queryAllUser();
    }

    @PostMapping("/user/saveUser")
    public Mono<Void> saveUser(@RequestBody UserDTO user) {
        return userService.save(Mono.just(user));
    }

}
