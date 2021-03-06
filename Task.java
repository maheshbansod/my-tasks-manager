import java.io.Serializable;

public class Task implements Serializable {
	private int priority;
	private String name;

	static final int HIGH_PRIORITY = 200;
	static final int MEDIUM_PRIORITY = 100;
	static final int LOW_PRIORITY = 0;

	Task(String name) {
		this.name = name;
		priority = MEDIUM_PRIORITY;
	}

	Task(String name, int priority) {
		this.name = name;
		this.priority = priority;
	}

	void setPriority(int p) {
		priority = p;
	}

	void setName(String name) {
		this.name = name;
	}

	String getName() {
		return name;
	}

	int getPriority() {
		return priority;
	}
}

