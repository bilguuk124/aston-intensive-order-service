package com.aston.orderservice.entity;

import com.aston.orderservice.exception.NotValidStatusException;

import java.util.Arrays;

public enum OrderStatus {
    CREATED,
    COOKING,
    SHIPPING,
    SUCCESS;

    public static OrderStatus parseStatus(String status) throws NotValidStatusException {
        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.name().equalsIgnoreCase(status))
                .findFirst().orElseThrow(() -> new NotValidStatusException("Status " + status + " is not valid status"));
    }
}
