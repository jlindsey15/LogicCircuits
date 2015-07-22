package jack.logicsimulator;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectState implements Serializable { //Contains all save-worthy things about the project (components and connections)
	ArrayList<Component> projectComponents;
	ArrayList<Connection> projectLines;
	public ProjectState() {
		projectComponents = Main.components;
		projectLines = Main.lines;
	}
}
