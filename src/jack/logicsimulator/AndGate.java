package jack.logicsimulator;

import jack.logicsimulator.*;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class AndGate extends Component { //And Gate class
	AndGate(int ID, int x, int y) {
		super(ID, "AND",  x, y); //adding to the component constructor, not replacing it
		type = "And";
		//formatting:
		setVerticalAlignment(SwingConstants.CENTER);
		setHorizontalAlignment(SwingConstants.CENTER);
		setBounds(x, y, 70, 30);
		setBorder(new LineBorder(Color.black, 1));
		//Below: creates two inputs, one output
		new Input(Main.currentConnectorID, this, 0, 6);
		new Input(Main.currentConnectorID, this, 0, getHeight() - 6);
		new Output(Main.currentConnectorID, this, getWidth(), getHeight() / 2);
	}
	
	public boolean operation(ArrayList<Boolean> args) { //"ands" the two inputs
		boolean input1 = args.get(0);
		boolean input2 = args.get(1);
		boolean output = (input1&&input2);
		return output;
	}
	
	
}
