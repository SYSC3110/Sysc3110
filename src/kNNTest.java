//Tashfiq Akhand 101004428


import junit.framework.TestCase;
import junit.framework.*;

import static org.junit.Assert.*;


import org.junit.Test;
import java.util.ArrayList;

import org.junit.*;

public class kNNTest extends TestCase {
	private kNN k;
	
	private ArrayList<Obj> o;
	
	private int T; //nearest neighbors value
	
	@Before
	public void setUp() throws Exception{
		k = new kNN();
		o = new ArrayList<Obj>();
		T = 2;
	}
	
	@After
	public void tearDown() throws Exception{
		k = null;
	}
	
	
	@Test 
	public void TestGetkNN() {
	    Value coord1 = new Value("Coords", "array", "{12, 25}");
	    Value coord2 = new Value("Coords", "array", "{10, 50}");
	    Value coord3 = new Value("Coords", "array", "{30, 100}");
	    Value coord4 = new Value("Coords", "array", "{15, 20}");
	    
	    Value sqft1 = new Value("Sq. Ft.","number","1200");
	    Value sqft2 = new Value("Sq. Ft.","number","100");
	    Value sqft3 = new Value("Sq. Ft.","number","800");
	    Value sqft4 = new Value("Sq. Ft.","number","1000");
	    
	    Value age1 = new Value("Age", "String", "new");
	    Value age2 = new Value("Age", "String", "old");
	    Value age3 = new Value("Age", "String", "new");
	    Value age4 = new Value("Age", "String", "new");
	    
	    Value price1 = new Value("Price", "number", "500000");
	    Value price2 = new Value("Price", "number", "300000");
	    Value price3 = new Value("Price", "number", "400000");

	    ArrayList<Value> v1 = new ArrayList<Value>();
	    v1.add(coord1);
	    v1.add(sqft1);
	    v1.add(age1);
	    v1.add(price1);
	    
	    ArrayList<Value> v2 = new ArrayList<Value>();
	    v2.add(coord2);
	    v2.add(sqft2);
	    v2.add(age2);
	    v2.add(price2);
	    
	    ArrayList<Value> v3 = new ArrayList<Value>();
	    v3.add(coord3);
	    v3.add(sqft3);
	    v3.add(age3);
	    v3.add(price3);
	    
	    ArrayList<Value> v4= new ArrayList<Value>();
	    v4.add(coord4);
	    v4.add(sqft4);
	    v4.add(age4);
	    
	    Obj o1 = new Obj(1, v1);
	    Obj o2 = new Obj(2, v2);
	    Obj o3 = new Obj(3, v3);
	    Obj o4 = new Obj(4, v4);
	    
	    o.add(o1);
	    o.add(o2);
	    o.add(o3);
	    
	    Value a = k.getKNN(o4, o, T);
	    Value b = new Value("450000");
	    
	    assertEquals(a,b);
	}
	

	public static void main(String args[]) {
		junit.textui.TestRunner.run(kNNTest.class);
	}

}