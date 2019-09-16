package com.github.nut077.springninja.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Log4j2
@Service
public class OderService {

    @PostConstruct
    private void init() {
        log.info(this.getClass().getSimpleName() + ": init");
    }
}
