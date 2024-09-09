package com.spring.learning.springwebfluxdemo.service.impl;

import com.spring.learning.springwebfluxdemo.enties.UserDTO;
import com.spring.learning.springwebfluxdemo.service.IUserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

    private Map<Long, UserDTO> userMap = new HashMap<>();

    @Override
    public Mono<UserDTO> queryUserById(Long userId) {
        UserDTO user = userMap.getOrDefault(userId, null);
        return Mono.just(user).switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<UserDTO> queryAllUser() {
        Collection<UserDTO> values = userMap.values();
        return Flux.fromIterable(values);
    }

    @Override
    public Mono<Void> save(Mono<UserDTO> userMonos) {
        return userMonos.doOnNext(f -> {
            Long i = Long.valueOf(userMap.size());
            f.setId(i);
            userMap.put(i, f);
        }).thenEmpty(Mono.empty());
    }
}
