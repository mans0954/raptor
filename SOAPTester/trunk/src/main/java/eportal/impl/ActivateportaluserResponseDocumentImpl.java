/*
 * An XML document type.
 * Localname: activateportaluserResponse
 * Namespace: eportal
 * Java type: eportal.ActivateportaluserResponseDocument
 *
 * Automatically generated - do not modify.
 */
package eportal.impl;
/**
 * A document containing one activateportaluserResponse(@eportal) element.
 *
 * This is a complex type.
 */
public class ActivateportaluserResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements eportal.ActivateportaluserResponseDocument
{
    
    public ActivateportaluserResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ACTIVATEPORTALUSERRESPONSE$0 = 
        new javax.xml.namespace.QName("eportal", "activateportaluserResponse");
    
    
    /**
     * Gets the "activateportaluserResponse" element
     */
    public eportal.ActivateportaluserResponseDocument.ActivateportaluserResponse getActivateportaluserResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            eportal.ActivateportaluserResponseDocument.ActivateportaluserResponse target = null;
            target = (eportal.ActivateportaluserResponseDocument.ActivateportaluserResponse)get_store().find_element_user(ACTIVATEPORTALUSERRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "activateportaluserResponse" element
     */
    public void setActivateportaluserResponse(eportal.ActivateportaluserResponseDocument.ActivateportaluserResponse activateportaluserResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            eportal.ActivateportaluserResponseDocument.ActivateportaluserResponse target = null;
            target = (eportal.ActivateportaluserResponseDocument.ActivateportaluserResponse)get_store().find_element_user(ACTIVATEPORTALUSERRESPONSE$0, 0);
            if (target == null)
            {
                target = (eportal.ActivateportaluserResponseDocument.ActivateportaluserResponse)get_store().add_element_user(ACTIVATEPORTALUSERRESPONSE$0);
            }
            target.set(activateportaluserResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "activateportaluserResponse" element
     */
    public eportal.ActivateportaluserResponseDocument.ActivateportaluserResponse addNewActivateportaluserResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            eportal.ActivateportaluserResponseDocument.ActivateportaluserResponse target = null;
            target = (eportal.ActivateportaluserResponseDocument.ActivateportaluserResponse)get_store().add_element_user(ACTIVATEPORTALUSERRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML activateportaluserResponse(@eportal).
     *
     * This is a complex type.
     */
    public static class ActivateportaluserResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements eportal.ActivateportaluserResponseDocument.ActivateportaluserResponse
    {
        
        public ActivateportaluserResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("", "return");
        
        
        /**
         * Gets the "return" element
         */
        public java.lang.String getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "return" element
         */
        public org.apache.xmlbeans.XmlString xgetReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
                return target;
            }
        }
        
        /**
         * Sets the "return" element
         */
        public void setReturn(java.lang.String xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RETURN$0);
                }
                target.setStringValue(xreturn);
            }
        }
        
        /**
         * Sets (as xml) the "return" element
         */
        public void xsetReturn(org.apache.xmlbeans.XmlString xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
    }
}
