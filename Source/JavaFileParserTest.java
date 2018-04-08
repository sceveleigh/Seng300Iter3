import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;

/**
*
* Tests fileParser and TypeFinderVisitor to ensure proper parsing and recognition
* of data types
*
*/

public class JavaFileParserTest {
	
	private static String BASEDIR = "C:\\Users\\Sarah\\SENG300";
	private JavaFileParser parser;
		
	@Before
	public void setup() {
		parser = new JavaFileParser();
	}
	
	
	public void annotationCounts() throws IllegalArgumentException, IOException{
		String[] names = {"qualifiedType.java"};
		String targetType = "SomeAnnotation";
		
		HashMap<String, int[]> hMap = new HashMap<String, int[]>();
		hMap = parser.parse(names, targetType);
		int[] counts = hMap.get(targetType);
		assertEquals(1,counts[0]);
		
		counts = hMap.get("@Override");
		assertEquals(1,counts[1]);
		
	}
	

	public void primitiveTypeCounts() throws IllegalArgumentException, IOException{
		
		String sourcePath = BASEDIR + File.separator + "tests" + File.separator +"GoodExtensions";
		String targetType = "int";
		String[] names = {"A.java", "B.java","C.java", "D.java","E.java"};
	
		HashMap<String, int[]> hMap = new HashMap<String, int[]>();
		hMap = parser.parse(names , sourcePath);
		int[] counts = hMap.get(targetType);
		
		assertEquals(1, counts[0]);
	}
	
	public void simpleTypeCounts() throws IllegalArgumentException, IOException{
		String[] names = { "A.java", "B.java" };
		String sourcePath = BASEDIR + File.separator + "tests" + File.separator + "GoodExtensions";
		String targetType = "A";
		
		HashMap<String, int[]> hMap = new HashMap<String, int[]>();
		hMap = parser.parse(names, sourcePath);
		int[] counts = hMap.get(targetType);
		
		assertEquals(1, counts[0]);
		assertEquals(4, counts[1]);
	}
	
	public void enumCounts() throws IllegalArgumentException, IOException{
		String[] names = { "A.java", "B.java", "C.java", "D.java", "E.java" };
		String sourcePath = BASEDIR + File.separator + "tests" + File.separator + "GoodExtensions";
		String targetType = "e";
		
		HashMap<String, int[]> hMap = new HashMap<String, int[]>();
		hMap = parser.parse(names, sourcePath);
		int[] counts = hMap.get(targetType);
		
		assertEquals(1, counts[0]);
	}
	

}