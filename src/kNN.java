import java.util.*;
//made by mustafa, Tashfiq, Manel
public class kNN{
	/**
	 * Calculates the last feature's Value based on the the least distance neighbours up to k
	 * @param objToFind object to find Value for
	 * @param objects 
	 * @param k
	 * @return Value object of the calculated value
	 */
	public Value getKNN(Obj objToFind, ArrayList<Obj> objects, int k){
		Value ans;
		String outputType;
		
		int i = 0;
		do{
			outputType = objects.get(i).getLastValue().getType();
			i++;
		}while(outputType == null);
		ans = new Value(objects.get(0).getLastValue().getValue());
		ans.setType(outputType);
		ans.reset();
	//	System.out.println(objects+" is trying to remove "+objToFind);
		if(objects.contains(objToFind))
			objToFind = objects.remove(objects.indexOf(objToFind));

		for(Obj obj:objects){
			//System.out.println(getDistance(objToFind,obj));
			obj.setDistance(getDistance(objToFind,obj));
			//distances.put(obj, getDistance(objToFind, obj));
		}
		boolean flag = true;
		while (flag){
			flag = false;
			for (i=0;i<objects.size()-1;i++){
				if (objects.get(i).getDistance() > objects.get(i+1).getDistance()){
					//	System.out.println(objects.get(i).getDistance() +" swag "+ objects.get(i+1).getDistance());
					Collections.swap(objects, i,i+1);
					flag = true;
				}
			}
			//System.out.println("test");
		}

		switch(outputType){
		case "number":
			//ans = new Value(objects.get(0).getLastValue().getValue());
			//ans.reset();
			//objToFind = objects.remove(objects.indexOf(objToFind));
			//HashMap<Obj, Double> distances = new HashMap<Obj, Double>(); //hashmap of distances as compared to objToFind
			for(i = 0; i < k; i++){
				//System.out.println(objects.get(i).getValue(objects.get(i).getValues().size()-1).getValue());
				ans.add(objects.get(i).getLastValue().getValue());
			}
			double ok = 1.0/k;
			ans.multiply(ok);
			objects.add(objToFind);
			return ans;
		case "String":
			HashMap<String, Integer> possOutputs = new HashMap<>();
			for(i = 0; i<objects.size();i++){
				String s = (String) objects.get(i).getLastValue().getValue();
				if(s != null && !possOutputs.containsKey(s)){
					possOutputs.put(s,0);
				}
			}
			for(i = 0; i < k; i++){
				String s = (String) objects.get(i).getLastValue().getValue();
				if(s!=null && possOutputs.containsKey(s)){
					possOutputs.put(s, possOutputs.get(s)+1);
				}
			}
			String out = "";
			int max = 0;
			for(String s:possOutputs.keySet()){
				if(possOutputs.get(s) > max){
					out = s;
					max = possOutputs.get(s);
				}
			}
			ans = new Value(out);
			objects.add(objToFind);
			return ans;

		case "array":
			break;
		case "Coordinates":
			for(i = 0; i < k; i++){
				//System.out.println(objects.get(i).getValue(objects.get(i).getValues().size()-1).getValue());
				ans.add(objects.get(i).getLastValue().getValue());
			}
			 ok = 1.0/k;
			ans.multiply(ok);
		//	System.out.println("Answer is: "+ans);
			objects.add(objToFind);
			return ans;
			
		}
		return null;
	}
	/**
	 * returns the total distance between two objects
	 * @param objToFind object 1
	 * @param obj object 2
	 * @return total distance between obj1 and 2
	 */
	private double getDistance(Obj objToFind, Obj obj) {
		double totalDistance = 0;
		int i = 0;
		for(; i<obj.getValues().size()-1;i++){
			//System.out.println("Subtracting "+objToFind.getValue(i).getValue()+" from "+obj.getValue(i).getValue());
			totalDistance += obj.getValue(i).getDifference(objToFind.getValue(i));
		}
		return totalDistance;
	}
	/**
	 * Adds up all the differences from the calculated values vs the actual values as error 
	 * @return total calculated error
	 */
	public double calculateError(ArrayList<Obj> objects){
		double totalError = 0;
		for(int i = 0; i < objects.size(); i++){
			Obj obj = objects.remove(i);
			Value oldVal = obj.getLastValue();
			Value calculatedValue = getKNN(obj, objects, objects.size()-1);
			if(oldVal.getValue() == null || calculatedValue.getValue() == null){
				continue;
			}
			//objects.add(i, obj);
			totalError+= oldVal.getDifference(calculatedValue) > 0 ?oldVal.getDifference(calculatedValue):0;
		}
		return totalError;
	}





}