package pl.szotaa.fbweatherbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping
    public void get(){
        log.info("get");
    }

    @PostMapping
    public void post(){
        log.info("post");
    }
}
