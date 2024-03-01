package com.aston.orderservice.service.impl;

import com.aston.orderservice.entity.*;
import com.aston.orderservice.exception.NotValidStatusException;
import com.aston.orderservice.exception.OrderNotFoundException;
import com.aston.orderservice.feign.MenuClient;
import com.aston.orderservice.repository.OrderRepository;
import com.aston.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final static String TOPIC_NAME = "statisticTopic";

    private final MenuClient menuClient;
    private final OrderRepository repository;
    private final CartBean cart;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public OrderDto getSessionCart() {
        log.info("Getting session cart");
        List<OrderProduct> orderProducts = cart.getCart()
                .entrySet()
                .stream()
                .map(e -> new OrderProduct(e.getKey(), e.getValue()))
                .toList();
        return OrderDto.builder()
                .products(orderProducts)
                .price(getCartPrice())
                .build();
    }

    @Override
    public void addProductToBean(OrderProduct orderProduct) {
        log.info("Adding {} food with id {}", orderProduct.getNumber(), orderProduct.getFoodId());
        cart.getCart().merge(orderProduct.getFoodId(), orderProduct.getNumber(), Integer::sum);
    }

    @Override
    public void updateProductInCart(OrderProduct orderProduct) {
        log.info("Updating {} food with id {}", orderProduct.getNumber(), orderProduct.getFoodId());
        cart.getCart().put(orderProduct.getFoodId(), orderProduct.getNumber());
    }

    @Override
    public void removeProductFromBean(Long foodId) {
        log.info("Removing food with id {} from cart", foodId);
        cart.getCart().remove(foodId);
    }

    @Override
    public Order orderCart(Long userId) {
        log.info("User with id {} paid for the cart, creating order", userId);
        List<OrderProduct> products = cart.getCart()
                .entrySet()
                .stream()
                .map(e -> new OrderProduct(e.getKey(), e.getValue()))
                .toList();

        Order order = Order.builder()
                .user_id(userId)
                .status(OrderStatus.CREATED)
                .cart(products)
                .price(getCartPrice())
                .build();

        sendStatistics(products, userId);
        return repository.saveAndFlush(order);
    }

    @Override
    public Order updateOrderStatus(Long id, String status) throws OrderNotFoundException, NotValidStatusException {
        log.info("Updating order status");
        OrderStatus orderStatus = OrderStatus.parseStatus(status);
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " was not found"));
        order.setStatus(orderStatus);
        return repository.save(order);
    }

    private Double getCartPrice() {
        log.info("Getting cart price");
        return menuClient.getCartPrice(cart.getCart());
    }

    @Async
    protected void sendStatistics(List<OrderProduct> products, Long userId){
        log.info("Sending statistics!");
        LocalDate now = LocalDate.now();
        for(OrderProduct orderProduct : products){
            StatisticDto statistic = StatisticDto.builder()
                    .foodId(orderProduct.getFoodId())
                    .userId(userId)
                    .number(orderProduct.getNumber())
                    .boughtDate(now)
                    .build();
            kafkaTemplate.send(TOPIC_NAME, statistic);
        }
    }
}
