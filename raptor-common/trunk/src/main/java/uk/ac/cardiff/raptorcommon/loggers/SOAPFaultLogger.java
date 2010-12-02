/**
 *
 */
package uk.ac.cardiff.raptorcommon.loggers;

import org.apache.cxf.message.Message;
import org.apache.log4j.Logger;



/**
 * @author philsmart
 *
 */
public class SOAPFaultLogger implements org.apache.cxf.logging.FaultListener{

    static Logger log = Logger.getLogger(SOAPFaultLogger.class);

    /* (non-Javadoc)
     * @see org.apache.cxf.logging.FaultListener#faultOccurred(java.lang.Exception, java.lang.String, org.apache.cxf.message.Message)
     */
    public boolean faultOccurred(Exception arg0, String arg1, Message arg2) {
	log.debug("Fault in the apache CXF stack");
	return false;
    }



}
