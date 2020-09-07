package examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import net.miginfocom.swing.MigLayout;

public class Example3 extends JComponent {

	/**
		 * 
		 */
	private static final long serialVersionUID = 4961943303303534846L;

	private final static Graph graph = new Graph();
	private static JFrame frame;
	private static JTextField textFieldEntry;
	private static Vertex active = null;
	final static Example3 comp = new Example3();

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
	private static JSlider slider_3 = new JSlider();
	private static JSlider slider_4 = new JSlider();
	private static Color color = Color.RED;
	private static int pointx = 0;
	private static int pointy = 0;
	private static int i = 0;

	public static void main(String[] args) {
		int scalex = 500;
		int scaley = 500;
		comp.setPreferredSize(new Dimension(scalex, scaley));
		comp.setBackground(Color.black);
		frame = new JFrame();
		frame.setBounds(100, 100, scalex, scaley);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(comp, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		textFieldEntry = new JTextField();
		textFieldEntry.setColumns(30);
		panel.add(textFieldEntry);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		graph.setAntialiasing(true);

		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				frame.repaint();
			}

		});
		comp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {

				if (me.isAltDown()) {
					graph.addVertex(new Vertex(me.getX() - 10, me.getY() - 10, "No_Name_" + i));
					i++;
				}
				if (!me.isShiftDown() && !me.isAltDown() &&!me.isControlDown()) {
					if (active != null) {
						active.setBackgroundColour(color);
						active.setX(pointx);
						active.setY(pointy);
					}

					active = graph.isThereVertex(new Point(me.getX(), me.getY()));

					if (active == null) {
						textField.setText("");
						textField_1.setText("");
						textField_5.setText("");
						textField_6.setText("");
						textField_2.setText("");
						textField_3.setText("");
						textField_4.setText("");
					}
					if (active != null) {
						color = active.getBackgroundColour();
						pointx = active.getX();
						pointy = active.getY();
						textField.setText("");
						textField_1.setText("");
						textField_5.setText("");
						textField_6.setText("");
						textField_2.setText("");
						textField_3.setText("");
						textField_4.setText("");
						textField.setText(active.getName());
						textField_1.setText(active.getRadius() + "");
						slider.setValue(active.getBackgroundColour().getRed());
						textField_2.setText(active.getBackgroundColour().getRed() + "");
						slider_1.setValue(active.getBackgroundColour().getGreen());
						textField_3.setText(active.getBackgroundColour().getGreen() + "");
						slider_2.setValue(active.getBackgroundColour().getBlue());
						textField_4.setText(active.getBackgroundColour().getBlue() + "");
						textField_5.setText(active.getX() + "");
						slider_3.setValue(active.getX());
						textField_6.setText(active.getY() + "");
						slider_4.setValue(active.getY());
						active.setBackgroundColour(Color.GREEN);
					}
				}
				if (me.isControlDown()) {
					if (graph.isThereVertex(new Point(me.getX(), me.getY())) != null) {
						if (active != null)
							if (active.equals(graph.isThereVertex(new Point(me.getX(), me.getY()))))
								active = null;
						graph.deleteVertex(graph.isThereVertex(new Point(me.getX(), me.getY())));
					}
				}
				if (me.isShiftDown()&&active != null) {
					if (graph.isThereVertex(new Point(me.getX(), me.getY())) != null) {
						graph.addEdges(active.getName() + "="
								+ graph.isThereVertex(new Point(me.getX(), me.getY())).getName());
					}
				}

				frame.repaint();
			}
		});
		JButton btnTop = new JButton("load");
		btnTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
				chooser.setFileFilter(xmlfilter);
				if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(frame)) {
					graph.importXML(chooser.getSelectedFile().getAbsolutePath());
					frame.repaint();
				}
			}
		});
		panel.add(btnTop);
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
				chooser.setFileFilter(xmlfilter);
				if (JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(frame)) {
					graph.toXML(chooser.getSelectedFile().getAbsolutePath());
					frame.repaint();
				}

			}
		});
		panel.add(btnSave);
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		JButton Organize = new JButton("Organize");
		Organize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetactive();
				graph.organize();
				frame.repaint();
			}
		});
		panel_2.add(Organize);
		JButton Circle = new JButton("Circle");
		Circle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetactive();
				graph.circleNodes();
				frame.repaint();
			}
		});
		panel_2.add(Circle);
		JButton btnAddLines = new JButton("Add");
		btnAddLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetactive();
				graph.addEdges(textFieldEntry.getText());
				frame.repaint();

			}
		});
		panel_2.add(btnAddLines);
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetactive();
				graph.reset();
			}
		});
		panel_2.add(btnReset);
		JButton btnDeleteV = new JButton("Delete V");
		btnDeleteV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetactive();
				graph.deleteVertex(new Vertex(0, 0, textFieldEntry.getText()));
				frame.repaint();

			}
		});
		panel_2.add(btnDeleteV);
		JButton btnDeleteLine = new JButton("Delete Line");
		btnDeleteLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetactive();
				String toParse[] = textFieldEntry.getText().split("=");
				if (toParse.length != 2) {
					toParse = textFieldEntry.getText().split("←");
				}
				if (toParse.length != 2) {
					toParse = textFieldEntry.getText().split("→");
				}
				if (toParse.length == 2) {
					graph.deleteEdge(new Edge(new Vertex(0, 0, toParse[0]), new Vertex(0, 0, toParse[1])));
					frame.repaint();
				}

			}
		});
		panel_2.add(btnDeleteLine);

		JPanel panel1 = new JPanel();
		frame.add(panel1, BorderLayout.EAST);
		panel1.setLayout(new MigLayout("", "[76.00px][94.00px,grow][28.00,grow]", "[23px][][][][][][][][][]"));
		JLabel lblJmeno = new JLabel("ID");
		panel1.add(lblJmeno, "cell 0 1,alignx trailing");
		textField = new JTextField();
		panel1.add(textField, "cell 1 1 2 1,growx,aligny center");
		textField.setColumns(10);
		JLabel lblNewLabel = new JLabel("Radius");
		panel1.add(lblNewLabel, "cell 0 2,alignx trailing");
		textField_1 = new JTextField();
		panel1.add(textField_1, "cell 1 2 2 1,growx");
		textField_1.setColumns(10);
		JLabel lblNewLabel_1 = new JLabel("Red color");
		panel1.add(lblNewLabel_1, "cell 0 3,alignx trailing");
		slider.setMaximum(255);
		panel1.add(slider, "flowx,cell 1 3");
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				textField_2.setText(((JSlider) ce.getSource()).getValue() + "");
				if (active != null && !textField_2.getText().equals("") && !textField_3.getText().equals("")
						&& !textField_4.getText().equals("")) {
					active.setBackgroundColour(new Color(Integer.parseInt(textField_2.getText()),
							Integer.parseInt(textField_3.getText()), Integer.parseInt(textField_4.getText())));
					frame.repaint();
				}

			}
		});
		textField_2 = new JTextField();
		textField_2.setText("255");
		panel1.add(textField_2, "cell 2 3,growx");
		textField_2.setColumns(10);
		JLabel lblNewLabel_2 = new JLabel("Green color");
		panel1.add(lblNewLabel_2, "cell 0 4,alignx trailing");
		slider_1.setMaximum(255);
		panel1.add(slider_1, "cell 1 4");
		slider_1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				textField_3.setText(((JSlider) ce.getSource()).getValue() + "");
				if (active != null && !textField_2.getText().equals("") && !textField_3.getText().equals("")
						&& !textField_4.getText().equals("")) {
					active.setBackgroundColour(new Color(Integer.parseInt(textField_2.getText()),
							Integer.parseInt(textField_3.getText()), Integer.parseInt(textField_4.getText())));
					frame.repaint();
				}
			}
		});
		textField_3 = new JTextField();
		textField_3.setText("255");
		panel1.add(textField_3, "cell 2 4,growx");
		textField_3.setColumns(10);
		JLabel lblNewLabel_3 = new JLabel("Blue color");
		panel1.add(lblNewLabel_3, "cell 0 5,alignx trailing");
		slider_2.setMaximum(255);
		panel1.add(slider_2, "cell 1 5");
		slider_2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				textField_4.setText(((JSlider) ce.getSource()).getValue() + "");
				if (active != null && !textField_2.getText().equals("") && !textField_3.getText().equals("")
						&& !textField_4.getText().equals("")) {
					active.setBackgroundColour(new Color(Integer.parseInt(textField_2.getText()),
							Integer.parseInt(textField_3.getText()), Integer.parseInt(textField_4.getText())));
					frame.repaint();
				}
			}
		});
		textField_4 = new JTextField();
		textField_4.setText("255");
		panel1.add(textField_4, "cell 2 5,growx");
		textField_4.setColumns(10);
		JLabel lblNewLabel_4 = new JLabel("Coordinates X");
		panel1.add(lblNewLabel_4, "cell 0 6,alignx trailing");
		textField_5 = new JTextField();
		panel1.add(textField_5, "cell 2 6,growx");
		textField_5.setColumns(10);
		slider_3.setMaximum(scalex);
		panel1.add(slider_3, "cell 1 6");
		slider_3.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				textField_5.setText(slider_3.getValue() + "");
				if (active != null) {
					active.setX(slider_3.getValue());
					frame.repaint();
				}

			}
		});
		JButton btnNewButton_1 = new JButton("Save");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (active != null) {
					active.setName(textField.getText());
					active.setRadius(Integer.parseInt(textField_1.getText()));
					active.setBackgroundColour(new Color(Integer.parseInt(textField_2.getText()),
							Integer.parseInt(textField_3.getText()), Integer.parseInt(textField_4.getText())));
					active.setX(Integer.parseInt(textField_5.getText()));
					active.setY(Integer.parseInt(textField_6.getText()));
					active = null;
					frame.repaint();
				}

			}
		});
		JLabel lblSouadniceY = new JLabel("Coordinates Y");
		panel1.add(lblSouadniceY, "cell 0 7,alignx trailing");
		textField_6 = new JTextField();
		panel1.add(textField_6, "cell 2 7,growx");
		textField_6.setColumns(10);
		panel1.add(btnNewButton_1, "cell 1 9,alignx left,aligny center");
		slider_4.setMaximum(scaley);
		panel1.add(slider_4, "cell 1 7");
		slider_4.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				textField_6.setText(slider_4.getValue() + "");
				if (active != null) {
					active.setY(slider_4.getValue());
					frame.repaint();
				}
			}
		});

		graph.addEdges("V0=V1,V0=V12");
		graph.addEdges("V0=V5");
		graph.addEdges("V0=V6");
		graph.addEdges("V5=V4");
		graph.addEdges("V12=V4");
		graph.addEdges("V12=V10");
		graph.addEdges("V10=V4");
		graph.addEdges("V10=V7");
		graph.addEdges("V1=V2");
		graph.addEdges("V2=V8");
		graph.addEdges("V2=V3");
		graph.addEdges("V3=V9");
		graph.addEdges("V3=V4");
		graph.addEdges("V4=V24");
		graph.addEdges("V24=V6");
		graph.addEdges("V6=V17");
		graph.addEdges("V17=V2");
		graph.addEdges("V2=V14");
		graph.addEdges("V14=V11");
		graph.addEdges("V11=V5");
		graph.addEdges("V5=V26");
		graph.addEdges("V5=V13");
		graph.addEdges("V13=V27");
		graph.addEdges("V27=V22");
		graph.addEdges("V22=V26");
		graph.addEdges("V26=V6");
		graph.addEdges("V22=V6");
		graph.addEdges("V22=V20");
		graph.addEdges("V20=V6");
		graph.addEdges("V6=V15");
		graph.addEdges("V6=V25");
		graph.addEdges("V6=V19");
		graph.addEdges("V6=V18");
		graph.addEdges("V6=V16");
		graph.addEdges("V6=V21");
		graph.addEdges("V6=V23");/*
									 * graph.addEdges("V0=V1,V1=V2"); graph.addEdges("V2=V3,V3=V4");
									 * graph.addEdges("V4=V5,V5←V0"); graph.addEdges("V0=V6,V1=V7");
									 * graph.addEdges("V2↔V8,V3=V9"); graph.addEdges("V4=V10,V5=V11");
									 */

		graph.getVertexes().get(2).setRadius(50);
		graph.organize();

		frame.pack();
		frame.setVisible(true);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		graph.setWindowSize(new Point(comp.getWidth(), comp.getHeight()));
		slider_3.setMaximum(comp.getWidth());
		slider_4.setMaximum(comp.getHeight());
		graph.graphToCenter();
		graph.paintGraph(g);
	}

	private static void resetactive() {
		if (active != null) {
			active.setBackgroundColour(color);
			active = null;
		}

	}

}