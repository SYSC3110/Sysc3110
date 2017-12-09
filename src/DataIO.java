import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataIO {
	public void exportModelToFilename(String filename, MLModel model){
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//			bw.write("Values "+objects.get(0).getValues().size()+"\n");
			//			for(Value val:objects.get(0).getValues()){
			//				bw.write(val.getDescription()+"%%"+val.getType()+"\n");
			//			}
			//			for(Obj object:objects) bw.write(object.getNumber()+"%%"+object.getValues()+"\n");
			oos.writeObject(model);
			bw.close();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public MLModel getModelFromFilename(String filename){
		FileInputStream in = null;
		ObjectInputStream inO = null;
		MLModel model = null;
		try {

			in = new FileInputStream(filename);
			inO = new ObjectInputStream(in);

			model = (MLModel)inO.readObject();

		} catch (Exception ex) {

			ex.printStackTrace();

		}  finally {

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (inO != null) {
				try {
					inO.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return model;
	}
}