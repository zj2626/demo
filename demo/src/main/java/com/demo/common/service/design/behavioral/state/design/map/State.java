package com.demo.common.service.design.behavioral.state.design.map;

public interface State {
    public String getState();

    public void handle(Context context);
}
