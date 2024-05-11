package com.techbank.cqrs.core.queries;

import com.techbank.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryDispatcher {

    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> query);

    <E extends BaseEntity> List<E> send(BaseQuery query);

}
