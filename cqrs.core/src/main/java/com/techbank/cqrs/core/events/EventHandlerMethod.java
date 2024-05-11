package com.techbank.cqrs.core.events;

@FunctionalInterface
public interface EventHandlerMethod<T extends BaseEvent> {

    void on(T event);

}
