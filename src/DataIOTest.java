//Tashfiq Akhand 101004428

import static org.junit.Assert.*;

import junit.framework.TestCase;
import junit.framework.*;

import static org.junit.Assert.*;


import org.junit.Test;
import java.util.ArrayList;

import org.junit.*;
public class DataIOTest {
	
	private DataIO d;
	private String filename = "Tashfiq, Mustafa and Manel";

	@Before
	public void setUp() throws Exception {
		d = new DataIO();
	}

	@After
	public void tearDown() throws Exception {
		d = null;
	}
	
	@Test
	public void testModelFromFilename() {
		ArrayList<Obj> o = new ArrayList<Obj>();
		ArrayList<Value> v = new ArrayList<Value>();
		
		MLModel m = new MLModel(o, v);
		MLModel m2 = new MLModel(o, v);
		
		d.exportModelToFilename(filename, m);
		m2 = d.getModelFromFilename(filename);
		
		assertEquals(m, m2);
	}
	
//	public static void main(String args[]) {
//		junit.textui.TestRunner.run(DataIOTest.class);
//	}

}
