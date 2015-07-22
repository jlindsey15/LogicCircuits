package jack.logicsimulator;

import jack.logicsimulator.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

public class Connection extends JComponent { //Connection class - Connections connect components together by their inputs and outputs (connectors)

	Output output; //connections "know" the output and input they are connecting
	Input input; //^^
	int id; //currently unused id feature
	public Connection(int ID, Output theOutput, Input theInput) {
		setDoubleBuffered(true); //graphics stuff - may or may not be necessary, but it does no harm
		id = ID;
		Main.currentConnectionID++;
		
		this.output = theOutput;
		this.input = theInput;
		if (output.isAvailable()) output.connections.add(this); //only make line if the outputs/inputs aren't already taken
		if (input.isAvailable()) input.connections.add(this); //^^
	}
	public void paintConnection(Graphics2D g2d) { //draws the line associated with the connection
		
			Line2D.Double line = new Line2D.Double(output.getX(), output.getY(), input.getX(), input.getY());			
			g2d.draw(line);
			output.inputsReceivingThis.add(input); //Now the output "knows" where it's going
			if (Main.mode.equals("choosingInput")) { //this if statement may or may not be necessary...
				input.component.numberConnected +=1;
		
			}
	}
}