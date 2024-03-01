package com.aston.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "menu", url = "{feign.menu.url}")
public interface MenuClient {
    @GetMapping(value = "/food/totalPrice")
    Double getCartPrice(Map<Long, Integer> foodIds);
}
