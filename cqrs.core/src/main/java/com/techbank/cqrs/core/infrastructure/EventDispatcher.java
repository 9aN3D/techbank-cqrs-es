package com.techbank.cqrs.core.infrastructure;

import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.events.EventHandlerMethod;

public interface EventDispatcher {

    <T extends BaseEvent> void registerHandler(Class<T> type, EventHandlerMethod<T> handlerMethod);

    void on(BaseEvent event);

}
