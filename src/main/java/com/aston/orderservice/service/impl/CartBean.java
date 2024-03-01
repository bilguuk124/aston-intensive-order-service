package com.aston.orderservice.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
@SessionScope
public class CartBean {

    private Map<Long, Integer> cart;

    @PostConstruct
    public void init(){
        if (cart == null) cart = new HashMap<>();
    }

}
