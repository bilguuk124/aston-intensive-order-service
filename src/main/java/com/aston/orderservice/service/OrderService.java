package com.aston.orderservice.service;

import com.aston.orderservice.entity.Order;
import com.aston.orderservice.entity.OrderDto;
import com.aston.orderservice.entity.OrderProduct;
import com.aston.orderservice.exception.NotValidStatusException;
import com.aston.orderservice.exception.OrderNotFoundException;

public interface OrderService {
    OrderDto getSessionCart();

    void addProductToBean(OrderProduct orderProduct);

    void updateProductInCart(OrderProduct orderProduct);

    void removeProductFromBean(Long foodId);

    Order orderCart(Long userId);

    Order updateOrderStatus(Long id, String status) throws OrderNotFoundException, NotValidStatusException;
}
