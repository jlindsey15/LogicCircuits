package jack.logicsimulator;

import jack.logicsimulator.Component;
import jack.logicsimulator.Connector;

import java.awt.Color;
import java.awt.Polygon;

public class Input extends Connector {//Input class - type of connector
	
	Input(int ID, Component owner, int x, int y) {
		super(ID,owner, x, y); //adding to connector code, not replacing
		isInput = true;
		owner.addInput(this);
		if (owner.type.equals("Start")) {
			owner.addtoToggles(this); //if it's an "Input" on a Start "gate", it should be treated as a toggle switch
		}
		maxConnections = 1; // only one connection can go to an input
		
		//defines look of the input:
		Polygon p = new Polygon();
		p.addPoint(x, y - h / 2);
		p.addPoint(x+w, y-h/2);
		p.addPoint(x+ w, y + h / 2);
		p.addPoint(x, y + h/2);
		shape = p;
		color = Color.blue;
	}
}
