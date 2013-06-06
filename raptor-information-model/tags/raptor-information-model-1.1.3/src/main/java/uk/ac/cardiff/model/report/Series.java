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
package uk.ac.cardiff.model.report;

import java.io.Serializable;

import uk.ac.cardiff.model.sql.ComparisonPredicate;
import uk.ac.cardiff.model.sql.ComparisonPredicate.CompOp;

/**
 * @author philsmart
 * 
 */
public class Series implements Serializable {

    /** Generated serialUID */
    private static final long serialVersionUID = 2842224280017951256L;

    /** The textual description of the series, as attached to the x-axis */
    private String seriesLabel;
    /** A formatted textual description of the series, typically formatted by the logic of the authentication statistic */
    private String SeriesLabelFormatted;
    /** a comparison predicate that forms the where clause */
    private ComparisonPredicate comparisonPredicate;

    /**
     * setting dummy series to true will not prevent the statistics handler from running the series. Hence the series is only used for the label which will
     * later be used to construct the graph model.
     */
    private boolean dummySeries = false;

    public Series() {
        super();
    }

    /**
     * Copy Constructor.
     * 
     * @param serie
     */
    public Series(Series series) {
        if (series != null) {
            seriesLabel = series.seriesLabel;
            SeriesLabelFormatted = series.SeriesLabelFormatted;
            dummySeries = series.dummySeries;
            if (series.comparisonPredicate != null) {
                comparisonPredicate = new ComparisonPredicate(series.comparisonPredicate);
            }
        }
    }

    public String computeComparisonAsSQL() {
        if (comparisonPredicate == null)
            return null;

        StringBuilder sql = new StringBuilder();
        sql.append(comparisonPredicate.getFieldName());
        if (comparisonPredicate.getCompOp() == CompOp.EQUAL)
            sql.append("=");
        if (comparisonPredicate.getCompOp() == CompOp.NOT_EQUAL)
            sql.append("!=");
        sql.append("'" + comparisonPredicate.getValue() + "'");

        if (sql.length() == 0)
            return null;

        return sql.toString();
    }

    public void setSeriesLabel(String seriesLabel) {
        this.seriesLabel = seriesLabel;
    }

    public String getSeriesLabel() {
        return seriesLabel;
    }

    public void setSeriesLabelFormatted(String seriesLabelFormatted) {
        SeriesLabelFormatted = seriesLabelFormatted;
    }

    public String getSeriesLabelFormatted() {
        return SeriesLabelFormatted;
    }

    public void setComparisonPredicate(ComparisonPredicate comparisonPredicate) {
        this.comparisonPredicate = comparisonPredicate;
    }

    public ComparisonPredicate getComparisonPredicate() {
        return comparisonPredicate;
    }

    /**
     * @return Returns the dummySeries.
     */
    public boolean isDummySeries() {
        return dummySeries;
    }

    /**
     * @param dummySeries
     *            The dummySeries to set.
     */
    public void setDummySeries(boolean dummySeries) {
        this.dummySeries = dummySeries;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Series [seriesLabel=");
        builder.append(seriesLabel);
        builder.append(", SeriesLabelFormatted=");
        builder.append(SeriesLabelFormatted);
        builder.append(", comparisonPredicate=");
        builder.append(comparisonPredicate);
        builder.append(", dummySeries=");
        builder.append(dummySeries);
        builder.append("]");
        return builder.toString();
    }

}
