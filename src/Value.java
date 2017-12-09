/**
 * made by Mustafa
 */
import java.io.Serializable;
import java.util.ArrayList;

public class Value implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5704088414948951112L;
	private Object value;
	private String type;
	private String description;
	private String metric;

	/**
	 * Constructor of object Value.
	 * @param value the value to be stored
	 */
	public Value(Object value){
		if(value instanceof String)
			this.type = "String";
		else if(value instanceof Double)
			this.type = "number";
		else if(value instanceof Boolean)
			this.type = "bool";
		else if(value instanceof ArrayList)
			this.type = "Composite";
		this.value = value;;
	}

	public Value(String description, String type,String metric) {
		this.description = description;
		this.type = type;
		this.metric = metric;
	}

	/**
	 * resets the value of this object.
	 */
	public void reset(){
		switch(type){
		case "number":
			value = 0.0;
			break;
		case "String":
			value = "";
			break;
		case "Composite":
			@SuppressWarnings("unchecked")
			ArrayList<Value> objects = (ArrayList<Value>) value;
			objects.clear();
			break;
		case "bool":
			value = false;
			break;
		}
	}
	/**
	 * Adds a value to this object
	 * @param val value to be added
	 */
	public void add(Object val){
		//	System.out.println("adding "+value+" with "+val);
		if(this.value == null || val == null){
			return;
		}
		switch(type){
		case "number":
			value = (double)value + (double)val;
			break;
		case "String":
			value = (String)value + (String)val;
			break;
		case "Composite":
			@SuppressWarnings("unchecked")
			ArrayList<Value> objects = (ArrayList<Value>) value;
			for(Value val1:objects){
				val1.add(val);
			}
			break;
		case "bool":
			break;
		case "Coordinates":
			double[] coords = (double[]) value;
			double[] coords1 = (double[]) val;
			//double[] vals = {Double.parseDouble(splitArr[0].substring(1)),Double.parseDouble(splitArr[1].substring(0, splitArr[1].length()-1))};
		    coords[0]+=coords1[0];
		    coords[1]+=coords1[1];
		    value = coords;
		    break;
		}
	}
	/**
	 * Returns the diff between the input and this object
	 * @param val value to be compared to
	 * @return difference between values
	 */
	public double getDifference(Value val){
		if(this.value == null || val == null || val.getValue() == null || val.getType()!=this.type){
			return 0;
		}
		double returnVal = 0;
		switch(type){
		case "number":
			switch(metric){
			case "Subtraction":
				returnVal = (double) val.getValue() - (double) this.value;
				break;
			}
			break;
		case "String":
			switch(metric){
			case "Lexographical comparison":
				String temp = (String) val.getValue();
				returnVal = temp.compareTo((String)this.value);
				break;
			}
			break;
		case "bool":
			returnVal = (boolean) val.getValue() == (boolean) this.value ? 0 : 1;
		case "Composite":
			@SuppressWarnings("unchecked")
			ArrayList<Value> vals = (ArrayList<Value>) val.getValue();
			@SuppressWarnings("unchecked")
			ArrayList<Value> thisVals = (ArrayList<Value>) this.value;
			for(int i = 0; i < thisVals.size(); i++){
				returnVal += thisVals.get(i).getDifference(vals.get(i));
			}
			break;
		case "Coordinates":
			switch(metric){
			case "Euclidean Distance":
				double[] vals1 = (double[]) value;
				double[] vals2 = (double[]) val.getValue();
				double ret = Math.pow(vals1[0]-vals2[0], 2) + Math.pow(vals1[1]-vals2[1], 2);
				ret = Math.sqrt(ret);
				returnVal = ret;
				break;
			case "Sum of Squared Difference":
				vals1 = (double[]) value;
				vals2 = (double[]) val.getValue();
				ret = Math.pow(vals1[0]-vals2[0], 2) + Math.pow(vals1[1]-vals2[1], 2);
				returnVal = ret;
				break;
			}
			break;
		}
		return Math.abs(returnVal);
	}
	/**
	 * Multiplies this value with val
	 * @param val value to multiply with
	 */
	public void multiply(Object val){
		//	System.out.println("multiplying "+value+" with "+val);
		//if(((String)val).substring(0, 5).equals("array")){
		
		//}
		if(this.value == null || val == null) return;
		switch(type){
		case "number":
			value = (double)value * (double)val;
			break;
		case "String":
			break;
		case "Composite":
			@SuppressWarnings("unchecked") ArrayList<Value> vals = (ArrayList<Value>) val;
			@SuppressWarnings("unchecked") ArrayList<Value> thisArr = (ArrayList<Value>) this.value;
			for(int i = 0; i < vals.size(); i++){
				thisArr.get(i).multiply(vals.get(i).getValue());
			}
			break;
		case "Coordinates":
			double[] dubarr = (double[])this.value;
			double vals1 = (double) val;
			for(int i = 0; i < dubarr.length;i++){
				dubarr[i]*=vals1;
			}
			this.value = dubarr;
			break;
		}
	}
	/**
	 * returns type of value
	 * @return type of the vlaue
	 */
	public String getType() {
		return type;
	}
	/**
	 * 	sets type of the value (string, number, array)
	 * @param type type of the value
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * returns the value held
	 * @return value stored
	 */
	public Object getValue() {
		return value;
	}
	/**
	 * sets a composite value object
	 * @param values
	 */
	public void setValue(ArrayList<Value> values){
		this.value = values;
	}
	/**
	 * sets the value held
	 * @param value value to store
	 */
	public void setValue(String value) {
		if(value.length()==0){
			this.value = new Value(null);
			return;
		}
		switch(type){
		case "number":
			this.value = Double.parseDouble(value);
			break;
		case "String":
			this.value = value;
			break;
		case "Coordinates":
			String[] splitArr = value.split(",");
			double[] vals = {Double.parseDouble(splitArr[0].substring(1)),Double.parseDouble(splitArr[1].substring(0, splitArr[1].length()-1))};

		    this.value =vals;
		    break;
		}
	}
	/**
	 * returns this object as a string
	 * @return value of the object
	 */
	public String toString(){
		if(type == "int" || type == "number" || type == "String")
			return ""+value;
		else if(type == "Composite"){
			String ret = "{";
			@SuppressWarnings("unchecked")
			ArrayList<Value> values = (ArrayList<Value>) value;
			for(int i = 0; i < values.size() - 1; i++){
				ret+=values.get(i)+", ";
			}
			ret+=values.get(values.size()-1)+"}";
			return ret;
		} else if(type.equals("Coordinates")){
			double[] arr = (double[]) value;
			return "("+arr[0]+","+arr[1]+")";
		}

		return "";
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description){
		this.description = description;

	}

	public String getMetric() {
		// TODO Auto-generated method stub
		return metric;
	}
	public void setMetric(String metric) {
		// TODO Auto-generated method stub
		this.metric = metric;
	}
}