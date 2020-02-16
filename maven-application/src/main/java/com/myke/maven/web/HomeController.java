package com.myke.maven.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zh
 * @date: 2020/2/15/015 19:51
 */
@Slf4j
@RestController
public class HomeController {

    @GetMapping("/")
    public String indexPage() {
        log.info("maven demo");
        return "maven demo";
    }
}
