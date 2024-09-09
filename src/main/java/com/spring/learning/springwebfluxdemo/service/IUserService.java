package com.spring.learning.springwebfluxdemo.service;

import com.spring.learning.springwebfluxdemo.enties.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {

    Mono<UserDTO> queryUserById(Long  userId);


    Flux<UserDTO> queryAllUser();


    Mono<Void> save(Mono<UserDTO> userDTO);
}
