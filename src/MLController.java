//made by mustafa
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MLController implements ActionListener{
	private MLView view;
	private MLModel model;

	/**
	 * constructor of type MLController
	 * @param view view to be added
	 * @param model model to be added
	 */
	public MLController(MLView view, MLModel model){
		this.view = view;
		this.model = model;
		model.addObserver(view);
		view.addListener(this);
		view.update(model, model.getObjects());
	}
	@Override
	/**
	 * actions to be done when a certain button is pressed
	 */
	public void actionPerformed(ActionEvent arg0) {
		String button = arg0.getActionCommand();
		//JList objList = new JList(obj_list);
		ArrayList<Value> features = model.getValues();;
		//	System.out.println(button);
		JPanel jp;
		kNN knn = new kNN();
		DataIO io = new DataIO();
		int result;
		switch(button){
		case "New":
			model = new MLModel(new ArrayList<>(), new ArrayList<>());
			model.addObserver(view);
			view.update(model, model.getObjects());
			break;
		case "Export Objects":
			jp = new JPanel();
			jp.setLayout(new GridLayout(features.size(), 2));
			JLabel label = new JLabel("File name for output: ");
			JTextField tf = new JTextField();
			jp.add(label);
			jp.add(tf);

			result = JOptionPane.showConfirmDialog(null, jp,
					"Exporting object data", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {

				String filename = tf.getText();
				io.exportModelToFilename(filename, model);
			}
			break;
		case "Import Objects":
			jp = new JPanel();
			label = new JLabel("File name to import from: ");
			tf =new JTextField();
			jp.add(label);
			jp.add(tf);
			result = JOptionPane.showConfirmDialog(null, jp,
					"Importing object data", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {

				String filename = tf.getText();
				MLModel newModel = io.getModelFromFilename(filename);
				model = newModel;
				model.addObserver(view);
			}



			view.update(model, model.getObjects());
			break;
		case "Add Value":
			int n = JOptionPane.showConfirmDialog(
					view,
					"Composite Feature?",
					"Adding a feature",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if(n == JOptionPane.YES_OPTION){
				ArrayList<Value> compVals = new ArrayList<>();
				jp = new JPanel();
				JFrame frame = new JFrame();
				JDialog dialog = new JDialog(frame, "Adding features to composite feature", true);
				JButton addFeatureButton = new JButton("Add Feature");
				JButton doneButton = new JButton("Done adding features");
				JPanel optionPane = new JPanel();
				JPanel outerContainer = new JPanel();
				JTextField vName = new JTextField("Enter feature description here");

				dialog.setLayout(new GridBagLayout());
				optionPane.setLayout(new BoxLayout(optionPane, BoxLayout.LINE_AXIS));
				optionPane.add(addFeatureButton);
				optionPane.add(doneButton);
				optionPane.add(vName);
				outerContainer.setLayout(new BoxLayout(outerContainer, BoxLayout.PAGE_AXIS));
				outerContainer.add(optionPane);
				outerContainer.add(jp);

				jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));

				addFeatureButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Value v = promptUserForValue();
						compVals.add(v);
						JLabel lab = new JLabel("Value #"+compVals.size()+", Description: "+v.getDescription()+", Type: "+v.getType()+", Metric: "+v.getMetric());
						jp.add(lab);
						//jp.updateUI();

						dialog.pack();
					}});
				doneButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						Value compV = new Value(vName.getText(),"Composite", "");
						compV.setValue(compVals);
						model.getValues().add(compV);


						dialog.dispose();
					}});
				dialog.setContentPane(outerContainer);
				frame.pack();
				dialog.pack();
				dialog.setVisible(true);

			}else if(n == JOptionPane.NO_OPTION){
				model.addValue(promptUserForValue());
			} else {}
			break;
		case "Add Object":
			if(model.getValues().size() == 0) break;
				ArrayList<Value> f_list = promptUserForObj();
				if(f_list!=null){
				Obj obj = new Obj(model.getNum(),f_list);
				model.addObject(obj,f_list);
				}
			
			break;

		case "Edit Object":
			if(view.getSelVal() == null) break;
			    f_list = promptUserForObj();

				if(f_list!=null){
				Obj obj = view.getSelVal();
				Obj newObj = new Obj(obj.getNumber(),f_list);

				model.removeObject(obj);
				model.addObject(newObj,f_list);
			}
			break;

		case "Remove Object":
			if(view.getSelVal() == null) break;
			
			Obj obj = view.getSelVal();
			model.removeObject(obj);
			break;
		case "Calculate Error":
			double err = knn.calculateError(model.getObjects());
			jp = new JPanel();
			JLabel errLabel = new JLabel("The total error is "+err);
			jp.add(errLabel);
			result = JOptionPane.showConfirmDialog(null, jp,
					"Error calculated", JOptionPane.OK_CANCEL_OPTION);
			break;
		case "Find kNN":
			jp = new JPanel();
			jp.setLayout(new GridLayout(features.size(), 2));
			label = new JLabel("kNN value: ");
			tf = new JTextField();
			jp.add(label);
			jp.add(tf);

			result = JOptionPane.showConfirmDialog(null, jp,
					"Please enter object values", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {

				int k = Integer.parseInt(tf.getText());
				obj = view.getSelVal();
				Value val = knn.getKNN(obj, model.getObjects(), k);
				//System.out.println("knn value: "+val.getValue());
				//System.out.println(val);
				obj.getLastValue().setValue(val.toString());
				model.setObject(model.getObjects().indexOf(obj), obj);
			}
			break;
		}

	}
	public static void main(String[] args){
		ArrayList<Value> vals = new ArrayList<>();
		ArrayList<Obj> objs = new ArrayList<>();
		vals.add(new Value("val 1", "number","Subtract"));
		vals.add(new Value("val 2", "number","Subtract"));
		vals.add(new Value("val 3", "number","Subtract"));
		//vals.add(new Value("Coord val","Coordinates","Euclidean Distance"));
		for(int i = 0; i < 5; i++){
			ArrayList<Value> newVals = new ArrayList<>();
			for(int c = 0; c < vals.size()-1; c++){
				Value v = new Value(vals.get(c).getDescription(),vals.get(c).getType(),vals.get(c).getMetric());
				v.setValue((Math.floor(Math.random()*10))+"");
				newVals.add(v);
			}

			Obj obj = new Obj(i+1, newVals);
			objs.add(obj);

		}
		MLView view = new MLView();
		MLModel model = new MLModel(objs,vals);
		model.notifyObservers(model.getObjects());
		@SuppressWarnings("unused")
		MLController con = new MLController(view, model);

	}
	public ArrayList<Value> promptUserForObj(){
		ArrayList<Value> features = this.model.getValues();
		JPanel jp = new JPanel();
		JLabel label;
		JTextField tf;
		jp.setLayout(new GridLayout(features.size(), 2));
		ArrayList<JTextField> tfA = new ArrayList<>();
		for(int i=0; i< features.size(); i++){
			if(!features.get(i).getType().equals("Composite"))
			{
				label = new JLabel("Value #"+(i+1)+": "+features.get(i).getDescription());
				tf = new JTextField();
				jp.add(label);
				jp.add(tf);
				tfA.add(tf);
			}else{
				JPanel newPane = new JPanel();
				@SuppressWarnings("unchecked")
				ArrayList<Value> vals = (ArrayList<Value>) features.get(i).getValue();
				newPane.setLayout(new GridLayout(vals.size(),2));
				for(Value v : vals){
					label = new JLabel("Sub-Value: "+v.getDescription());
					tf = new JTextField();
					newPane.add(label);
					newPane.add(tf);
					tfA.add(tf);
				}
				jp.add(newPane);
				jp.add(new JLabel());
			}
		}
		int result = JOptionPane.showConfirmDialog(null, jp,
				"Please enter object values", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			ArrayList<Value> f_list = new ArrayList<>();
			int c = 0;
			for(int i = 0; i < features.size();i++){
				if(!features.get(i).getType().equals("Composite")){
					Value f = new Value(features.get(i).getDescription(),features.get(i).getType(),features.get(i).getMetric());

					f.setValue(tfA.get(c++).getText());
					f_list.add(f);
				} else {
					@SuppressWarnings("unchecked")
					ArrayList<Value> vals = (ArrayList<Value>) features.get(i).getValue();
					ArrayList<Value> newList = new ArrayList<>();
					for(Value v : vals){
						Value f = new Value(v.getDescription(), v.getType(), v.getMetric());
						f.setValue(tfA.get(c++).getText());
						newList.add(f);
					}
					Value newF = new Value(features.get(i).getDescription(), features.get(i).getType(),features.get(i).getMetric());
					newF.setValue(newList);
					f_list.add(newF);

				}
			}
			return f_list;
		}
		return null;
	}
	public Value promptUserForValue(){
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(4,2));
		JLabel JLDescription = new JLabel("Description: ");
		JTextField JtfDescription = new JTextField();
		JLabel JLType = new JLabel("Type: ");
		JLabel JLMetric = new JLabel("Metric: ");

		DefaultListModel<String> list = new DefaultListModel<>();
		DefaultListModel<String> mList = new DefaultListModel<>();
		JList<String> j_list = new JList<>(list);
		JList<String> mj_list = new JList<>(mList);
		j_list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {

				mList.clear();
				switch(j_list.getSelectedValue()){
				case "String":
					mList.addElement("Lexographical comparison");
					break;
				case "number":
					mList.addElement("Subtraction");
					break;
				case "Coordinates":
					mList.addElement("Euclidean Distance");
					mList.addElement("Sum of Squared Difference");
					break;
				}
			}
		});
		list.addElement("number");
		list.addElement("String");
		list.addElement("Coordinates");
		j_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		j_list.setBorder(BorderFactory.createLineBorder(Color.black));
		mj_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mj_list.setBorder(BorderFactory.createLineBorder(Color.black));
		jp.add(JLDescription);
		jp.add(JtfDescription);
		jp.add(JLType);
		jp.add(j_list);
		jp.add(JLMetric);
		jp.add(mj_list);

		int result = JOptionPane.showConfirmDialog(null, jp,
				"Please enter feature details", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION){
			String desc = JtfDescription.getText();
			Value f = new Value(desc, j_list.getSelectedValue(),mj_list.getSelectedValue());

			return f;
		} 
		return null;
	}
}