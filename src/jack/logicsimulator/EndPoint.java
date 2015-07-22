package jack.logicsimulator;

import jack.logicsimulator.Component;
import jack.logicsimulator.Input;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class EndPoint extends Component { //End point class
	   EndPoint(int ID, int x, int y) {
	       super(ID, "End", x, y); //adding to the component constructor, not replacing it
	       type = "End";
	       //Formatting stuff:
	       setVerticalAlignment(SwingConstants.CENTER);
	       setHorizontalAlignment(SwingConstants.CENTER);
	       setBounds(x, y, 70, 30);
	       setBorder(new LineBorder(Color.black, 1));
	       //Creates one input, zero outputs:
	       new Input(Main.currentConnectorID, this, 0, getHeight() /2);
	      
	   }
}
