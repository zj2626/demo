package com.kdniao.logisticsfront.common.biz.service.impl.stream.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToEntity {
    String name;
    Double score;
    int age;
    String type;
}
