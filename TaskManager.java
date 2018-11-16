import java.util.List;
import java.util.ArrayList;
import java.text.NumberFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class TaskManager implements ActionListener, PropertyChangeListener {
	List<TaskCard> tasks;
	JFrame frame = new JFrame("My Tasks Manager");
	JPanel panel = new JPanel(new GridBagLayout());
	JPanel cpanel = new JPanel(new GridBagLayout());
	JButton addtask = new JButton("Add task");
	JButton refreshlist = new JButton("Refresh"); //repl with image
	
	
	final String filename = "taskfile.dat";

	TaskManager() {
		frame.setLayout(new FlowLayout());

		GridBagConstraints c = new GridBagConstraints();

		frame.add(panel);
		frame.getContentPane().setBackground(new Color(0,0,255));
		panel.setOpaque(false);
		cpanel.setOpaque(true);
		cpanel.setBackground(new Color(0,255,0));
		frame.add(cpanel);
		tasks = new ArrayList<TaskCard>();
		populateList();
		/*tasks.add(new TaskCard(new Task("Test 1")));
		tasks.add(new TaskCard(new Task("Test 2")));
		Task t3 = new Task("Test 3");
		t3.setPriority(Task.HIGH_PRIORITY);
		Task t4 = new Task("Test 4");
		t4.setPriority(Task.LOW_PRIORITY);
		TaskCard tc4 = new TaskCard(t4);
		tc4.addPropertyChangeListener("delete",this);

		tasks.add(new TaskCard(t3));
		tasks.add(tc4);*/

		reAddList();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = c.gridy = 0;
		cpanel.add(addtask, c);
		addtask.addActionListener(this);
		c.gridy+=1;
		cpanel.add(refreshlist, c);
		refreshlist.addActionListener(this);

		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if(propName.equals("delete")) {
			TaskCard tc = (TaskCard)evt.getNewValue();
			tasks.remove(tc);
			reAddList();
			frame.getContentPane().setBackground(new Color(0,0,255));
		}
	}

	void populateList() {
		try {
		ObjectInputStream infile = new ObjectInputStream(new FileInputStream(filename));
		int n = infile.read();
		for(int i=0;i<n;i++) {
			TaskCard tc = new TaskCard((Task)infile.readObject());
			tc.addPropertyChangeListener(this);
			tasks.add(tc);
		}
		infile.close();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	void reAddList() {
		/* 
		* Redraws the list to draw added or deleted stuff
		* also saves the changes to a file
		*/
		GridBagConstraints c = new GridBagConstraints();

		panel.removeAll();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.weightx = 1.0;
		c.gridy = 0;
		for(TaskCard t: tasks) {
			panel.add(t,c);
			c.gridy+=1;
		}
		panel.validate();
		frame.validate();

		//add to file
		try {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
		out.write(tasks.size());
		for(TaskCard tc: tasks) {
			out.writeObject(tc.task);
		}
		out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}/* catch(FileNotFoundException e) {
			e.printStackTrace();
		}*/
	}

	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getSource().equals(refreshlist)) {
			tasks.sort((t1,t2) -> t2.task.getPriority() - t1.task.getPriority());
			reAddList();
		} else if(evt.getSource().equals(addtask)) {
			JFrame addframe = new JFrame("Add a task");
			addframe.setLayout(new GridLayout(3,2));
			JTextField tname = new JTextField(10);
			JFormattedTextField tp = new JFormattedTextField(NumberFormat.getNumberInstance());
			tp.setColumns(10);
			tp.setValue(Task.MEDIUM_PRIORITY);
			JButton confirm = new JButton("Add task");
			addframe.add(new JLabel("Task Name"));
			addframe.add(tname);
			addframe.add(new JLabel("Task priority"));
			addframe.add(tp);
			addframe.add(new JPanel());
			addframe.add(confirm);

			confirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if(addTask(tname.getText(), Integer.parseInt(tp.getText()))) {
						JOptionPane.showMessageDialog(frame, "Added task '"+tname.getText()+"'.");
					} else {
						JOptionPane.showMessageDialog(frame, "Couldn't add task. It probably already exists.", "Failure",JOptionPane.ERROR_MESSAGE);
					}
					addframe.dispose();
				}
			});
			
			addframe.pack();
			addframe.setVisible(true);
		}
	}

	boolean addTask(String name, int priority) {
		for(TaskCard t: tasks) {
			if(t.task.getName().equals(name))
				return false;
		}
		tasks.add(new TaskCard(new Task(name, priority)));
		reAddList();
		return true;
	}
	
	Task getTask(String name) {
		for(TaskCard t: tasks) {
			if(t.task.getName().equals(name))
				return t.task;
		}
		return null;
	}

	public static void main(String []args) {
		new TaskManager();
	}
}

class TaskCard extends JPanel implements ActionListener {
	JPanel leftpanel;
	JPanel rightpanel;
	JButton uparrow; //increase priority
	JButton downarrow; //decrease priority
	JButton deletebutton; //deletes the task and the card.
	JLabel taskname;
	Task task;

	final static int P_INC = 10;

	TaskCard(Task task) {
		this.task = task;

		
	/*	setBackground(new Color(255,0,0));*/
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new GridLayout(1,2));
		leftpanel = new JPanel();
		leftpanel.setOpaque(true);
		leftpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		rightpanel = new JPanel();
		rightpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(leftpanel);
		add(rightpanel);

		taskname = new JLabel(task.getName());
		leftpanel.add(taskname);
		uparrow = new JButton("+"); //Change to image
		downarrow = new JButton("-"); //change to image
		deletebutton = new JButton("X"); //change to image
		rightpanel.add(deletebutton);
		rightpanel.add(uparrow);
		rightpanel.add(downarrow);

		uparrow.addActionListener(this);
		downarrow.addActionListener(this);
		deletebutton.addActionListener(this);

		resetBG();
	}

	void resetBG() {
		int p = task.getPriority();
		int brightness = 0;
		if(p>Task.HIGH_PRIORITY) {
			brightness = Task.HIGH_PRIORITY-p; //-ve = darkness
			p=Task.HIGH_PRIORITY;
		} else if(p<0) {
			brightness = -p;
			p=0;
		}
		p = p*255/Task.HIGH_PRIORITY;
		leftpanel.setBackground(new Color(
					p+((brightness<0)?brightness:0),
					255-p-((brightness>0)?brightness:0),
					0)); //color on priority
	}

	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource().equals(uparrow)) {
			task.setPriority(task.getPriority()+P_INC);
			resetBG();
		} else if(evt.getSource().equals(downarrow)) {
			task.setPriority(task.getPriority()-P_INC);
			resetBG();
		} else if(evt.getSource().equals(deletebutton)) {
			firePropertyChange("delete", null, this); //add confirmation
		}
	}
}
