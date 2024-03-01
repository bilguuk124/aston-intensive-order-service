package com.aston.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "order")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_gen")
    @SequenceGenerator(name = "order_gen", sequenceName = "order_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    private Long user_id;
    private Double price;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ElementCollection
    private List<OrderProduct> cart;

}
