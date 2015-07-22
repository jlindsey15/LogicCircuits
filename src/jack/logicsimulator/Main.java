package jack.logicsimulator;
//Todo: comments in other classes, image files?, study for math test, add comments to buttonlisteners

/*authors: Jack Lindsey and Maria Messick
version 1.0
12/4/2012

This program allows users to design their own logic circuits, by creating and connecting logic
gates, simulating their output, and saving them into more complex gates (black boxes) to be used
later.
*/

//Bugs - erasing the connection receiver after load doesn't erase the connection -FIXED
//simulation after a bunch of loads - FIXED
//crazy drag after load - crazy drag squared after 2 loads - FIXED
//weird toggle thing after load - FIXED
//lines stay on screen after load - FIXED
//simulation doesn't work -FIXED
//connections from black boxes are weird - FIXED


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.*;
import java.io.*;
public class Main implements Serializable {
	//Main class
	//Formatting stuff below

	
	static ArrayList<Component> components = new ArrayList<Component>(); //list of all components in the world
	static ArrayList<Connection> lines = new ArrayList<Connection>(); //list of all connections between gates in the world
	//The following fields are used for giving ID numbers to objects.  These ID numbers turned out to never be used,
	//but they seem a useful thing to have, and indeed they might be if future updates are made to this program
	static int currentComponentID = 0; 
	static int currentConnectorID = 0;
	static int currentConnectionID = 0;
	static String mode = ""; //The "mode" of the program - depending on the mode, different actions will do different things
	static boolean showInputs = true, showOutputs = true;
	static Output selectedOutput = null;
	
	//Create the program window:
	static JLabel header = new JLabel("Hey, user, what's up?");
	static JFrame frame = new JFrame("");
	static Screen drawPanel = new Screen();
	
	Main() {
		//GUI FORMATTING STUFF BEGINS
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dimension = new Dimension(1600,400); //dimension size
		frame.setMinimumSize(dimension);
		
		frame.add(header, BorderLayout.PAGE_START);
		drawPanel.setLayout(null);
		frame.add(drawPanel);
		JPanel buttonspanel = new JPanel();
		frame.add(buttonspanel, BorderLayout.PAGE_END);
		JButton andbutton = new JButton("Add an And Gate");
		JButton orbutton = new JButton("Add an Or Gate");
		JButton notbutton = new JButton("Add a Not Gate");
		JButton newConnectionButton = new JButton("Add a Connection");
		JButton startButton = new JButton("Add a Start Point");
		JButton endButton = new JButton("Add an End Point");
		JButton simulateButton = new JButton("Simulate");
		JButton saveButton = new JButton("Save");
		JButton eraseButton = new JButton("Erase");
		JButton loadButton = new JButton("Load");
		JButton saveBlackBoxButton = new JButton("Save as BB");
		JButton loadBlackBoxButton = new JButton("Load black box");
		JButton addBlackBoxButton = new JButton ("Add black box");
		
		
		buttonspanel.add(andbutton);
		buttonspanel.add(orbutton);
		buttonspanel.add(notbutton);
		buttonspanel.add(newConnectionButton);
		buttonspanel.add(startButton);
		buttonspanel.add(endButton);
		buttonspanel.add(simulateButton);
		buttonspanel.add(eraseButton);
		buttonspanel.add(saveButton);
		buttonspanel.add(loadButton);
		buttonspanel.add(saveBlackBoxButton);
		buttonspanel.add(loadBlackBoxButton);
		buttonspanel.add(addBlackBoxButton);
		
		//GUI FORMATTING STUFF ENDS.  button event handling below.  To see what the buttons actually do,
		//Go to the function inside "actionPerformed"
		andbutton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		makeAnd();
		}
		});
		
		orbutton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		makeOr();
		}
		});
		
		notbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				makeNot();
			}
		});
		
		newConnectionButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		makeConnection();
		}
		});
		
		
		startButton.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent arg0) {
		       makeStart();
		   }
		});
		
		endButton.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent arg0) {
			       makeEnd();
			   }
			});
		simulateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				simulate();
			}
		});
		
		eraseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				makeErase();
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					save(false);
				}
				catch (IOException e) {
					;
				}
			}
		});
		
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					load();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				catch (ClassNotFoundException e) {
					System.out.println("Class not found...");
					e.printStackTrace();
				}
			}
		});
		
		saveBlackBoxButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					saveBlackBox();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		loadBlackBoxButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					loadBlackBox();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		addBlackBoxButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				makeBlackBox();
				
				
				
			}
		});
		
		
		frame.setVisible(true);
	}
	
	//self-explanatory functions below
	void makeAnd() { 
		header.setText("Click the screen and watch the magic happen...");
		mode = "addingAnd";
	}
	
	void makeOr() {
		header.setText("Click the screen and watch the magic happen...");
		mode = "addingOr";
	}
	
	void makeNot() {
		header.setText("Click the screen and watch the magic happen...");
		mode = "addingNot";
	}
	void makeConnection() {
		header.setText("Click an output to connect...");
		mode = "choosingOutput";
		showInputs = false;
		showOutputs= true;
		drawPanel.repaint(); //repainting is basically refreshing the GUI
	}
	
	void makeStart() {
	   header.setText("Click to add start point");
	   mode = "addingStart";
	}
	
	void makeEnd() {
		header.setText("Click to add end point");
		mode = "addingEnd";
	}
	
	void makeErase() {
		
		mode = "erase";
		header.setText("Click something to erase it");
	}
	
	void makeBlackBox() {
		mode = "addingBlackBox";
		header.setText("Click the screen and watch some real magic happen");
	}
	
	//Now here's the simulate function...
	int simulate() {
		int toBeReturned = -1; //eror value - returned unless the function works
		
		int keepGoing = 0; 
		
		while (keepGoing < 200) { //cycle through a bunch of times to make sure the end of the circuit is reached
			keepGoing++;
			int endsReached = 0;
		
			for (Component gate : components) {
				boolean act = true;
				ArrayList<Boolean> inputValues = new ArrayList<Boolean>(); //list of values of inputs to gate
				for (Input input : gate.inputs) {
					if (!(input.hasValue)) {
						act = false; //if the gate doesn't have values in it's inputs, it should wait till it's next "turn" to act
					}
					else {
						inputValues.add(input.value); 
					}
				}
				if (act) {  //assuming the gate did have values in its inputs
					if (gate.type.equals("User")) { //User defined black boxes use a different function to operate on their inputs,
													//and they also can have multiple outputs.  So they are handled separately
						int nextOne=0;
						for (Output out : gate.outputs) {
							System.out.println("nextone is " + nextOne + "input value: " + inputValues + " value is " + (gate.userOperation(inputValues)));
							out.value = gate.userOperation(inputValues).get(nextOne);
							nextOne ++;
						}
					}
					else { //everything besides black boxes
						for (Output out : gate.outputs){
							out.value = gate.operation(inputValues);
						}
					}
					for (Output out : gate.outputs) { //"transfers" value from outputs to the inputs they are connected to
						for (Input input : out.inputsReceivingThis) { 
							
							
							input.value = out.value;
							input.hasValue = true;
						}
					}
					if (gate.type.equals("End")) { 
						boolean reached = false;
						String result;
						if (gate.inputs.get(0).value == false) {
							result = Integer.toString(0);
							reached = true;
						}
						else if (gate.inputs.get(0).value == true) {
							result = Integer.toString(1);
							reached = true;
						}
						else { //in case the input value never got set, we don't want the program  trying to set the End gate value to it
							result = "";
						}
						
						if (reached) { //see comment a couple lines above
							gate.setText(result);
							endsReached +=1;
						}
						
						ArrayList<Component> endPoints = new ArrayList<Component>();
						for (Component component : components) {
							if (component.type.equals("End")) {
								endPoints.add(component);
							}
						}
						if (endsReached >= endPoints.size()) { //if all the ends have been reached, it means your circuit ain't broken
							toBeReturned = 0; //success return value
							
						}
					
					}
				}
				else {
					;
				}
	
			
			}
			
		}
		return toBeReturned;
		
		
			
		
	}
	
	void saveBlackBox() throws IOException { //saves project as a black box
		
		if (simulate() == -1) { //Don't want to let users save broken black boxes
			System.out.println("You can't save that pathetic shell of a circuit");
			
		}
		else if (simulate() == 0) {
			save(true);  //saving black boxes and projects uses the same save() function.  The boolean argument designates
						//whether the statement "this is a black box save" is true or not
			
		}
		
		
	}
	
	void loadBlackBox() throws IOException, ClassNotFoundException { //loads black box from a .bb file for later use
		//new window formatting stuff...
		final JFrame loadInput = new JFrame("");
		loadInput.setSize(350,150);
		loadInput.setVisible(true);
		JPanel loadPanel = new JPanel();
		loadInput.add(loadPanel);
		final JTextField textfield = new JTextField(30);
		final JLabel loadPrompt = new JLabel("Choose a black box load point (don't put a file extension)");
		
		JButton loadButton = new JButton("Load");
		loadButton.setPreferredSize(new Dimension(100, 40));
		loadPanel.add(loadPrompt);
		loadPanel.add(textfield);
		
		loadPanel.add(loadButton, BorderLayout.PAGE_END);
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					loadBlackBoxFromFile(textfield.getText() + ".bb"); //load black box from the user typed path.  Extension added automatically
					loadInput.setVisible(false);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				
			}
		});
	}
	
	//this function takes a .bb save file and modifies the UserGate class so that subsequent UserGates are the ones in the .bb file
	void loadBlackBoxFromFile(String text) throws IOException, ClassNotFoundException { 
		ProjectState state;
		String loadpoint = text;
		FileInputStream fileIn = new FileInputStream(loadpoint);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		state = (ProjectState) in.readObject(); //everything gets saved as a serialized ProjectState, basically a summary of everything in the
												//program world.  This deserializes it
		in.close();
		fileIn.close();
		HashMap truthTable = new HashMap(); //to define the black box function, we make a truth table for it by simulating every
											//possible combo of inputs
		ArrayList<ArrayList<Integer>> possibleStarts = new ArrayList<ArrayList<Integer>>();
		
		int keepGoing = 0;
		ArrayList<Component> startPoints = new ArrayList<Component>();
		ArrayList<Component> endPoints = new ArrayList<Component>();
		for (Component gate : state.projectComponents) { //create arraylists of start and end points (useful later)
			if (gate.type.equals("Start")) { 
				startPoints.add(gate);
			}
			else if (gate.type.equals("End")) {
				endPoints.add(gate);
			}
		}
		for (int i = 0; i<Math.pow(2,  startPoints.size()); i++) { //if x inputs, then 2^x possible ways to have them
			//this loop basically goes through all the binary numbers (e.g 000, 001, 010, 011, 100) and then turns their
			// digits into an arraylist of values, which corresponds to a potential starting state of the program inputs
			
			int zerosNeeded = startPoints.size() - Integer.toBinaryString(i).length();
			String binaryRep = Integer.toBinaryString(i);
			for (int n = 0; n < zerosNeeded; n++) {
				
				binaryRep = "0" + binaryRep;
			}
			ArrayList<Integer> valueThings = new ArrayList<Integer>();
			System.out.println("binaryrepis " + binaryRep);
			for (int j = 0; j < binaryRep.length(); j++) {
				valueThings.add(Integer.parseInt(String.valueOf(binaryRep.charAt(j))));
			}
			possibleStarts.add(valueThings);
			
			
			
		}
		for (ArrayList<Integer> possibleStart : possibleStarts) { //do this for every possible combo of inputs
			int currentAssignedValue = 0;
			for (Component gate : state.projectComponents) { //assigns the aforementioned possible combo to the start gates
															//note - the way all this is done means that, for now, it is difficult
															//to make black boxes where the order of the inputs matters be useful
				if (gate.type.equals("Start")) {
					gate.value = possibleStart.get(currentAssignedValue);
					if (possibleStart.get(currentAssignedValue) == 0) {
						gate.outputs.get(0).value = false;
					}
					else {
						gate.outputs.get(0).value = true;
					}
					currentAssignedValue++;
				}
			}
			keepGoing=0;
			while (keepGoing<200) { //what follows is basically the simulate function all over again
				keepGoing++;
				System.out.println("infinite");
				int endsReached = 0;
			
				for (Component gate : state.projectComponents) {
					boolean act = true;
					ArrayList<Boolean> inputValues = new ArrayList<Boolean>();
					
					for (Input input : gate.inputs) {
						if (!(input.hasValue)) {
							act = false;
						}
						else {
							inputValues.add(input.value);
						}
					}
					if (act) {
						
						for (Output out : gate.outputs){
							out.value = gate.operation(inputValues);
							
							for (Input input : out.inputsReceivingThis) {
								
								input.value = out.value;
								input.hasValue = true;
							}
						}
						if (gate.type.equals("End")) {
							System.out.println("here'sanend");
							int result;
							System.out.println("testedvalue: " + gate.inputs.get(0).value);
							if (gate.inputs.get(0).value == false) {
								
								result = 0;
							}
							else {
								result = 1;
							}
							gate.value = result;
							endsReached +=1;
							
							if (endsReached >= endPoints.size()) {
								;
							}
						}
						
					}
					else {
						System.out.println("Your circuit is broken...");
					}
				}
				
			}
			ArrayList<Boolean> endValues = new ArrayList<Boolean>();
			for (Component end : endPoints) { //makes a boolean array of the ending values, because that's what the operation function
											//needs to output
				if (end.value == 0) {
					endValues.add(false);
				}
				else if (end.value == 1) { 
					endValues.add(true);
				}
			}
			ArrayList<Boolean> newPossibleStart = new ArrayList<Boolean>();
			for (Integer i : possibleStart) { //same as above, but with start values
				if (i == 0) {
					newPossibleStart.add(false);
				}
				else if (i == 1) {
					newPossibleStart.add(true);
				}
			}
			truthTable.put(newPossibleStart, endValues); //adds this correspondence of starting values and results to a hashmap
		
		}
		//What follows basically sets the UserGate class to be like the stuff in the hashmap
		UserGate.numberInputs = startPoints.size();
		UserGate.numberOutputs = endPoints.size();
		UserGate.theTruthTable = truthTable;
		String[] nameFromFile = loadpoint.split("\\\\");
		String theName = nameFromFile[nameFromFile.length - 1]; //extracts name of file from absolute file path
		theName = theName.replace(".bb", "");
		UserGate.name = theName;
		
		
	}
	
	void save(boolean thing) throws IOException { //save function, used by both black box saves and normal projects
		
		final boolean isBB = thing; //true if the thing being saved is a black box
		//new window formatting stuff below:
		final JFrame saveInput = new JFrame("");
		saveInput.setSize(350,150);
		saveInput.setVisible(true);
		JPanel savePanel = new JPanel();
		saveInput.add(savePanel);
		final JTextField textfield = new JTextField(30);
		final JLabel savePrompt = new JLabel("Choose a save point (don't put a file extension)");
		JButton saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(100, 40));
		
		savePanel.add(savePrompt);
		savePanel.add(textfield);
		savePanel.add(saveButton, BorderLayout.PAGE_END);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (isBB) { //depending on bb versus regular, the file extension differs
						saveToFile(textfield.getText() + ".bb");
					}
					else {
						saveToFile(textfield.getText() + ".txt");
					}
					saveInput.setVisible(false);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	void saveToFile(String text) throws IOException {
		//serializes the current ProjectState and saves it
		String savepoint = text;
		FileOutputStream fileOut = new FileOutputStream(savepoint);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		ProjectState state = new ProjectState();
		out.writeObject(state);
		out.close();
		fileOut.close();
				
	}
	
	void load() throws IOException, ClassNotFoundException {
	
		//new window formatting stuff:
		final JFrame loadInput = new JFrame("");
		loadInput.setSize(350,150);
		loadInput.setVisible(true);
		JPanel loadPanel = new JPanel();
		loadInput.add(loadPanel);
		final JTextField textfield = new JTextField(30);
		final JLabel loadPrompt = new JLabel("Choose a load point (don't put a file extension)");
		JButton loadButton = new JButton("Load");
		loadButton.setPreferredSize(new Dimension(100, 40));
		
		loadPanel.add(loadPrompt);
		loadPanel.add(textfield);
		loadPanel.add(loadButton, BorderLayout.PAGE_END);
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					loadFromFile(textfield.getText() + ".txt"); //auto adds .txt extension because not a black box.  Loads from
																//user typed path
					loadInput.setVisible(false);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				
			}
		});
	}
	void loadFromFile(String text) throws IOException, ClassNotFoundException {
		//sets stuff in the Main class to be like it is in the deserialized Project state, thus "loading" the file
		ProjectState state;
		String loadpoint = text;
		FileInputStream fileIn = new FileInputStream(loadpoint);
		ObjectInputStream in = new ObjectInputStream(fileIn); 
		state = (ProjectState) in.readObject(); //deserialization
		in.close();
		fileIn.close();
		drawPanel.removeAll(); //Hey user, Do you want to save your changes before loading?  To bad, I'm not going to ask you if you want to
		drawPanel.repaint();
		
		
		Main.components.remove(Main.components); //unnecessary, but I'd rather type this long comment than delete it
		Main.lines.remove(Main.lines); //same as above
		
		Main.components=state.projectComponents;
		Main.lines = state.projectLines;
		for (Component component : state.projectComponents) { //goes through each component, recreates associated lines	
			drawPanel.add(component);

		}
	}
		


	public static void main(String[] args) {
		
		new Main(); //starts this whole shabang
	}
}
