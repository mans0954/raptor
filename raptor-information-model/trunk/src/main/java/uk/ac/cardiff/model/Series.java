/**
 *
 */
package uk.ac.cardiff.model;

import java.io.Serializable;

import uk.ac.cardiff.model.sql.ComparisonPredicate;
import uk.ac.cardiff.model.sql.SQLFilter;
import uk.ac.cardiff.model.sql.ComparisonPredicate.CompOp;

/**
 * @author philsmart
 *
 */
public class Series implements Serializable{


    /* Generated serialUID */
    private static final long serialVersionUID = 2842224280017951256L;
    
    /* The textual description of the series, as attached to the x-axis */
    private String seriesLabel;
    /* A formatted textual description of the series, typically formatted by the logic of the authentication statistic*/
    private String SeriesLabelFormatted;
    
    private ComparisonPredicate comparisonPredicate;
    
    public String constructComparisonAsSQL(){
	if (comparisonPredicate==null) return null;
	
	StringBuilder sql = new StringBuilder();
	sql.append(comparisonPredicate.getFieldName());
	if (comparisonPredicate.getCompOp()==CompOp.EQUAL)sql.append("=");
	if (comparisonPredicate.getCompOp()==CompOp.NOTEQUAL)sql.append("!=");
	sql.append("'"+comparisonPredicate.getValue()+"'");
	
	if (sql.length()==0)return null;
	
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




}
