package com.drozda.fx.dialog;

import impl.org.controlsfx.skin.AutoCompletePopup;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by GFH on 10.10.2015.
 */
public class CleanStyleHelper {
    private static final Logger log = LoggerFactory.getLogger(CleanStyleHelper.class);

    public static void cleanStyle(AutoCompletionBinding bindingForCleaning) {

        if (bindingForCleaning == null) {
            throw new NullPointerException("bindingForCleaning is null");
        }
        try {
            Field field = AutoCompletionBinding.class.getDeclaredField("autoCompletionPopup");

            field.setAccessible(true);
            AutoCompletePopup autoCompletePopup = (AutoCompletePopup) field.get(bindingForCleaning);
            autoCompletePopup.getStyleClass().clear();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}
