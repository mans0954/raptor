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
/**
 *
 */

package uk.ac.cardiff.raptor.parse.external.file.format;

import java.util.Map;

/**
 * The Class Header.
 * 
 * @author philsmart
 */
public class Header {

    /**
     * The Enum Type.
     */
    public enum Type {
        /** The STRING. */
        STRING, /** The DATE. */
        DATE, /** The INTEGER. */
        INTEGER, /** The STRINGLIST. */
        STRINGLIST, /** The URL. */
        URL
    }

    /** The name of the field this header matches to in the internal <code>Event</code> model. */
    private String fieldName;

    /** The index in the currently tokenized line this header matches to. */
    private int fieldNo;

    /** Used to combined two fields together. */
    private int[] additionalFieldNos;

    /** The type of field, based on the Type enum. */
    private Type fieldType;

    /** A map of regular expressions and replacement values. */
    private Map<String, String> regexReplaceAll;

    /** A regular expression that matches to a substring, which if found is kept as the value for this header. */
    private String regexRetain;

    /** Whether the <code>regexRetain</code> should be case insensitive. Default is false */
    private boolean regexRetainCaseInsensitive;

    /**
     * If this header has a <code>DATE</code> type, this represents the format the value of the header should be parsed
     * with.
     */
    private String dateTimeFormat;

    /** Timezone information. */
    private String timeZone;

    /** The list delimeter. */
    private String listDelimeter;

    /**
     * Instantiates a new header.
     */
    public Header() {
        regexRetainCaseInsensitive = false;
    }

    /**
     * Sets the field name.
     * 
     * @param fieldName the new field name
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Gets the field name.
     * 
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the field no.
     * 
     * @param fieldNo the new field no
     */
    public void setFieldNo(int fieldNo) {
        this.fieldNo = fieldNo;
    }

    /**
     * Gets the field no.
     * 
     * @return the field no
     */
    public int getFieldNo() {
        return fieldNo;

    }

    /**
     * Sets the field type.
     * 
     * @param typeS the new field type
     */
    public void setFieldType(String typeS) {
        this.fieldType = Type.valueOf(typeS);
    }

    /**
     * returns a string, as Type is null during init from Spring which causes an error, so the below accessor should be
     * used.
     * 
     * @return the field type
     */
    public String getFieldType() {
        return fieldType.toString();
    }

    /**
     * USE this method to return the type.
     * 
     * @return the type
     */
    public Type getType() {
        return fieldType;
    }

    /**
     * Sets the date time format.
     * 
     * @param dateTimeFormat the new date time format
     */
    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    /**
     * Gets the date time format.
     * 
     * @return the date time format
     */
    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    /**
     * Sets the list delimeter.
     * 
     * @param listDelimeter the listDelimeter to set
     */
    public void setListDelimeter(String listDelimeter) {
        this.listDelimeter = listDelimeter;
    }

    /**
     * Gets the list delimeter.
     * 
     * @return the listDelimeter
     */
    public String getListDelimeter() {
        return listDelimeter;
    }

    /**
     * Sets the time zone.
     * 
     * @param timeZone the new time zone
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Gets the timezone, if none specified, UTC is the default.
     * 
     * @return the time zone
     */
    public String getTimeZone() {
        if (timeZone == null)
            return "UTC";
        return timeZone;
    }

    /**
     * Sets the regex replace all.
     * 
     * @param regexReplaceAll the regexReplaceAll to set
     */
    public void setRegexReplaceAll(Map<String, String> regexReplaceAll) {
        this.regexReplaceAll = regexReplaceAll;
    }

    /**
     * Gets the regex replace all.
     * 
     * @return the regexReplaceAll
     */
    public Map<String, String> getRegexReplaceAll() {
        return regexReplaceAll;
    }

    /**
     * Sets the regex retain.
     * 
     * @param regexRetain the regexRetain to set
     */
    public void setRegexRetain(String regexRetain) {
        this.regexRetain = regexRetain;
    }

    /**
     * Gets the regex retain.
     * 
     * @return the regexRetain
     */
    public String getRegexRetain() {
        return regexRetain;
    }

    /**
     * Sets the additional field nos.
     * 
     * @param additionalFieldNos the additionalFieldNos to set
     */
    public void setAdditionalFieldNos(int[] additionalFieldNos) {
        this.additionalFieldNos = additionalFieldNos;
    }

    /**
     * Gets the additional field nos.
     * 
     * @return the additionalFieldNos
     */
    public int[] getAdditionalFieldNos() {
        return additionalFieldNos;
    }

    /**
     * Sets the regex retain case insensitive.
     * 
     * @param regexRetainCaseInsensitive the new regex retain case insensitive
     */
    public void setRegexRetainCaseInsensitive(boolean regexRetainCaseInsensitive) {
        this.regexRetainCaseInsensitive = regexRetainCaseInsensitive;
    }

    /**
     * Checks if is regex retain case insensitive.
     * 
     * @return true, if is regex retain case insensitive
     */
    public boolean isRegexRetainCaseInsensitive() {
        return regexRetainCaseInsensitive;
    }

}
