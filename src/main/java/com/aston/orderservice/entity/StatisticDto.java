package com.aston.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("statistic")
public class StatisticDto {
    private Long userId;
    private Long foodId;
    private Integer number;
    private LocalDate boughtDate;

}
