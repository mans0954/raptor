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
/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author philsmart
 *
 */
public class ManyRow<T> implements Serializable {

    static Logger log = LoggerFactory.getLogger(ManyRow.class);

    private String groupLabel;
    private List<T> values;


    public void setGroupLabel(String groupLabel) {
	this.groupLabel = groupLabel;
    }
    public String getGroupLabel() {
	return groupLabel;
    }
    public void setValues(List<T> values) {
	this.values = values;
    }
    public List<T> getValues() {
	return values;
    }

    public void addValue(T value){
	if (values==null) values = new ArrayList<T>();
	values.add(value);
    }



}
