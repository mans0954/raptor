/*
 * An XML document type.
 * Localname: activateportaluser
 * Namespace: eportal
 * Java type: eportal.ActivateportaluserDocument
 *
 * Automatically generated - do not modify.
 */
package eportal;


/**
 * A document containing one activateportaluser(@eportal) element.
 *
 * This is a complex type.
 */
public interface ActivateportaluserDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ActivateportaluserDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s1DC1876CFE04559233FAB5E813A2655A").resolveHandle("activateportalusere974doctype");
    
    /**
     * Gets the "activateportaluser" element
     */
    eportal.ActivateportaluserDocument.Activateportaluser getActivateportaluser();
    
    /**
     * Sets the "activateportaluser" element
     */
    void setActivateportaluser(eportal.ActivateportaluserDocument.Activateportaluser activateportaluser);
    
    /**
     * Appends and returns a new empty "activateportaluser" element
     */
    eportal.ActivateportaluserDocument.Activateportaluser addNewActivateportaluser();
    
    /**
     * An XML activateportaluser(@eportal).
     *
     * This is a complex type.
     */
    public interface Activateportaluser extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Activateportaluser.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s1DC1876CFE04559233FAB5E813A2655A").resolveHandle("activateportaluser3d2eelemtype");
        
        /**
         * Gets the "pStudentid" element
         */
        java.lang.String getPStudentid();
        
        /**
         * Gets (as xml) the "pStudentid" element
         */
        org.apache.xmlbeans.XmlString xgetPStudentid();
        
        /**
         * Sets the "pStudentid" element
         */
        void setPStudentid(java.lang.String pStudentid);
        
        /**
         * Sets (as xml) the "pStudentid" element
         */
        void xsetPStudentid(org.apache.xmlbeans.XmlString pStudentid);
        
        /**
         * Gets the "pEmail" element
         */
        java.lang.String getPEmail();
        
        /**
         * Gets (as xml) the "pEmail" element
         */
        org.apache.xmlbeans.XmlString xgetPEmail();
        
        /**
         * Sets the "pEmail" element
         */
        void setPEmail(java.lang.String pEmail);
        
        /**
         * Sets (as xml) the "pEmail" element
         */
        void xsetPEmail(org.apache.xmlbeans.XmlString pEmail);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static eportal.ActivateportaluserDocument.Activateportaluser newInstance() {
              return (eportal.ActivateportaluserDocument.Activateportaluser) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static eportal.ActivateportaluserDocument.Activateportaluser newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (eportal.ActivateportaluserDocument.Activateportaluser) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static eportal.ActivateportaluserDocument newInstance() {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static eportal.ActivateportaluserDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static eportal.ActivateportaluserDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static eportal.ActivateportaluserDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static eportal.ActivateportaluserDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static eportal.ActivateportaluserDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static eportal.ActivateportaluserDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static eportal.ActivateportaluserDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static eportal.ActivateportaluserDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static eportal.ActivateportaluserDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static eportal.ActivateportaluserDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static eportal.ActivateportaluserDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static eportal.ActivateportaluserDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static eportal.ActivateportaluserDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static eportal.ActivateportaluserDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static eportal.ActivateportaluserDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static eportal.ActivateportaluserDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static eportal.ActivateportaluserDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (eportal.ActivateportaluserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
