package com.drozda.battlecity.appflow;


import com.benjiweber.statemachine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GFH on 17.09.2015.
 */
public interface AppState extends State<AppState> {
    static final Logger log = LoggerFactory.getLogger(AppState.class);

    @Override
    default void beforeTransition(AppState from) {
        log.info("i was in " + getClass().getSimpleName());
    }

    @Override
    default void afterTransition(AppState from) {
        log.info("now im in " + getClass().getSimpleName());
    }

}
