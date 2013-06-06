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
 * [  <predicate>    ::=
         <comparison predicate>
     |     <between predicate>
     |     <in predicate>
     |     <like predicate>
     |     <null predicate>
     |     <quantified comparison predicate>
     |     <exists predicate>
     |     <unique predicate>
     |     <match predicate>
     |     <overlaps predicate>
     |     <similar predicate>
     |     <distinct predicate>
     |     <type predicate>
     ]

   We only allow a simplified comparison predicate

 * @author philsmart
 *
 */
public class Predicate implements Serializable{

    /** Generated SerialUID */
    private static final long serialVersionUID = 4283734426422664602L;

    private ComparisonPredicate comparisonPredicate;

    public void setComparisonPredicate(ComparisonPredicate comparisonPredicate) {
	this.comparisonPredicate = comparisonPredicate;
    }

    public ComparisonPredicate getComparisonPredicate() {
	return comparisonPredicate;
    }
}
