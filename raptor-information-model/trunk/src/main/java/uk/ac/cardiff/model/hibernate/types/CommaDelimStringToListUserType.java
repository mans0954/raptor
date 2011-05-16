package uk.ac.cardiff.model.hibernate.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

public class CommaDelimStringToListUserType implements UserType {

	private static final int[] SQL_TYPES = { Types.VARCHAR };
	private static final String delimiter = ",";

	@Override
	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException {
		return null;
	}


	public Object deepCopy(Object arg0) throws HibernateException {
		return arg0;
	}


	public Serializable disassemble(Object arg0) throws HibernateException {
		return null;
	}


	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		} else if (x == null || y == null) {
			return false;
		} else {
			return x.equals(y);
		}

	}


	public int hashCode(Object arg0) throws HibernateException {
		return 0;
	}


	public boolean isMutable() {
		return false;
	}


	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
			throws HibernateException, SQLException {
	    	String[] result = null;
		String value = resultSet.getString(names[0]);
		if (!resultSet.wasNull()) {
			result = value == "" ? null : convertToArray(value);
		}
		return result;
	}

	private String[] convertToArray(String value){
		List<String> values = new ArrayList<String>();
		String[] splitValue = value.split(delimiter);
		for (String v : splitValue)values.add(v);
		return values.toArray(new String[0]);
	}


	public void nullSafeSet(PreparedStatement statement, Object value, int index)
			throws HibernateException, SQLException {
		if (value == null) {
			statement.setInt(index, 0);
		} else {
			String[] toDelim = (String[])value;
			String combined ="";
		    for (int i=0; i< toDelim.length;i++){
		    	combined+=toDelim[i];
		    	if (i < toDelim.length-1)combined+=delimiter;
		    }
			statement.setString(index, combined);
		}

	}


	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
		return null;
	}


	public Class returnedClass() {
		return List.class;
	}


	public int[] sqlTypes() {
		return SQL_TYPES;
	}

}
