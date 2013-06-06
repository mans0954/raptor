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
package uk.ac.cardiff.utility;


/**
 * Collected methods which allow easy implementation of <code>equals</code>. taken from http://www.javapractices.com/
 * 
 * Example use case in a class called Car:
 * 
 * <pre>
 * public boolean equals(Object aThat) {
 *     if (this == aThat)
 *         return true;
 *     if (!(aThat instanceof Car))
 *         return false;
 *     Car that = (Car) aThat;
 *     return EqualsUtil.areEqual(this.fName, that.fName) &amp;&amp; EqualsUtil.areEqual(this.fNumDoors, that.fNumDoors) &amp;&amp; EqualsUtil.areEqual(this.fGasMileage, that.fGasMileage)
 *             &amp;&amp; EqualsUtil.areEqual(this.fColor, that.fColor) &amp;&amp; Arrays.equals(this.fMaintenanceChecks, that.fMaintenanceChecks); // array!
 * }
 * </pre>
 * 
 * <em>Arrays are not handled by this class</em>. This is because the <code>Arrays.equals</code> methods should be used for array fields.
 */
public final class EqualsUtil {

    static public boolean areEqual(boolean aThis, boolean aThat) {
        return aThis == aThat;
    }

    static public boolean areEqual(char aThis, char aThat) {
        return aThis == aThat;
    }

    static public boolean areEqual(long aThis, long aThat) {
        /*
         * Implementation Note Note that byte, short, and int are handled by this method, through implicit conversion.
         */
        return aThis == aThat;
    }

    static public boolean areEqual(float aThis, float aThat) {
        return Float.floatToIntBits(aThis) == Float.floatToIntBits(aThat);
    }

    static public boolean areEqual(double aThis, double aThat) {
        return Double.doubleToLongBits(aThis) == Double.doubleToLongBits(aThat);
    }

    /**
     * Possibly-null object field.
     * 
     * Includes type-safe enumerations and collections, but does not include arrays. See class comment.
     */
    static public boolean areEqual(Object aThis, Object aThat) {
        // System.out.println("Object");
        return aThis == null ? aThat == null : aThis.equals(aThat);
    }
}
