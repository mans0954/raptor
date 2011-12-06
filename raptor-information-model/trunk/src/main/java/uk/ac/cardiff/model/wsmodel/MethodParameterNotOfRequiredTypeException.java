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

public class MethodParameterNotOfRequiredTypeException extends MethodParameterException {

    /**
     * Generated serialUID.
     */
    private static final long serialVersionUID = 4981036423065460219L;

    /** The name of the instance that was of the wrong type */
    private String parameterName;

    /** The required type */
    private Class requiredType;

    /** The offending type */
    private Class actualType;

    /**
     * Create a new BeanNotOfRequiredTypeException.
     * 
     * @param beanName
     *            the name of the bean requested
     * @param requiredType
     *            the required type
     * @param actualType
     *            the actual type returned, which did not match the expected type
     */
    public MethodParameterNotOfRequiredTypeException(String parameterName, Class requiredType, Class actualType) {
        super("Parameter named '" + parameterName + "' must be of type [" + requiredType.getName() + "], but was actually of type [" + actualType.getName() + "]");
        this.parameterName = parameterName;
        this.requiredType = requiredType;
        this.actualType = actualType;
    }

    /**
     * Return the name of the instance that was of the wrong type.
     */
    public String getParameterName() {
        return this.parameterName;
    }

    /**
     * Return the expected type for the bean.
     */
    public Class getRequiredType() {
        return this.requiredType;
    }

    /**
     * Return the actual type of the instance found.
     */
    public Class getActualType() {
        return this.actualType;
    }

}
