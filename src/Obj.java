import java.io.Serializable;
import java.util.ArrayList;
public class Obj implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4650176844586391252L;
	private ArrayList<Value> values;
	private int number;
	private double distance;
	
	public double getDistance(){
		return distance;
	}
	public void setDistance(double distance){
		this.distance = distance;
	}
	/**
	 * Constructor for object class 
	 * @param number: number of object
	 * @param values: list of values in object
	 */
	public Obj(int number, ArrayList<Value> values){
	this.number = number;
	this.values = values;
	}
	/*
	 * Add feature to the current object
	 */
	public void addValue(Value f){
		this.values.add(f);
	}
	/*
	 * Get feature
	 */
	public Value getValue(int index){
		return values.get(index);
	}
	/*
	 * return all the values of the current object
	 */
	public ArrayList<Value> getValues() {
		return values;
	}
	/*
	 * set all the values of this object
	 */
	public void setValues(ArrayList<Value> values) {
		this.values = values;
	}
	/*
	 * get the number of this object
	 */
	public int getNumber() {
		return number;
	}
	/*
	 * return the number of this object
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public Value getLastValue(){
		return this.values.get(values.size()-1);
	}
	public String toString(){
		String retStr = "Object #"+number+" with values: ";
		for(int i = 0; i < values.size() - 1; i++){
			retStr+=values.get(i).getDescription()+": ";
			retStr+=values.get(i)+", ";
		}
		retStr+=values.get(values.size()-1).getDescription()+": ";
		retStr+=values.get(values.size()-1)+".";
		return retStr;
	}
}