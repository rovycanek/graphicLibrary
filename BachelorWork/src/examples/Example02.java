package examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import graph.Graph;
import graph.Vertex;
import net.miginfocom.swing.MigLayout;

public class Example02 extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private final static Graph graph = new Graph();
	final static Example02 example02 = new Example02();
	private static Vertex active=null;
	private static Color color=Color.RED;
	private static JTextField textField;
	private static JTextField textField_1;
	private static JTextField textField_5;
	private static JTextField textField_6;
	private static JTextField textField_2;
	private static JTextField textField_3;
	private static JTextField textField_4;
	private static JSlider slider = new JSlider();
	private static JSlider slider_1 = new JSlider();
	private static JSlider slider_2 = new JSlider();

	public static void main(String[] args) {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		example02.setPreferredSize(new Dimension(500, 500));
		frame.getContentPane().add(example02, BorderLayout.WEST);
		
		graph.setAntialiasing(true);
		graph.setWindowSize(new Point(500,500));
		graph.setDistance(100);
		
		example02.addMouseListener(new MouseAdapter() { 
	        public void mousePressed(MouseEvent me) {
	        	Vertex previous=active;
	        	if(previous!=null)
	        		previous.setBackgroundColour(color);
	    		active=graph.isThereVertex(new Point(me.getX(), me.getY()));

		  		  if(previous==null){ 
	        		textField.setText("");
	        		textField_1.setText("");
	        		textField_5.setText("");
	        		textField_6.setText("");
	        		textField_2.setText("");
	        		textField_3.setText("");
	        		textField_4.setText("");
	        		slider.setValue(0);
	        		slider_1.setValue(0);
	        		slider_2.setValue(0);
	        	  }
	        	  if(active!=null){ 
	        		color=active.getBackgroundColour();
	        		
	        		textField.setText(active.getName());
	        		textField_1.setText(active.getRadius()+"");
	        		slider.setValue(active.getBackgroundColour().getRed());
	        		textField_2.setText(active.getBackgroundColour().getRed()+"");
	        		slider_1.setValue(active.getBackgroundColour().getGreen());
	        		textField_3.setText(active.getBackgroundColour().getGreen()+"");
	        		slider_2.setValue(active.getBackgroundColour().getBlue());
	        		textField_4.setText(active.getBackgroundColour().getBlue()+"");
	        		textField_5.setText(active.getX()+"");
	        		textField_6.setText(active.getY()+"");
	        		active.setBackgroundColour(Color.GREEN);
	        	  }
	  	  
	  	  frame.repaint();
	    } 
	  });  	
		graph.addEdges("V0=V1,V1=V2");
		graph.addEdges("V2=V3,V3=V4");
		graph.addEdges("V4=V5,V5←V0");
		graph.addEdges("V0=V6,V1=V7");
		graph.addEdges("V2↔V8,V3=V9");
		graph.addEdges("V4=V10,V5=V11");
		graph.organize(600);
		graph.graphToCenter();
		

		JPanel panel = new JPanel();
		frame.add(panel, BorderLayout.EAST);
		panel.setLayout(new MigLayout("", "[76.00px][94.00px,grow][28.00,grow]", "[23px][][][][][][][][][]"));
		JLabel lblJmeno = new JLabel("ID");
		panel.add(lblJmeno, "cell 0 1,alignx trailing");
		textField = new JTextField();
		panel.add(textField, "cell 1 1 2 1,growx,aligny center");
		textField.setColumns(10);
		JLabel lblNewLabel = new JLabel("Radius");
		panel.add(lblNewLabel, "cell 0 2,alignx trailing");
		textField_1 = new JTextField();
		panel.add(textField_1, "cell 1 2 2 1,growx");
		textField_1.setColumns(10);
		JLabel lblNewLabel_1 = new JLabel("Red color");
		panel.add(lblNewLabel_1, "cell 0 3,alignx trailing");
		slider.setMaximum(255);
		panel.add(slider, "flowx,cell 1 3");
		slider.addChangeListener(new ChangeListener() {
		        @Override
		        public void stateChanged(ChangeEvent ce) {
		            textField_2.setText(((JSlider)ce.getSource()).getValue()+"");
		        }
		    });
		textField_2 = new JTextField();
		textField_2.setText("255");
		panel.add(textField_2, "cell 2 3,growx");
		textField_2.setColumns(10);
		JLabel lblNewLabel_2 = new JLabel("Green color");
		panel.add(lblNewLabel_2, "cell 0 4,alignx trailing");
		slider_1.setMaximum(255);
		panel.add(slider_1, "cell 1 4");
		slider_1.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent ce) {
	            textField_3.setText(((JSlider)ce.getSource()).getValue()+"");
	        }
	    });
		textField_3 = new JTextField();
		textField_3.setText("255");
		panel.add(textField_3, "cell 2 4,growx");
		textField_3.setColumns(10);
		JLabel lblNewLabel_3 = new JLabel("Blue color");
		panel.add(lblNewLabel_3, "cell 0 5,alignx trailing");
		slider_2.setMaximum(255);
		panel.add(slider_2, "cell 1 5");
		slider_2.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent ce) {
	            textField_4.setText(((JSlider)ce.getSource()).getValue()+"");
	        }
	    });
		textField_4 = new JTextField();
		textField_4.setText("255");
		panel.add(textField_4, "cell 2 5,growx");
		textField_4.setColumns(10);
		JLabel lblNewLabel_4 = new JLabel("Coordinates X");
		panel.add(lblNewLabel_4, "cell 0 6,alignx trailing");
		textField_5 = new JTextField();
		panel.add(textField_5, "cell 1 6 2 1,growx");
		textField_5.setColumns(10);
		JButton btnNewButton_1 = new JButton("Save");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		
					if(active!=null) {
						active.setName(textField.getText());
						active.setRadius(Integer.parseInt(textField_1.getText()));
						active.setBackgroundColour(new Color(Integer.parseInt(textField_2.getText()), Integer.parseInt(textField_3.getText()), Integer.parseInt(textField_4.getText())));
						active.setX(Integer.parseInt(textField_5.getText()));
						active.setY(Integer.parseInt(textField_6.getText()));
						active=null;
						frame.repaint();
					}

				
			}
		});
		JLabel lblSouadniceY = new JLabel("Coordinates Y");
		panel.add(lblSouadniceY, "cell 0 7,alignx trailing");
		textField_6 = new JTextField();
		panel.add(textField_6, "cell 1 7 2 1,growx");
		textField_6.setColumns(10);
		panel.add(btnNewButton_1, "cell 1 9,alignx left,aligny center");
	
		

		
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// vykreslení grafu
		graph.setWindowSize(new Point(example02.getWidth(),example02.getHeight()));
		graph.paintGraph(g);
	}
	
}
