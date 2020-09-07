package examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

import graph.Graph;

public class manual extends JComponent {
	static int scalex = 500;
	static int scaley = 500;
	/**
		 * 
		 */
	private static final long serialVersionUID = 4961943303303534846L;

	// vytvoření a inicializace objektu
	private final static Graph graph = new Graph();

	private static JFrame frame;

	public static void main(String[] args) {

		final manual comp = new manual();
		comp.setPreferredSize(new Dimension(scalex, scaley));
		frame = new JFrame();
		frame.setBounds(100, 100, scalex, scaley);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(comp, BorderLayout.CENTER);

		
		// povolení vyhlazování
		graph.setAntialiasing(true);
		// vytvoření vrcholů a hran
		graph.addEdges("V0=V1,V1=V2,V2=V3,V0=V3,V0←V4");
		// nastavení optimální délky hrany
		graph.setDistance(250);
		// změna barvy Vertexu
		graph.getVertexByID("V4").setBackgroundColour(Color.GREEN);
		// změna barvy hrany
		graph.getEdgeByID("V4=V0").setColor(Color.red);
		// organizace
		graph.organize();

		frame.pack();
		frame.setVisible(true);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// vykreslení grafu
		graph.paintGraph(g);
	}

}