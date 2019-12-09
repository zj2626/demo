package com.demo.common.service.thread.abs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Params {
    String type;
    Integer size;
    boolean isOrder = false;
    Object data;
    int from;
    int to;
}
