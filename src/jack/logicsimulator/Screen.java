package jack.logicsimulator;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.*;
import java.io.*;

public class Screen extends JPanel { //the "canvas" for everything
	Screen() {
		setDoubleBuffered(true); //may or may not be necessary, can't hurt though
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { //clicking on the screen  does different things depending on mode
				if (Main.mode.equals("addingAnd")) { //adds and gate at click point
					Component c = new AndGate(Main.currentComponentID, e.getX(), e.getY());
					;
					
					Main.mode = "";
					Main.header.setText("Do something!");
					Main.drawPanel.repaint();
				}
				
				else if (Main.mode.equals("addingOr")) {// adds or gate at click point
					Component c = new OrGate(Main.currentComponentID, e.getX(), e.getY());
					;
					Main.mode = "";
					Main.header.setText("Do something!");
					Main.drawPanel.repaint();
				}
				
				else if (Main.mode.equals("addingNot")) {//adds not gate at click point
					Component c = new NotGate(Main.currentComponentID, e.getX(), e.getY());
					
					
					Main.mode = "";
					Main.header.setText("Do something!");
					Main.drawPanel.repaint();
				}
				
				
				else if (Main.mode.equals("addingStart")) {//adds start gate at click point
				   Component c = new StartPoint(Main.currentComponentID, e.getX(), e.getY());
				   
				  
				   Main.mode = "";
				   Main.header.setText("Do something!");
				   Main.drawPanel.repaint();
				   
				}
				
				else if (Main.mode.equals("addingEnd")) { //adds end gate at click point
					   Component c = new EndPoint(Main.currentComponentID, e.getX(), e.getY());
					   
					   
					   Main.mode = "";
					   Main.header.setText("Do something!");
					   Main.drawPanel.repaint();
					   
				}
				
				else if (Main.mode.equals("addingBlackBox")) { //adds user black box gate at click point
					Component c = new UserGate(Main.currentComponentID, e.getX(), e.getY());
					Main.mode = "";
					Main.header.setText("Do something!");
					Main.drawPanel.repaint();
				}
			
			}
		});
	}
	@Override
	public void paintComponent(Graphics g) { //called whenever you call drawPanel.repaint()
		super.paintComponent(g); //paints the drawPanel and associated components
		//graphics stuff:
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		System.out.println(Main.lines.size());
		for (Connection line : Main.lines) { //paints all the lines in the world
			line.paintConnection(g2d);
		}
	}
	
	
}
