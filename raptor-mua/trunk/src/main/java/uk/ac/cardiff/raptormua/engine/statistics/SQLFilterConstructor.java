package uk.ac.cardiff.raptormua.engine.statistics;

import uk.ac.cardiff.model.sql.ComparisonPredicate.CompOp;
import uk.ac.cardiff.model.sql.SQLFilter;
import uk.ac.cardiff.model.sql.SQLWhere;

/**
 * This is a experimental and not very well implemented  engine for constructing 
 * SQL filters (principaly where), for use by the statistics engine.
 * 
 * @author philsmart
 *
 */
public class SQLFilterConstructor {
	
	private SQLFilter sqlFilter;

	public SQLFilterConstructor(SQLFilter sqlFilter){
		this.sqlFilter = sqlFilter;
		
	}
	
	/**
	 * Perform the conversion of the <code>SQLFilter</code> into a string that
	 * can be inserted into an SQL expression.
	 * 
	 * @return
	 */
	public String convertFilterToString(){
		
		if (sqlFilter instanceof SQLWhere){
			return buildWhere((SQLWhere)sqlFilter);
		}
		
		return null;
		
	}
	
	private String buildWhere(SQLWhere sqlWhere){
		StringBuilder sql = new StringBuilder();
		
		CompOp comparisonOperator = sqlWhere.getBooleanExpression().getBooleanTerm().getBooleanFactor().getBooleanTest().getBooleanPrimary().getPredicate().getComparisonPredicate().getCompOp();
		String fieldName = sqlWhere.getBooleanExpression().getBooleanTerm().getBooleanFactor().getBooleanTest().getBooleanPrimary().getPredicate().getComparisonPredicate().getFieldName();
		String value = sqlWhere.getBooleanExpression().getBooleanTerm().getBooleanFactor().getBooleanTest().getBooleanPrimary().getPredicate().getComparisonPredicate().getValue();
		
		sql.append(fieldName);
		if (comparisonOperator==CompOp.EQUAL)sql.append("=");
		if (comparisonOperator==CompOp.NOTEQUAL)sql.append("!=");
		sql.append("'"+value+"'");
		
		if (sql.length()==0)return null;
		
		return sql.toString();
	
	
	}
	
	

}
