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
package main.uk.ac.cf.dao.external.file;

import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * @author philsmart
 *
 */
public class LogFileParserTest {


    /**
     * This test is not a unit test, it is not an integration test, indeed its not much of a test
     */
    @Test
    public void testDateParser(){
	String value = "20100328T010234";
	DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	dtf = dtf.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("UTC")));
	DateTime dt = dtf.parseDateTime(value);
	System.out.println(dt);
    }

}
