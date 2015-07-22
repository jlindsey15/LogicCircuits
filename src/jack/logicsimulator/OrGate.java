package jack.logicsimulator;

import jack.logicsimulator.*;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class OrGate extends Component { // Or Gate class
	OrGate(int ID, int x, int y) {
		super(ID, "OR",  x, y); //adding to the component constructor, not replacing it
		type = "Or";
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
	
	public boolean operation(ArrayList<Boolean> args) { // "ors" the two inputs
		boolean input1 = args.get(0);
		boolean input2 = args.get(1);//need to relate these with the physical inputs
		boolean output = (input1||input2);//need to relate this with the Output
		return output;
	}
}
