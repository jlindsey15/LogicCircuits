package jack.logicsimulator;

import jack.logicsimulator.*;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class NotGate extends Component {  //Not gate class
	NotGate(int ID, int x, int y) {
		super(ID, "NOT",  x, y); //adding to the component constructor, not replacing it
		type = "Not";
		//formatting:
		setVerticalAlignment(SwingConstants.CENTER);
		setHorizontalAlignment(SwingConstants.CENTER);
		setBounds(x, y, 70, 30);
		setBorder(new LineBorder(Color.black, 1));
		//Below: creates one input, one output
		new Input(Main.currentConnectorID, this, 0, getHeight() / 2);
		new Output(Main.currentConnectorID, this, getWidth(), getHeight() / 2);
	}
	public boolean operation(ArrayList<Boolean> args) { //"nots" the input
		boolean input1 = args.get(0);
		boolean output = !input1;
		return output;
	}
	
}
