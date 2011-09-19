package uk.ac.cardiff.model.sql;

import java.io.Serializable;

/**
 * [  <predicate>    ::=
         <comparison predicate>
     |     <between predicate>
     |     <in predicate>
     |     <like predicate>
     |     <null predicate>
     |     <quantified comparison predicate>
     |     <exists predicate>
     |     <unique predicate>
     |     <match predicate>
     |     <overlaps predicate>
     |     <similar predicate>
     |     <distinct predicate>
     |     <type predicate>
     ]

   We only allow a simplified comparison predicate

 * @author philsmart
 *
 */
public class Predicate implements Serializable{

    /** Generated SerialUID */
    private static final long serialVersionUID = 4283734426422664602L;

    private ComparisonPredicate comparisonPredicate;

    public void setComparisonPredicate(ComparisonPredicate comparisonPredicate) {
	this.comparisonPredicate = comparisonPredicate;
    }

    public ComparisonPredicate getComparisonPredicate() {
	return comparisonPredicate;
    }
}
