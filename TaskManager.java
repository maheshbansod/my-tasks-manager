import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class TaskManager {
	List<TaskCard> tasks;
	JFrame frame = new JFrame("My Tasks Manager");

	TaskManager() {
		JLabel label = new JLabel("just saasdf");

		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		Task task = new Task("Test task");
		Task task2 = new Task("Another test task");
		tasks = new ArrayList<TaskCard>();
		tasks.add(new TaskCard(task));
		tasks.add(new TaskCard(task));
		frame.add(label);
		for(TaskCard t: tasks) {
			frame.add(t);
		}

		frame.setSize(800,600);
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
	JButton uparrow;
	JButton downarrow;
	JLabel taskname;
	Task task;

	TaskCard(Task task) {
		this.task = task;

		setLayout(new GridLayout(1,2));
		leftpanel = new JPanel();
		leftpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		rightpanel = new JPanel();
		rightpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(leftpanel);
		add(rightpanel);

		taskname = new JLabel(task.getName());
		leftpanel.add(taskname);
		uparrow = new JButton("/\\");
		downarrow = new JButton("\\/");
		rightpanel.add(uparrow);
		rightpanel.add(downarrow);
	}

}