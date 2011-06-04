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
		if (comparisonOperator==CompOp.NOT_EQUAL)sql.append("!=");
		sql.append("'"+value+"'");

		if (sql.length()==0)return null;

		return sql.toString();


	}



}
