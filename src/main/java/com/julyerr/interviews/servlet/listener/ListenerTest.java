package com.julyerr.interviews.servlet.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ListenerTest implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println("session created:"+httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("session removed:"+httpSessionEvent.getSession().getId());
    }
}
