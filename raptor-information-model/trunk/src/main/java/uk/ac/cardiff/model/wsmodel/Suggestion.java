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
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;

public class Suggestion  implements Serializable{
    
    /** Generate Serial UID */
    private static final long serialVersionUID = 6492859645919853121L;

    /** The value of the base type of this suggestions e.g. class type*/
    private String base;
    
    /** The value of this suggestion */
    private String value;
    
    public Suggestion(String base, String value){
        this.base = base;
        this.value = value;
    }

    /**
     * Default constructor
     */
    public Suggestion(){
        
    }
    
    public void setBase(String base) {
        this.base = base;
    }

    public String getBase() {
        return base;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass() + "@[");
        builder.append(base); builder.append(":"); builder.append(value);        
        builder.append("]");
        return builder.toString();
    }
    

}
