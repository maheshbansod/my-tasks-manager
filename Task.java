
public class Task {
	private int priority;
	private String name;

	static final int HIGH_PRIORITY = 40;
	static final int MEDIUM_PRIORITY = 25;
	static final int LOW_PRIORITY = 10;

	Task(String name) {
		this.name = name;
		priority = MEDIUM_PRIORITY;
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

