package com.demo.common.service.design.structural.facade.design.map;

/**
 * 外观角色
 * <p>
 * 门面角色：外观模式的核心。它被客户角色调用，它熟悉子系统的功能。内部根据客户角色的需求预定了几种功能的组合。
 */
public class Facade {
    SubSystem subSystem;

    public Facade() {
        subSystem = new SubSystem();
    }

    public void operation(){
        // subSystem operation
    }
}
