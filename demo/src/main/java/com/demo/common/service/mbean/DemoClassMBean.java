package com.demo.common.service.mbean;

public interface DemoClassMBean  {
    public String getName();
    public void setName(String name);
    public void printHello();
    public void printHello(String whoName);
}