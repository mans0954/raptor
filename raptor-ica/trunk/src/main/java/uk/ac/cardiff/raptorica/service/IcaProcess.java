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

package uk.ac.cardiff.raptorica.service;

public interface IcaProcess {

    /**
     * Instructs this ICA to capture events from all registered data sources.
     */
    public void capture();

    /**
     * Instructs this ICA to release events using the configured client.
     */
    public void release();

    /**
     * Instructs this ICA to remove any events that have already been processed and sent through the configured client.
     */
    public void garbageCollect();

}
