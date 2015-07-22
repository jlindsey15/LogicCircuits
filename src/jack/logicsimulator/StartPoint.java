package jack.logicsimulator;

import jack.logicsimulator.*;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class StartPoint extends Component { //Start point class
	
	
	   StartPoint(int ID, int x, int y) {
	       super(ID, "Start", x, y); //adding to the component constructor, not replacing it
	       type = "Start";
	       
	      
	       value = 0; //startpoints are the only components with a value.  Other components only store values in their inputs/outputs
	       this.setText(Integer.toString(value)); //set's the text of the gate to its value
	       //formatting:
	       setVerticalAlignment(SwingConstants.CENTER);
	       setHorizontalAlignment(SwingConstants.CENTER);
	       setBounds(x, y, 70, 30);
	       setBorder(new LineBorder(Color.black, 1));
	       //Below: creates one input, one output.  Startpoints don't really have any inputs,
	       //but they do have toggle switches, and it was easier to make the toggle switches 
	       //be a hackishly modified version of inputs than to make a new class
	       Output bye = new Output(Main.currentConnectorID, this, getWidth(), getHeight() /2);
	       Input hi = new Input(Main.currentConnectorID, this, 0, getHeight() /2);
	       hi.hasValue = true;  //normally hasValue only gets set to true if there's a connection connected to the gate
	       						//input.  However, Startpoints can't have connections to their "input," but they still
	       						//have a value
	       
	       hi.maxConnections = 0; //should not be able to connect to this "input" (the toggle switch)
	       
	      
	   }
	   boolean isAvailable() { //startpoints should never be available - this is actually kind of redundant because maxConnections
		   						// is already 0, but whatever
		   return false;
	   }
	   public boolean operation(ArrayList<Boolean> args) { //doesn't actually do any operation, needs to be able to be called by simulate()
		   
		   return outputs.get(0).value;
	   }
	   public void toggle() { //toggles the startpoint value
		   System.out.println("toggling");
		   value +=1;
		   value %= 2;
		   if (value == 0) {
			   outputs.get(0).value = false; //StartPoint values are ints, but output values are booleans
		   }
		   else {
			   outputs.get(0).value = true;
		   }
		  
		   
		   this.setText(Integer.toString(value)); //sets the gate text to the value
		   
	   }
	   
	   

}
