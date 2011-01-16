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
package uk.ac.cardiff.raptor.raptorica.dao.external;

import java.util.List;
import java.util.Set;

import uk.ac.cardiff.raptor.raptorica.dao.RawData;
import uk.ac.cardiff.raptor.raptorica.model.ExclusionList;
import uk.ac.cardiff.raptor.raptorica.model.InclusionList;

public abstract class AuthenticationInput extends RawData{


    private ExclusionList exclusionList;
    private InclusionList inclusionList;


    public Set getAuthentications() {
	return getEntryHandler().getEntries();

    }

    public void setExclusionList(ExclusionList exclusionList) {
	this.exclusionList = exclusionList;
    }

    public ExclusionList getExclusionList() {
	return exclusionList;
    }


    public void setInclusionList(InclusionList inclusionList) {
	this.inclusionList = inclusionList;
    }


    public InclusionList getInclusionList() {
	return inclusionList;
    }





}
