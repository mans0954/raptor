/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.cardiff.model.event;

import uk.ac.cardiff.utility.EqualsUtil;
import uk.ac.cardiff.utility.HashCodeUtil;
import uk.ac.cardiff.utility.StringUtils;

/**
 *
 */
public class JuspAggregatedEvent extends AggregatedEvent {

    private String plId;

    private int jr1Downloads;

    private int jr1aDownloads;

    private int totalDownloads;

    public JuspAggregatedEvent() {

    }

    /**
     * Copy constructor.
     * 
     * @param event
     *            the event to copy
     */
    protected JuspAggregatedEvent(JuspAggregatedEvent event) {
        super(event);
        plId = event.getPlId();
        jr1Downloads = event.getJr1Downloads();
        jr1aDownloads = event.getJr1aDownloads();
        totalDownloads = event.getTotalDownloads();

    }

    /**
     * @return Returns the totalDownloads.
     */
    public Integer getTotalDownloads() {
        return totalDownloads;
    }

    /**
     * @param totalDownloads
     *            The totalDownloads to set.
     */
    public void setTotalDownloads(Integer totalDownloads) {
        this.totalDownloads = totalDownloads;
    }

    @Override
    public String toString() {
        return StringUtils.buildToString(this);
    }

    /**
     * @return Returns the jr1Downloads.
     */
    public Integer getJr1Downloads() {
        return jr1Downloads;
    }

    /**
     * @param jr1Downloads
     *            The jr1Downloads to set.
     */
    public void setJr1Downloads(Integer jr1Downloads) {
        this.jr1Downloads = jr1Downloads;
    }

    /**
     * @return Returns the jr1aDownloads.
     */
    public Integer getJr1aDownloads() {
        return jr1aDownloads;
    }

    /**
     * @param jr1aDownloads
     *            The jr1aDownloads to set.
     */
    public void setJr1aDownloads(Integer jr1aDownloads) {
        this.jr1aDownloads = jr1aDownloads;
    }

    /**
     * @return Returns the plId.
     */
    public String getPlId() {
        return plId;
    }

    /**
     * @param plId
     *            The plId to set.
     */
    public void setPlId(String plId) {
        this.plId = plId;
    }

    /**
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        JuspAggregatedEvent that = (JuspAggregatedEvent) obj;
        boolean areEqual = EqualsUtil.areEqual(this.getEventTimeMillis(), that.getEventTimeMillis()) && EqualsUtil.areEqual(this.getEventId(), that.getEventId())
                && EqualsUtil.areEqual(this.getServiceHost(), that.getServiceHost()) && EqualsUtil.areEqual(this.getResourceHost(), that.getResourceHost())
                && EqualsUtil.areEqual(this.getServiceId(), that.getServiceId()) && EqualsUtil.areEqual(this.getEventType(), that.getEventType())
                && EqualsUtil.areEqual(this.getResourceId(), that.getResourceId()) && EqualsUtil.areEqual(this.getJr1aDownloads(), that.getJr1aDownloads())
                && EqualsUtil.areEqual(this.getJr1Downloads(), that.getJr1Downloads()) && EqualsUtil.areEqual(this.getPlId(), that.getPlId())
                && EqualsUtil.areEqual(this.getTotalDownloads(), that.getTotalDownloads());

        return areEqual;
    }

    /**
     * For hibernate, so the hashcode can be persisted.
     * 
     * @return the hash code
     */
    public int getHashCode() {
        return hashCode();
    }

    /**
     * For hibernate, does nothing as the hascode is computed on the fly from the <code>hashCode</code> method.
     * 
     * @param hashCode
     *            the new hash code
     */
    public void setHashCode(int hashCode) {

    }

    /**
     * create a unique hash, with as uniform a distribution as possible.
     * 
     * @return the int
     */
    @Override
    public int hashCode() {
        int hash = HashCodeUtil.SEED;
        // all inherited fields are hashed here.
        hash = HashCodeUtil.hash(hash, getEventTimeMillis());
        hash = HashCodeUtil.hash(hash, getEventId());
        hash = HashCodeUtil.hash(hash, getServiceHost());
        hash = HashCodeUtil.hash(hash, getResourceHost());
        hash = HashCodeUtil.hash(hash, getPlId());
        hash = HashCodeUtil.hash(hash, getEventType());
        hash = HashCodeUtil.hash(hash, getServiceId());
        hash = HashCodeUtil.hash(hash, getResourceId());
        // all new fields part of the business key should be hashed below.
        hash = HashCodeUtil.hash(hash, getJr1aDownloads());
        hash = HashCodeUtil.hash(hash, getTotalDownloads());

        return hash;

    }

}
