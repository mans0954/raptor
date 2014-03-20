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
public class WugenEvent extends Event {

    private String wayflessUrl;

    private String target;

    private int quality;

    public WugenEvent() {

    }

    @Override
    public String toString() {
        return StringUtils.buildToString(this);
    }

    /**
     * Copy constructor.
     * 
     * @param event
     *            the event to copy
     */
    protected WugenEvent(WugenEvent event) {
        super(event);
        this.wayflessUrl = event.getWayflessUrl();
        this.target = event.getTarget();
        this.quality = event.getQuality();

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
        WugenEvent that = (WugenEvent) obj;
        boolean areEqual = EqualsUtil.areEqual(this.getEventTimeMillis(), that.getEventTimeMillis()) && EqualsUtil.areEqual(this.getEventId(), that.getEventId())
                && EqualsUtil.areEqual(this.getServiceHost(), that.getServiceHost()) && EqualsUtil.areEqual(this.getResourceHost(), that.getResourceHost())
                && EqualsUtil.areEqual(this.getServiceId(), that.getServiceId()) && EqualsUtil.areEqual(this.getEventType(), that.getEventType())
                && EqualsUtil.areEqual(this.getResourceId(), that.getResourceId()) && EqualsUtil.areEqual(this.getWayflessUrl(), that.getWayflessUrl())
                && EqualsUtil.areEqual(this.getTarget(), that.getTarget()) && EqualsUtil.areEqual(this.getQuality(), that.getQuality());
        return areEqual;
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
        hash = HashCodeUtil.hash(hash, getWayflessUrl());
        hash = HashCodeUtil.hash(hash, getEventId());
        hash = HashCodeUtil.hash(hash, getServiceHost());
        hash = HashCodeUtil.hash(hash, getResourceHost());
        hash = HashCodeUtil.hash(hash, getTarget());
        hash = HashCodeUtil.hash(hash, getEventType());
        hash = HashCodeUtil.hash(hash, getServiceId());
        hash = HashCodeUtil.hash(hash, getResourceId());
        // all new fields part of the business key should be hashed below.
        hash = HashCodeUtil.hash(hash, getQuality());

        return hash;

    }

    /**
     * @return Returns the wayflessUrl.
     */
    public String getWayflessUrl() {
        return wayflessUrl;
    }

    /**
     * @param wayflessUrl
     *            The wayflessUrl to set.
     */
    public void setWayflessUrl(String wayflessUrl) {
        this.wayflessUrl = wayflessUrl;
    }

    /**
     * @return Returns the target.
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target
     *            The target to set.
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return Returns the quality.
     */
    public Integer getQuality() {
        return quality;
    }

    /**
     * @param quality
     *            The quality to set.
     */
    public void setQuality(Integer quality) {
        this.quality = quality;
    }

}
