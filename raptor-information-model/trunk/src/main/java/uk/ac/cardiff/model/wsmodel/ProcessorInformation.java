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
import java.util.ArrayList;
import java.util.List;

/**
 * Used to encapsulate processor information for sending between MUA and Web. Related closely to the MUA only {@link ProcessorTemplate}.
 * 
 */
public class ProcessorInformation implements Serializable {

    /** Generated Serial UID */
    private static final long serialVersionUID = 3004339123572624352L;

    /** canoncial name of the class type of this processor. Not a class type due to ageis binding. **/
    private String processorClass;

    private String friendlyName;

    private List<MethodParameter> methodParameters;

    public ProcessorInformation() {
        super();
    }

    /**
     * Copy Constructor.
     * 
     * @param info
     */
    public ProcessorInformation(ProcessorInformation info) {
        super();
        if (info != null) {
            processorClass = info.processorClass;
            friendlyName = info.friendlyName;
            if (info.getMethodParameters() != null) {
                methodParameters = new ArrayList<MethodParameter>();
                for (MethodParameter param : info.methodParameters) {
                    MethodParameter newParam = new MethodParameter(param);
                    methodParameters.add(newParam);
                }
            }
        }

    }

    /**
     * @param methodParameters
     *            the methodParameters to set
     */
    public void setMethodParameters(List<MethodParameter> methodParameters) {
        this.methodParameters = methodParameters;
    }

    /**
     * @return the methodParameters
     */
    public List<MethodParameter> getMethodParameters() {
        return methodParameters;
    }

    /**
     * @param processorClass
     *            the processorClass to set
     */
    public void setProcessorClass(String processorClass) {
        this.processorClass = processorClass;
    }

    /**
     * @return the processorClass
     */
    public String getProcessorClass() {
        return processorClass;
    }

    /**
     * @param friendlyName
     *            the friendlyName to set
     */
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    /**
     * @return the friendlyName
     */
    public String getFriendlyName() {
        return friendlyName;
    }

}
