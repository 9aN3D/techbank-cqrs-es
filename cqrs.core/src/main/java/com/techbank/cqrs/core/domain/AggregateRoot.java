package com.techbank.cqrs.core.domain;

import com.techbank.cqrs.core.events.BaseEvent;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregateRoot {

    @Getter
    protected String id;
    @Getter
    @Setter
    private int version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public List<BaseEvent> getUncommittedChanges() {
        return changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    public void raiseEvent(BaseEvent event) {
        applyChanges(event, true);
    }

    public void replayEvent(Iterable<BaseEvent> events) {
        events.forEach(event -> applyChanges(event, false));
    }

    protected void applyChanges(BaseEvent event, boolean isNewEvent) {
        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException exception) {
            logger.log(Level.WARNING, MessageFormat.format("The apply method was not found in the aggregate for {0}", event.getClass().getName()));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while applying an event to the aggregate", e);
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AggregateRoot.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("version=" + version)
                .add("changesSize=" + changes.size())
                .toString();
    }

}
