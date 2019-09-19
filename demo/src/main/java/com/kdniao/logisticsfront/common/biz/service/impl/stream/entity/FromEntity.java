package com.kdniao.logisticsfront.common.biz.service.impl.stream.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FromEntity {
    String name;
    Double score;
    int age;
    String type;
}
