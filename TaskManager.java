import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class TaskManager {
	List<TaskCard> tasks;
	JFrame frame = new JFrame("My Tasks Manager");
	JPanel panel = new JPanel(new GridBagLayout());
	JPanel cpanel = new JPanel(new GridBagLayout());
	JButton addtask = new JButton("Add task");

	TaskManager() {
		frame.setLayout(new FlowLayout());

		GridBagConstraints c = new GridBagConstraints();

		frame.add(panel);
		frame.getContentPane().setBackground(new Color(0,0,255));
		cpanel.setOpaque(true);
		cpanel.setBackground(new Color(0,255,0));
		frame.add(cpanel);
		tasks = new ArrayList<TaskCard>();
	tasks.add(new TaskCard(new Task("Test 1")));
		tasks.add(new TaskCard(new Task("Test 2")));
		Task t3 = new Task("Test 3");
		t3.setPriority(Task.HIGH_PRIORITY);
		Task t4 = new Task("Test 4");
		t4.setPriority(Task.LOW_PRIORITY);
		tasks.add(new TaskCard(t3));
		tasks.add(new TaskCard(t4));
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.weightx = 1.0;
		c.gridy = 0;
		for(TaskCard t: tasks) {
			panel.add(t,c);
			c.gridy+=1;
		}

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = c.gridy = 0;
		cpanel.add(addtask, c);

		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String []args) {
		new TaskManager();
	}
}

class TaskCard extends JPanel{
	JPanel leftpanel;
	JPanel rightpanel;
	JButton uparrow; //increase priority
	JButton downarrow; //decrease priority
	JButton deletebutton; //deletes the task and the card.
	JLabel taskname;
	Task task;

	TaskCard(Task task) {
		this.task = task;

		int p = task.getPriority();
		p = (p>Task.HIGH_PRIORITY)?Task.HIGH_PRIORITY:p;
		p = p*255/Task.HIGH_PRIORITY;
		
	/*	setBackground(new Color(255,0,0));*/
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new GridLayout(1,2));
		leftpanel = new JPanel();
		leftpanel.setOpaque(true);
		leftpanel.setBackground(new Color(p,255 - p,0)); //color on priority
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
	}

}
