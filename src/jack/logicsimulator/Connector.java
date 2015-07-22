package jack.logicsimulator;

import jack.logicsimulator.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.io.Serializable;
import java.util.ArrayList;

abstract class Connector implements Serializable{ // general class for input and output since they're similar
												// inputs and outputs are the blue and red squares on the gates
	boolean value; //In general, inputs and outputs have values, not gates
	boolean hasValue = false; //doesn't have value until it's given one
	boolean isInput; //I used booleans instead of a "type" string, just to shake things up
	boolean isOutput; //^^
	int id;
	Component component; //the component that this connector "belongs" to
	ArrayList<Connection> connections = new ArrayList<Connection>(); //connections attached to this connector
	int maxConnections; //changed by subclasses
	//general formatting stuff to be fleshed out in Input and Output classes
	int x, y; //coordinates
	int w = 10, h = 10; //dimensions
	Shape shape; //set by subclasses - defines shape of how connector appears
	Color color; //^^
	Connector(int ID, Component c, int x, int y) {
		
		id = ID;
		Main.currentConnectorID++;
		
		this.component = c;
		this.x = x;
		this.y = y;
	}
	boolean isAvailable() { //so that you don't have six things going to one input
		if (component.type.equals("Start") && this.isInput) { //start gates are never available - jeez I have like three separate
															//things making sure of that
			return false;
		}
		else { //true of number of connections attached is less than the max (i.e. you can add more)
			return (connections.size() < maxConnections);
		}
		
	}
	void addConnection(Connection con) {
		if (isAvailable()) connections.add(con);
	}
	public int getX() {
		return component.getX() + x; //the x and y coordinates of connectors are their coordinates relative to their component, thus 
									//this gives you their absolute coordinates
	}
	public int getY() {
		return component.getY() + y; //^^
	}
	public boolean contains(Point p) { //used to see if a mouse click is on the Connector (input or output)
	return shape.contains(p);
	}
	public void paintConnector(Graphics2D g2d) { //draws the connector
		if (isAvailable()) {
			g2d.setColor(color);
		}
		else if (component.type.equals("Start")) { //toggle switches are a different color
			g2d.setColor(Color.pink);
			
		}
		else { //connector turns gray once it becomes "filled"
			g2d.setColor(Color.lightGray);
		}
		g2d.fill(shape); //graphics stuff
	}
}
