package com.drozda.battlecity;

/**
 * Created by GFH on 02.10.2015.
 */
public class MailEnvelope<D extends Comparable, M> implements Comparable<MailEnvelope<D, M>> {
    protected D destination;
    protected M message;
    protected EnvelopeType envelopeType;

    @Override
    public int compareTo(MailEnvelope<D, M> o) {
        return this.envelopeType.compareTo(o.envelopeType);
    }


    public enum EnvelopeType {
        FOR_ALL,
        FOR_ONE,
        FOR_GROUP
    }
}
