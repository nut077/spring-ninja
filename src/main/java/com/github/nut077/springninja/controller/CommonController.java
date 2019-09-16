package com.github.nut077.springninja.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Log4j2
@RequestMapping("/api/v1")
public class CommonController {

    @PostConstruct
    protected void postConstruct() {
        log.info(getClass().getSimpleName(), "{} : Initialized()");
    }
}
