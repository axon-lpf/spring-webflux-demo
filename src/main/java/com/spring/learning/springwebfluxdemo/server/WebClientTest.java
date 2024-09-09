package com.spring.learning.springwebfluxdemo.server;

import com.spring.learning.springwebfluxdemo.enties.UserDTO;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *  这是客户端的请求释放
 */
public class WebClientTest {

    public static void main(String[] args) {

        WebClient webClient = WebClient.create("http://127.0.0.1:54145");
        String id = "1";
        UserDTO block = webClient.get().uri("/user/queryUserById/{id}", id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(UserDTO.class).block();
        System.out.println(block.getName());
        System.out.println("客户端的请求方式");
    }
}
