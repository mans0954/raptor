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
package uk.ac.cardiff.model.sql;

import java.io.Serializable;

/**
 * [<boolean factor>    ::=   [ NOT ] <boolean test>]
 * @author philsmart
 *
 */
public class BooleanFactor implements Serializable{

    /** Generated SerialUID*/
    private static final long serialVersionUID = 3862662680037988213L;

    private boolean not;
    private BooleanTest booleanTest;

    public void setNot(boolean not) {
	this.not = not;
    }
    public boolean isNot() {
	return not;
    }
    public void setBooleanTest(BooleanTest booleanTest) {
	this.booleanTest = booleanTest;
    }
    public BooleanTest getBooleanTest() {
	return booleanTest;
    }


}
