package jack.logicsimulator;

import jack.logicsimulator.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class UserGate extends Component {
	static HashMap theTruthTable = new HashMap(); //the truthTable of the current selected black box file
	HashMap myTruthTable; //existing black boxes shouldn't change when new black box file selected.  Thus, we
						// clone the current truth table and set that to an object-specific truth table
	int inputCount = 0; //used for formatting
	int outputCount = 0;//used for formatting
	static int numberInputs = 0;
	static int numberOutputs = 0;
	static String name;

	UserGate(int ID, int x, int y) {
		super(ID, name, x, y);
		myTruthTable = (HashMap)theTruthTable.clone();
		type = "User";
		setVerticalAlignment(SwingConstants.CENTER);
	    setHorizontalAlignment(SwingConstants.CENTER);
	    if (numberInputs > numberOutputs) {
	    	setBounds(x, y, 70, 15+(numberInputs*15));
	    }
	    else {
	    	setBounds(x, y, 20 + 8*name.length(), 15+(numberOutputs*15));
	    }
	    setBorder(new LineBorder(Color.black, 1));
	    for (int i = 0; i < numberInputs; i++) { //creates proper number of inputs, formats them non-stupidly
	    	new Input(Main.currentConnectorID, this, 0, (inputCount+1) * getHeight() / (numberInputs + 1));
	    	inputCount++;
	    }
	    for (int i = 0; i < numberOutputs; i++) { //same deal but with outputs
	    	new Output(Main.currentConnectorID, this, getWidth(), (outputCount+1) * getHeight() / (numberOutputs + 1) );
	    	
	    	outputCount++;
	    }
	}
	
	public ArrayList<Boolean> userOperation(ArrayList<Boolean> args) { //gets value corresponding to the inputs from the truth table
		ArrayList<Boolean> toBeReturned = new ArrayList<Boolean>();
		System.out.println(args);
		toBeReturned = (ArrayList<Boolean>)myTruthTable.get(args);
		return toBeReturned;
		
	}
}
