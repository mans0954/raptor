package uk.ac.cardiff.raptormua.engine.statistics;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.cardiff.raptormua.runtimeutils.ReflectionHelper;

public class ReflectionHelperTest {
	
	@Test
	public void testFindClassForField(){		
			String classForField = ReflectionHelper.findEntrySubclassForMethod("principleName");
			System.out.println("ShibbolethEntry = "+classForField);
			equals(classForField.equals("ShibbolethEntry"));
			
			classForField = ReflectionHelper.findEntrySubclassForMethod("requestHost");
			System.out.println("Entry = "+classForField);
			equals(classForField.equals("Entry"));
			
			
	}

}
