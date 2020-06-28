package com.demo.common.service.stream.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToEntity {
    String name;
    Double score;
    int age;
    String type;
    Long longSize;
    BigDecimal price;
}
