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
package uk.ac.cardiff.raptor.parse.external.remote;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.parse.ParserException;
import uk.ac.cardiff.raptor.parse.external.file.format.Header;
import uk.ac.cardiff.raptor.parse.external.remote.format.JsonFormat;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 *
 */
public class JsonLogParser extends BaseWebServiceParser {

    /** class logger. */
    private final Logger log = LoggerFactory.getLogger(JsonLogParser.class);

    /**
     * format used to put elements from the return map into the expected class;
     */
    private JsonFormat jsonFormat;

    /**
     * URL of HTTP GET web services that returns a JSON string. Parameters required to correctly invoke the service
     * should be in the URL.
     */
    private URI webServiceLocation;

    /**
     * the parameters for this service.
     */
    private Map<String, String> queryParams;

    /** {@inheritDoc} */
    @Override
    public void parse() throws ParserException {
        log.info("Running remote Json Parser for URL [{}] with params [{}]", webServiceLocation, queryParams);

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();

        for (Map.Entry<String, String> queryParam : queryParams.entrySet()) {
            params.add(queryParam.getKey(), queryParam.getValue());
        }

        Client c = Client.create();
        WebResource r = c.resource(webServiceLocation);
        ClientResponse response =
                r.queryParams(params).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        String returnAsString = response.getEntity(String.class);
        log.debug("Remote Service Has Returned with HTTP Response [{}]", response.getStatus());

        ObjectMapper mapper = new ObjectMapper();
        try {

            Collection<Map<String, Object>> events =
                    mapper.readValue(returnAsString, new TypeReference<Collection<Map<String, Object>>>() {});

            for (Map<String, Object> event : events) {
                Event authE = (Event) this.createObject(eventType);
                populateEvent(authE, event);

                if (jsonFormat.isAddEventTimePerEvent()) {
                    authE.setEventTime(new DateTime());
                }

                // hack here for evaluation ---remove
                authE.setServiceId(queryParams.get("id"));

                eventHandler.addEvent(authE);

                log.debug("Has event [{}]", authE);
            }

        } catch (Throwable e) {
            log.error("Could not reconstruct events from JSON input string, no events will be stored", e);
        }

        log.debug("LogFileParser currently has: {} entries, latestEntry: {}", eventHandler.getNumberOfEvents(),
                eventHandler.getLatestEventTime());

    }

    /**
     * Creates and event from the given input string from the headers defined.
     * 
     * @param event
     * @return
     */
    private final void populateEvent(final Event event, final Map<String, Object> eventMap) {
        log.debug("Creating event from Map [{}]", eventMap);
        if (jsonFormat != null) {
            for (Header header : jsonFormat.getHeaders()) {
                Object valueAsObject = eventMap.get(header.getSourceFieldName());
                if (valueAsObject != null) {
                    log.trace("Value off json object is {} as {}", valueAsObject, valueAsObject.getClass());
                    // convert to generic string - should be from json anyway.
                    String value = valueAsObject.toString();

                    log.trace("Taking value [{}] off the json response from fieldName [{}]", value,
                            header.getSourceFieldName());

                    switch (header.getType()) {

                        case DATE:
                            addDate(value, header.getDateTimeFormat(), header.getTimeZone(), header.getFieldName(),
                                    event);
                            break;
                        case STRING:
                            addString(value, header.getFieldName(), event);
                            break;
                        case INTEGER:
                            addInteger(value, header.getFieldName(), event);
                            break;
                        case STRINGLIST:
                            addStringList(value, header.getFieldName(), event, header.getListDelimeter());
                            break;
                        case URL:
                            value = decode(value);
                            addString(value, header.getFieldName(), event);
                            break;
                    }
                }
            }
        }
    }

    /**
     * @return Returns the webServiceLocation.
     */
    public URI getWebServiceLocation() {
        return webServiceLocation;
    }

    /**
     * @param webServiceLocation The webServiceLocation to set.
     */
    public void setWebServiceLocation(URI webServiceLocation) {
        this.webServiceLocation = webServiceLocation;
    }

    /**
     * @return Returns the jsonFormat.
     */
    public JsonFormat getJsonFormat() {
        return jsonFormat;
    }

    /**
     * @param jsonFormat The jsonFormat to set.
     */
    public void setJsonFormat(JsonFormat jsonFormat) {
        this.jsonFormat = jsonFormat;
    }

    /**
     * @return Returns the queryParams.
     */
    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    /**
     * @param queryParams The queryParams to set.
     */
    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

}
