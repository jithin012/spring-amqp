package com.mclabs.learn.aopExample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@Slf4j
public class Passenger {
    Passenger(int id) {
        this.id = id;
        log.info("on passenger constructor");
    }
    private int id;
}
