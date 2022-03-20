package com.hlg.fcf.event;

public interface EventPublisher<T> {

    public void publish(T event);

}
