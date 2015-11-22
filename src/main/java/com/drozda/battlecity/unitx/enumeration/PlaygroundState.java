package com.drozda.battlecity.unitx.enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 07.10.2015.
 */
public enum PlaygroundState {
    CREATED,
    INITIALIZING,
    ACTIVE,
    PAUSED,
    STOPPED;
    static Map<PlaygroundState, List<PlaygroundState>> allowedTransitions = new HashMap<>();

    static {
        allowedTransitions.put(CREATED, asList(INITIALIZING));
        allowedTransitions.put(INITIALIZING, asList(ACTIVE));
        allowedTransitions.put(ACTIVE, asList(PAUSED, STOPPED));
        allowedTransitions.put(PAUSED, asList(ACTIVE, STOPPED));
        allowedTransitions.put(STOPPED, new ArrayList<>());
    }

    protected final Logger log = LoggerFactory.getLogger(PlaygroundState.class);

    public PlaygroundState tryTransitionIgnoreWrong(PlaygroundState newState) {
        if (!canTransition(newState)) {
            return this;
        } else {
            return newState;
        }
    }

    public boolean canTransition(PlaygroundState newState) {
        if (newState == null) {
            throw new IllegalArgumentException("newState can not be null");
        }
        return getAllowedTransitions().contains(newState);
    }

    public List<PlaygroundState> getAllowedTransitions() {
        return allowedTransitions.get(this);
    }


    public PlaygroundState tryTransition(PlaygroundState newState) {
        if (!canTransition(newState)) {
            throw new IllegalArgumentException("Cant make transition from " + this + "to " + newState);
        } else {
            return newState;
        }
    }
}
