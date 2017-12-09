//made by mustafa
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class MLModel extends Observable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2186343833656677381L;
	private ArrayList<Obj> objects;
	private ArrayList<Value> values;
	private int num; //number of total objects
	/**
	 * MLModel constructor
	 * @param objects arraylist of objects
	 * @param values arraylist of values for the objects
	 */
	public MLModel(ArrayList<Obj> objects, ArrayList<Value> values){
		this.objects = objects;
		this.values = values;
		this.num = objects.size()+1;
	}
	/**
	 * Add a new object to objects
	 * @param values list of values of the object
	 */
	public void newObject(ArrayList<Value> values){
		Obj obj = new Obj(num++, values);
		objects.add(obj);
		setChanged();
		notifyObservers(this.objects);
	}
	/**
	 * set an object at a certain index
	 * @param index index of obj
	 * @param obj object replacement
	 */
	public void setObject(int index, Obj obj){
		objects.set(index, obj);
		setChanged();
		notifyObservers(this.objects);
	}
	/**
	 * Add an object with a values list
	 * @param obj obj to be added
	 * @param values list of feautures
	 */
	public void addObject(Obj obj, ArrayList<Value> values){
		obj.setValues(values);
		objects.add(obj);
		num++;
		setChanged();
		notifyObservers(this.objects);
	}
	/**
	 * Removes an object from the list
	 * @param obj object to be removed
	 * @return the object if removed successfully
	 */
	public Obj removeObject(Obj obj){
		Obj i =  objects.remove(objects.indexOf(obj));
		setChanged();
		notifyObservers(this.objects);
		return i;
	}
	/**
	 * Add feature f to the list
	 * @param f feature to be added
	 */
	public void addValue(Value f){
		values.add(f);
		setChanged();
		notifyObservers(this.objects);
	}
	/**
	 * Removes a feature from the list
	 * @param f feature to be removed
	 * @return null if fail, f if success
	 */
	public Value removeValue(Value f){
		setChanged();
		notifyObservers(this.objects);
		return values.remove(values.indexOf(f));
	}
	/**
	 * getter for values
	 * @return values
	 */
	public ArrayList<Value> getValues() {
		return values;
	}
	/**
	 * Getter for objects
	 * @return objects
	 */
	public ArrayList<Obj> getObjects() {
		return objects;
	}
	/**
	 * get an object at index
	 * @param index index of obj
	 * @return objects[index]
	 */
	public Obj getObject(int index){
		return objects.get(index);
	}
	/**
	 * setter for Objects
	 * @param objects objects
	 */
	public void setObjects(ArrayList<Obj> objects) {
		
		this.objects = objects;
		setChanged();
		notifyObservers(this.objects);
	}
	/**
	 * getter for num
	 * @return num
	 */
	public int getNum() {
		return num;
	}
	/**
	 * setter for num
	 * @param num
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * setter for values
	 * @param values
	 */
	public void setValues(ArrayList<Value> values) {
		this.values = values;
	}

}