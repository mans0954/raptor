
package uk.ac.cardiff.raptormua.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import uk.ac.cardiff.raptormua.engine.Version;

@ManagedResource(objectName = "uk.ac.cardiff.raptor:name=muaDescriptor", description = "MUA Information MBean")
public class MuaDescriptorJmx {

    @ManagedAttribute
    public String getVersion() {
        return Version.getMajorVersion() + "." + Version.getMinorVersion() + "." + Version.getMicroVersion();
    }

}
