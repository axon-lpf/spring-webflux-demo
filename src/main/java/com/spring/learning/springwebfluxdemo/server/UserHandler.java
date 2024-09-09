package com.spring.learning.springwebfluxdemo.server;

import com.spring.learning.springwebfluxdemo.enties.UserDTO;
import com.spring.learning.springwebfluxdemo.service.IUserService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.springframework.web.reactive.function.BodyInserters.fromObject;


/**
 * 函数式编程
 */
public class UserHandler {

    private IUserService userService;

    public UserHandler(IUserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> queryUserById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<UserDTO> userDTOMono = userService.queryUserById(id);
        return userDTOMono.flatMap(person -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(person))).switchIfEmpty(notFound);

    }

    public Mono<ServerResponse> queryAllUser(ServerRequest request) {
        Flux<UserDTO> userDTOFlux = userService.queryAllUser();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userDTOFlux, UserDTO.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<UserDTO> userDTOMono = request.bodyToMono(UserDTO.class);
        return ServerResponse.ok().build(this.userService.save(userDTOMono));
    }
}
