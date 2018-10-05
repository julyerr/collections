package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.lock;

import java.util.Set;

public class ServerStatus {
    public final Set<String> users;
    public final Set<String> queries;

    public ServerStatus(Set<String> users, Set<String> queries) {
        this.users = users;
        this.queries = queries;
    }

    public synchronized void addUser(String u) {
        users.add(u);
    }

    public synchronized void addQuery(String q) {
        queries.add(q);
    }
    //...
}
