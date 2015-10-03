package com.drozda.battlecity.manager;

import com.drozda.battlecity.MailEnvelope;
import com.drozda.battlecity.interfaces.CanReceiveMail;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.WeakHashMap;

/**
 * Created by GFH on 02.10.2015.
 */
public class MailManager {
    private static final Logger log = LoggerFactory.getLogger(MailManager.class);

    Map<String, CanReceiveMail> addressBook = new WeakHashMap<>();

    PriorityQueue<MailEnvelope<String, Object>> messages = new PriorityQueue<>();

    public void addObservableList(ObservableList<CanReceiveMail> mailObservableList) {
        if (mailObservableList == null) {
            log.error("addObservableList mailObservableList is null");
            throw new NullPointerException("addObservableList mailObservableList is null");
        }
        mailObservableList.addListener(new ListChangeListener<CanReceiveMail>() {
                                           @Override
                                           public void onChanged(Change<? extends CanReceiveMail> c) {
                                               while (c.next()) {
                                                   if (c.wasPermutated()) {
                                                       for (int i = c.getFrom(); i < c.getTo(); ++i) {
                                                           //permutate
                                                       }
                                                   } else if (c.wasUpdated()) {
                                                       //update item
                                                   } else {
                                                       for (CanReceiveMail remitem : c.getRemoved()) {
                                                           addressBook.remove(remitem.getAddress());
                                                           log.debug(remitem.getAddress() + " removed from " +
                                                                   "addressbook");
                                                       }
                                                       for (CanReceiveMail additem : c.getAddedSubList()) {
                                                           addressBook.put(additem.getAddress(), additem);
                                                           log.debug(additem.getAddress() + " added to addressbok");
                                                       }
                                                   }
                                               }
                                           }
                                       }
        );
    }

}
