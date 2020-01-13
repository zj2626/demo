package com.google;
com.demo.common.service.spring.learn3.proxy.dynamic1.TargetClass;

public class Proxy$01 implements TargetClass {
	private TargetClass target;
	public Proxy$01 (TargetClass target){
		this.target = target;
	}

	@Override
	public void action1(){
		this.target.action1();
	}

	@Override
	public void action2(java.lang.String v0,java.lang.Integer v1,com.demo.common.service.spring.learn3.proxy.dynamic1.bean.Person v2){
		this.target.action2();
	}
}
