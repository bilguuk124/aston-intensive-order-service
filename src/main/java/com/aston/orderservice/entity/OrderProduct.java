package com.aston.orderservice.entity;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@Embeddable
@AllArgsConstructor
public class OrderProduct {
    private Long foodId;
    private int number;
}
