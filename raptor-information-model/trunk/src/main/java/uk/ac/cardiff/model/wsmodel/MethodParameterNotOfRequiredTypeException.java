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
