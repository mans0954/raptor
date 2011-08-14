package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;

public class ProcessorInformation implements Serializable {

    /** Generated Serial UID */
    private static final long serialVersionUID = 3004339123572624352L;

    /** Bean name of the processor */
    private String beanName;

    /** Name (fully qualified) of this processors class **/
    private String className;

    /**
     * @param beanName
     *            the beanName to set
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    /**
     * @return the beanName
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * @param className
     *            the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

}
