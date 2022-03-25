package fr.uge.hyperstack;

import java.io.Serializable;

import fr.uge.hyperstack.model.Stack;

public class Logs implements Serializable {
    private long timestamp;
    private Stack stack;

    public Logs(Stack stack) {
        this.timestamp = System.currentTimeMillis();
        this.stack = stack;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Stack getStack() {
        return stack;
    }

}
