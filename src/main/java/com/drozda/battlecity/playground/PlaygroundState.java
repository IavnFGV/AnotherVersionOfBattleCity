package com.drozda.battlecity.playground;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 07.10.2015.
 */
public enum PlaygroundState {
    CREATED {
        @Override
        public List<PlaygroundState> getAllowedTransitions() {
            return asList(new PlaygroundState[]{INITIALIZING});
        }
    },
    INITIALIZING {
        @Override
        public List<PlaygroundState> getAllowedTransitions() {
            return asList(new PlaygroundState[]{ACTIVE});
        }
    },
    ACTIVE {
        @Override
        public List<PlaygroundState> getAllowedTransitions() {
            return asList(new PlaygroundState[]{PAUSED, STOPPED});
        }
    },
    PAUSED {
        @Override
        public List<PlaygroundState> getAllowedTransitions() {
            return asList(new PlaygroundState[]{ACTIVE, STOPPED});
        }
    },
    STOPPED {
        @Override
        public List<PlaygroundState> getAllowedTransitions() {
            return asList(new PlaygroundState[]{});
        }
    };
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

    public abstract List<PlaygroundState> getAllowedTransitions();

    public PlaygroundState tryTransition(PlaygroundState newState) {
        if (!canTransition(newState)) {
            throw new IllegalArgumentException("Cant make transition from " + this + "to " + newState);
        } else {
            return newState;
        }
    }
}
