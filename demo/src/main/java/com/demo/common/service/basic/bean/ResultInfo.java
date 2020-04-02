package com.demo.common.service.basic.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResultInfo {

    private int result = 0;


    private List<SendDetail> sendDetails;

    @Data
    public static class SendDetail {
        private String code;
    }
}
