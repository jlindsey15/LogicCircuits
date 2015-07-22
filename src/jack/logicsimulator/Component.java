package jack.logicsimulator;

import jack.logicsimulator.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JLabel;

abstract class Component extends JLabel implements MouseListener, MouseMotionListener, Serializable { //Superclass of gates, Starts, and Ends
	
	
	int numberConnected = 0; //starts out with nothing connected
	int id; //part of the currently-unused id feature
	String type; //set differently depending on subclass

	public void toggle() { //Is overwritten only in the StartPoint class - go there if you dare...
		;
	}
	public boolean operation(ArrayList<Boolean> args) { //overwritten by subclasses
		return false;
	}
	
	public ArrayList<Boolean> userOperation(ArrayList<Boolean> args) { //overwritten by subclasses
		return (new ArrayList<Boolean>());
	}
	int value = 0; //default value of gate is 0 (That's why startpoints start out at 0)
	ArrayList<Input> inputs = new ArrayList<Input>(); //list of this component's inputs
	ArrayList<Input> toggles = new ArrayList<Input>(); // list of this component's toggle "inputs" (there should be 1 if StartPoint, else 0)
	ArrayList<Output> outputs = new ArrayList<Output>();//list of its outputs
	public Component(int ID, String text, int x, int y) {
		
		super(text); //we are extending JLabel, not replacing it - this makes JLabel with the given text
		id = ID;
		Main.currentComponentID++;
		
		Main.components.add(this); //adds to metalist of components
		addMouseListener(this); //allows for mouse events on the component (fleshed out later in class)
		addMouseMotionListener(this);
		Main.drawPanel.add(this); //makes this component appear on the screen
	}
	
	public void addOutput(Output o) {
		outputs.add(o);
	}
	public void addInput(Input i) {
		inputs.add(i);
	}
	
	public void addtoToggles(Input i) {
		toggles.add(i);
	}
	@Override
	public void paintComponent(Graphics g) { //displays component depending on the your Main.modes and stuff
											//this gets called on drawPanel.repaint()
		
	
		super.paintComponent(g); 

		Graphics2D g2d = (Graphics2D) g;

		if (Main.showInputs) { //inputs only shown when appropriate
			for (Input in : inputs) {
				in.paintConnector(g2d);
			}
		}
		
		else {
			for (Input tog : toggles) {
				tog.paintConnector(g2d);
			}
		}
		if (Main.showOutputs) {
			for (Output out : outputs) { //outputs only shown when appropriate
				if (out.isAvailable()) {
					out.paintConnector(g2d);
				}
				
			}
		}
	}
	int startDragX, startDragY; //used for click and drag
	boolean inDrag = false; //set true when in click and drag
	
	@Override
	public void mouseEntered(MouseEvent e) {

		//doesn't do anything, but java makes you have it there because it thinks it might do something
	}
	@Override
	public void mouseExited(MouseEvent e) {
		//doesn't do anything, but java makes you have it there because it thinks it might do something
	}
	@Override
	public void mousePressed(MouseEvent e) { //the "click" of click-and-drag
		
		System.out.println(Main.mode);
		if (Main.mode.equals("erase")) { //if you're in erase mode and you click a component, it should go away
			
		
			Main.drawPanel.remove(this);
			Main.components.remove(this);
			//the following removes all connections associated with this component:
			for (Input input : this.inputs) {

				for (Connection connection : input.connections) {
					
					Main.drawPanel.remove(connection);
					Main.lines.remove(connection);
					
					connection.output.connections.remove(connection);
					
					
					//for (Line2D.Double line : connection.physicalLines) {
					//	Main.drawPanel.remove(line);
					//}
					
				}
				input.connections.remove(input.connections);
			}
			for (Output output : this.outputs) {
				for (Connection connection : output.connections) {
					Main.drawPanel.remove(connection);
					Main.lines.remove(connection);
	
					connection.input.connections.remove(connection);
				}
				output.connections.remove(output.connections);
			}
			
			Main.drawPanel.repaint();
			Main.mode = ""; //if you want to erase again, you have to click the erase button again
			Main.header.setText("Do something!");
		}
		startDragX = e.getX(); // mouse location at click time
		startDragY = e.getY(); // mouse location at click time
		
	}
	@Override
	public void mouseReleased(MouseEvent e) { //when you release the mouse from a click and drag
		if (inDrag) {
			inDrag = false;
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		for (Input in : inputs) {
			if (in.component.type.equals("Start") && in.contains(e.getPoint())){ //if you click on a start point "input" (toggle switch), it toggles the start value
				System.out.println("testtoggle");
				in.component.toggle();
				
		}
		}
		if (Main.mode.equals("choosingInput")) { //if you're in choosing Input Main.mode, it chooses the input and then draws the line
												//if you're in this mode then you already chose an output for the line (selectedOutput)
		
			for (Input in : inputs) {
				if (in.isAvailable() && in.contains(e.getPoint())) {
					
					Main.lines.add(new Connection(Main.currentConnectionID, Main.selectedOutput, in));
					
					Main.drawPanel.repaint();
					Main.mode = "";
					Main.showOutputs = true;
					Main.header.setText(" ");
				}
			}
		}
		if (Main.mode.equals("choosingOutput")) { //if you're in choosing Input Main.mode, it chooses the input and then goes to choosing output Main.mode
			for (Output out : outputs) {
				if (out.isAvailable() && out.contains(e.getPoint())) {
					
					Main.selectedOutput = out; //so that the program knows where to draw the connection from when you select the input
					Main.showOutputs = false;
					Main.mode = "choosingInput"; //after choosing an output you choose an input
					Main.showInputs = true;
					Main.header.setText("Click an input to connect...");
					Main.drawPanel.repaint();
					
				}
			}
		}
	
	}
	@Override
	
	//The "drag" of click-and-drag
	public void mouseDragged(MouseEvent e) {
		int newX = getX()  + (e.getX() -startDragX); //moves component to its new location
		int newY = getY() + (e.getY() -startDragY); //^^
		setLocation(newX, newY);
		inDrag = true;
		Main.drawPanel.repaint();
		Main.frame.repaint();
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		//once again useless but necessary
	}
}