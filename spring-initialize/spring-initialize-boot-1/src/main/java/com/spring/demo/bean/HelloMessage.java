package com.spring.demo.bean;

import lombok.Data;

@Data
public class HelloMessage {

	private String sendTo;

	private String sendFrom;

	private String msg;
}
