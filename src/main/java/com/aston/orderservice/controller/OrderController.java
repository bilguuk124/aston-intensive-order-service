package com.aston.orderservice.controller;

import com.aston.orderservice.entity.Order;
import com.aston.orderservice.entity.OrderDto;
import com.aston.orderservice.entity.OrderProduct;
import com.aston.orderservice.exception.NotValidStatusException;
import com.aston.orderservice.exception.OrderNotFoundException;
import com.aston.orderservice.service.OrderService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService service;

    // Public endpoint
    @GetMapping("/cart")
    public ResponseEntity<OrderDto> getSessionCart(){
        log.info("Got request to get session cart");
        return ResponseEntity.ok(service.getSessionCart());
    }

    // Public endpoint
    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody OrderProduct orderProduct){
        log.info("Got request to add a new product to cart");
        service.addProductToBean(orderProduct);
        return ResponseEntity.ok().build();
    }

    // Public endpoint
    @PostMapping
    public ResponseEntity<Order> orderCart(@RequestHeader("userId") Long userId){
        log.info("Got request to order the cart");
        return ResponseEntity.ok(service.orderCart(userId));
    }

    // Public endpoint
    @PutMapping("/cart")
    public ResponseEntity<?> updateNumberInCart(@RequestBody OrderProduct orderProduct){
        log.info("Got request to update a product in cart");
        service.updateProductInCart(orderProduct);
        return ResponseEntity.ok().build();
    }

    // Public endpoint
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable("id") Long id){
        log.info("Got request to delete a product in cart");
        service.removeProductFromBean(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody String status) throws OrderNotFoundException, NotValidStatusException {
        log.info("Got request to update order status");
        return ResponseEntity.ok(service.updateOrderStatus(id, status));
    }


}
