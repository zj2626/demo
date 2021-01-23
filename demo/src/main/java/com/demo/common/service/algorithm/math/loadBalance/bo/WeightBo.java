package com.demo.common.service.algorithm.math.loadBalance.bo;

import com.demo.common.service.algorithm.math.loadBalance.Server;
import lombok.Data;

@Data
public class WeightBo extends Server {

    private int originWeight;
    private int currentWeight;
}
