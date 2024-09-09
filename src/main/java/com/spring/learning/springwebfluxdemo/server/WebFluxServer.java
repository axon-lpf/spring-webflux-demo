package com.spring.learning.springwebfluxdemo.server;


import com.spring.learning.springwebfluxdemo.service.IUserService;
import com.spring.learning.springwebfluxdemo.service.impl.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

/**
 * 服务端的处理
 *
 * 核心类： RouterFunction、  userHandler
 *
 */
public class WebFluxServer {

    public static void main(String[] args) throws IOException {
        WebFluxServer webFluxServer = new WebFluxServer();
        webFluxServer.createWebFluxServer();
        System.out.println("enter to exit");
        System.in.read();
    }

    public RouterFunction<ServerResponse> routingFunction() {
        IUserService userService = new UserServiceImpl();
        UserHandler userHandler = new UserHandler(userService);

        return RouterFunctions.route(GET("/user/queryUserById/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::queryUserById)
                .andRoute(GET("/user/queryAllUser").and(accept(MediaType.APPLICATION_JSON)), userHandler::queryAllUser)
                .andRoute(POST("/user/save").and(accept(MediaType.APPLICATION_JSON)), userHandler::save);

    }

    public void createWebFluxServer() {
        RouterFunction<ServerResponse> serverResponseRouterFunction = routingFunction();
        HttpHandler handler = toHttpHandler(serverResponseRouterFunction);
        ReactorHttpHandlerAdapter reactorHttpHandlerAdapter = new ReactorHttpHandlerAdapter(handler);

        HttpServer httpServer = HttpServer.create();

        httpServer.handle(reactorHttpHandlerAdapter).bindNow();
    }
}
