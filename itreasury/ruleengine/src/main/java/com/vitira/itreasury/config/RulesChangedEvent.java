package com.vitira.itreasury.config;

import org.springframework.context.ApplicationEvent;

public class RulesChangedEvent extends ApplicationEvent {
    public RulesChangedEvent(Object source) {
        super(source);
    }
}