import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ListSelectionModel;




@SuppressWarnings("serial")
public class MLView extends javax.swing.JPanel implements Observer{
    private JMenuBar bar;
    private JMenu jm;
    private JMenuItem newItem;
    private JMenuItem exportObjs;
    private JMenuItem importObjs;
    private JButton addFeature;
    private JButton addObject;
    private JButton editObject;
    private JButton removeObject;
    private JButton calcError;
    private JButton findKNN;
    private JList<Obj> objList;
    private DefaultListModel<Obj> obj_list;
    private JFrame frame ;

    /**
     * Constructor for view
     */
	public MLView(){
		//superclass constructor
        super();
        //setup vars
        frame = new JFrame("View");
        obj_list = new DefaultListModel<>();
        bar = new JMenuBar();
        jm = new JMenu("Menu");
        newItem = new JMenuItem("New");
        exportObjs = new JMenuItem("Export Objects");
        importObjs = new JMenuItem("Import Objects");
        addFeature = new JButton("Add Value");
        addObject = new JButton("Add Object");
        editObject = new JButton("Edit Object");
        removeObject = new JButton("Remove Object");
        calcError = new JButton("Calculate Error");
        findKNN = new JButton("Find kNN");
        objList = new JList<Obj>(obj_list);
        objList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //add comps to panel
        this.add(addFeature);
        this.add(addObject);
        this.add(editObject);
        this.add(removeObject);
        this.add(findKNN);
        this.add(calcError);
        this.add(objList);
        jm.add(newItem);
        jm.add(exportObjs);
        jm.add(importObjs);
        bar.add(jm);
        //set up the frame
        frame.setJMenuBar(bar);
        frame.add(this);
        frame.setSize(750, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * Add an action listener to all JButtons and JMenu items
	 * @param a action listener to add
	 */
	public void addListener(ActionListener a){
		for(Component c:this.getComponents()){
			if(c instanceof JButton){
				((JButton) c).addActionListener(a);
				continue;
			}
		}
		newItem.addActionListener(a);
		importObjs.addActionListener(a);
		exportObjs.addActionListener(a);
	}
	
	@Override
	/**
	 * update the current j_list of the model
	 */
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof ArrayList){
			@SuppressWarnings("unchecked")
			ArrayList<Obj> objects = (ArrayList<Obj>)arg1;
			this.remove(objList);
			obj_list = new DefaultListModel<>();
			for(Obj o : objects){
				obj_list.addElement(o);
			}
			objList = new JList<Obj>(obj_list);
			this.add(objList);
			this.updateUI();
		}
	}
	/**
	 * returns the currently selected value on the j_list
	 * @return value of type Obj
	 */
	public Obj getSelVal() {
		
		return objList.getSelectedValue();
	}
}