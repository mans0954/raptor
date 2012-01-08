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

package uk.ac.cardiff.raptormua.engine;

public class Version {

    /** Name of the mua. */
    private static final String NAME;

    /** mua version. */
    private static final String VERSION;

    /** mua major version number. */
    private static final int MAJOR_VERSION;

    /** mua minor version number. */
    private static final int MINOR_VERSION;

    /** mua micro version number. */
    private static final int MICRO_VERSION;

    /** Constructor. */
    public Version() {
    }

    /**
     * Gets the name of the mua.
     * 
     * @return name of the mua
     */
    public static String getName() {
        return NAME;
    }

    /**
     * Gets the version of the mua.
     * 
     * @return version of the mua
     */
    public static String getVersion() {
        return VERSION;
    }

    /**
     * Gets the major version number of the mua.
     * 
     * @return major version number of the mua
     */
    public static int getMajorVersion() {
        return MAJOR_VERSION;
    }

    /**
     * Gets the minor version number of the mua.
     * 
     * @return minor version number of the mua
     */
    public static int getMinorVersion() {
        return MINOR_VERSION;
    }

    /**
     * Gets the micro version number of the mua.
     * 
     * @return micro version number of the mua
     */
    public static int getMicroVersion() {
        return MICRO_VERSION;
    }

    static {
        Package pkg = Version.class.getPackage();
        NAME = pkg.getImplementationTitle().intern();
        VERSION = pkg.getImplementationVersion().intern();
        String[] versionParts = VERSION.split("\\.");
        MAJOR_VERSION = Integer.parseInt(versionParts[0]);
        MINOR_VERSION = Integer.parseInt(versionParts[1]);
        MICRO_VERSION = Integer.parseInt(versionParts[2].replace("-SNAPSHOT", ""));
    }
}
