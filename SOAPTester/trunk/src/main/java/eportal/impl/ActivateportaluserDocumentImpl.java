/*
 * An XML document type.
 * Localname: activateportaluser
 * Namespace: eportal
 * Java type: eportal.ActivateportaluserDocument
 *
 * Automatically generated - do not modify.
 */
package eportal.impl;
/**
 * A document containing one activateportaluser(@eportal) element.
 *
 * This is a complex type.
 */
public class ActivateportaluserDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements eportal.ActivateportaluserDocument
{
    
    public ActivateportaluserDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ACTIVATEPORTALUSER$0 = 
        new javax.xml.namespace.QName("eportal", "activateportaluser");
    
    
    /**
     * Gets the "activateportaluser" element
     */
    public eportal.ActivateportaluserDocument.Activateportaluser getActivateportaluser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            eportal.ActivateportaluserDocument.Activateportaluser target = null;
            target = (eportal.ActivateportaluserDocument.Activateportaluser)get_store().find_element_user(ACTIVATEPORTALUSER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "activateportaluser" element
     */
    public void setActivateportaluser(eportal.ActivateportaluserDocument.Activateportaluser activateportaluser)
    {
        synchronized (monitor())
        {
            check_orphaned();
            eportal.ActivateportaluserDocument.Activateportaluser target = null;
            target = (eportal.ActivateportaluserDocument.Activateportaluser)get_store().find_element_user(ACTIVATEPORTALUSER$0, 0);
            if (target == null)
            {
                target = (eportal.ActivateportaluserDocument.Activateportaluser)get_store().add_element_user(ACTIVATEPORTALUSER$0);
            }
            target.set(activateportaluser);
        }
    }
    
    /**
     * Appends and returns a new empty "activateportaluser" element
     */
    public eportal.ActivateportaluserDocument.Activateportaluser addNewActivateportaluser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            eportal.ActivateportaluserDocument.Activateportaluser target = null;
            target = (eportal.ActivateportaluserDocument.Activateportaluser)get_store().add_element_user(ACTIVATEPORTALUSER$0);
            return target;
        }
    }
    /**
     * An XML activateportaluser(@eportal).
     *
     * This is a complex type.
     */
    public static class ActivateportaluserImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements eportal.ActivateportaluserDocument.Activateportaluser
    {
        
        public ActivateportaluserImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName PSTUDENTID$0 = 
            new javax.xml.namespace.QName("", "pStudentid");
        private static final javax.xml.namespace.QName PEMAIL$2 = 
            new javax.xml.namespace.QName("", "pEmail");
        
        
        /**
         * Gets the "pStudentid" element
         */
        public java.lang.String getPStudentid()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PSTUDENTID$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "pStudentid" element
         */
        public org.apache.xmlbeans.XmlString xgetPStudentid()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PSTUDENTID$0, 0);
                return target;
            }
        }
        
        /**
         * Sets the "pStudentid" element
         */
        public void setPStudentid(java.lang.String pStudentid)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PSTUDENTID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PSTUDENTID$0);
                }
                target.setStringValue(pStudentid);
            }
        }
        
        /**
         * Sets (as xml) the "pStudentid" element
         */
        public void xsetPStudentid(org.apache.xmlbeans.XmlString pStudentid)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PSTUDENTID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PSTUDENTID$0);
                }
                target.set(pStudentid);
            }
        }
        
        /**
         * Gets the "pEmail" element
         */
        public java.lang.String getPEmail()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PEMAIL$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "pEmail" element
         */
        public org.apache.xmlbeans.XmlString xgetPEmail()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PEMAIL$2, 0);
                return target;
            }
        }
        
        /**
         * Sets the "pEmail" element
         */
        public void setPEmail(java.lang.String pEmail)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PEMAIL$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PEMAIL$2);
                }
                target.setStringValue(pEmail);
            }
        }
        
        /**
         * Sets (as xml) the "pEmail" element
         */
        public void xsetPEmail(org.apache.xmlbeans.XmlString pEmail)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PEMAIL$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PEMAIL$2);
                }
                target.set(pEmail);
            }
        }
    }
}
