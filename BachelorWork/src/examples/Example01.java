package examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

import graph.Graph;
import graph.Vertex;

public class Example01 extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private final static Graph graph = new Graph();
	final static Example01 example01 = new Example01();
	private static Vertex active=null;
	static int i=0;
	public static void main(String[] args) {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		example01.setPreferredSize(new Dimension(500, 500));
		frame.getContentPane().add(example01, BorderLayout.CENTER);
		
		graph.setAntialiasing(true);
		graph.setWindowSize(new Point(400,400));
		graph.setDistance(250);
		
		example01.addMouseListener(new MouseAdapter() { 
	          public void mousePressed(MouseEvent me) { 
	        	  if(me.isAltDown()) {
	        		  graph.addVertex(new Vertex(me.getX()-10, me.getY()-10, "V"+i));
	        		  i++;
	        	  }
        	  	  if(!me.isShiftDown()&&!me.isAltDown()) {
        	  		  if(active!=null){ 
	              		  active.setBackgroundColour(Color.RED);
	              	  }
	              	  active=graph.isThereVertex(new Point(me.getX(), me.getY()));
	              	  if(active!=null){ 
	              		  active.setBackgroundColour(Color.GREEN);
	              	  }
	        	  }
        	  	  if(me.isControlDown()) {
        	  		if(graph.isThereVertex(new Point(me.getX(), me.getY()))!=null){
        	  				graph.deleteVertex(graph.isThereVertex(new Point(me.getX(), me.getY())));  	  		  
	  				}
        	  	  }
        	  	  if(me.isShiftDown()&&active!=null) {
        	  		  if(graph.isThereVertex(new Point(me.getX(), me.getY()))!=null){
        	  			  graph.addEdges(active.getName()+"="+graph.isThereVertex(new Point(me.getX(), me.getY())).getName());
        	  		  }
        	  	  }
	        	  frame.repaint();
	          } 
	        }); 		
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// vykreslení grafu
		graph.setWindowSize(new Point(example01.getWidth(),example01.getHeight()));
		graph.paintGraph(g);
	}
	
	
}
