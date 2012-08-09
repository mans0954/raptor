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
 * Subset of intended BNF [<boolean value expression>    ::=
         <boolean term>
     |     <boolean value expression> OR <boolean term>]

 * @author philsmart
 *
 */
public class BooleanExpression implements Serializable{

    /** Generated SerialUID*/
    private static final long serialVersionUID = -7779020375150742717L;

    private BooleanTerm booleanTerm;

    public void setBooleanTerm(BooleanTerm booleanTerm) {
	this.booleanTerm = booleanTerm;
    }

    public BooleanTerm getBooleanTerm() {
	return booleanTerm;
    }


}
