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

package uk.ac.cardiff.raptormua.engine.runtimestatistics;

import org.joda.time.DateTime;

public class YesterdayStatisticsPeriod implements StatisticPeriod {

    public DateTime getStart() {
        DateTime now = new DateTime(System.currentTimeMillis());
        now = now.minusHours(now.getHourOfDay());
        now = now.minusMinutes(now.getMinuteOfHour());
        now = now.minusSeconds(now.getSecondOfMinute());
        now = now.minusMillis(now.getMillisOfSecond());
        now = now.minusDays(1);
        return now;
    }

    public DateTime getEnd() {
        DateTime now = new DateTime(System.currentTimeMillis());
        now = now.minusHours(now.getHourOfDay());
        now = now.minusMinutes(now.getMinuteOfHour());
        now = now.minusSeconds(now.getSecondOfMinute());
        now = now.minusMillis(now.getMillisOfSecond());
        return now;
    }

    public String getPeriodIdentifier() {
        return "yesterdayStatisticPeriod";
    }

}
