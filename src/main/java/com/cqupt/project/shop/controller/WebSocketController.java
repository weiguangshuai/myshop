package com.cqupt.project.shop.controller;

import com.cqupt.project.shop.vo.Greeting;
import com.cqupt.project.shop.vo.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author weigs
 * @date 17-12-8
 */
@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage helloMessage) throws InterruptedException {
        Thread.sleep(1000);
        Greeting greeting = new Greeting();
        greeting.setContent(helloMessage.getContent());
        return greeting;
    }
}
