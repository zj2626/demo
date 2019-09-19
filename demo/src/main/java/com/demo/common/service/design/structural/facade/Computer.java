package com.demo.common.service.design.structural.facade;

/**
 * 功能: 操作系统启动
 */
public class Computer {
    private CPU cpu;
    private Disk disk;
    private Memory memory;

    public Computer() {
        cpu = new CPU();
        disk = new Disk();
        memory = new Memory();
    }

    public void turnOnComputer() {
        disk.start();
        memory.start();
        cpu.start();
    }

    public void turnOffComputer() {
        cpu.shutdown();
        memory.shutdown();
        disk.shutdown();
    }
}
