package graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

public class Edge {
	Vertex begin;
	Vertex end;
	char direction = '=';
	Color color;
	int arrowScale = 10;
	boolean dashed = false;

	public boolean isDashed() {
		return dashed;
	}

	public void setDashed(boolean dashed) {
		this.dashed = dashed;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Vertex getBegin() {
		return begin;
	}

	public void setBegin(Vertex begin) {
		this.begin = begin;
	}

	public Vertex getEnd() {
		return end;
	}

	public void setEnd(Vertex end) {
		this.end = end;
	}

	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}

	public int getArrowScale() {
		return arrowScale;
	}

	public void setArrowScale(int arrowScale) {
		this.arrowScale = arrowScale;
	}

	public Color getColor() {
		return color;
	}

	public Edge(Vertex begin, Vertex end, Color color) {
		this.begin = begin;
		this.end = end;
		this.color = color;
	}

	public Edge(Vertex begin, Vertex end, char direction, Color color) {
		this.begin = begin;
		this.end = end;
		this.color = color;
		this.direction = direction;
	}
	public Edge(Vertex begin, Vertex end, char direction, Color color, boolean dashed) {
		this.begin = begin;
		this.end = end;
		this.color = color;
		this.direction = direction;
		this.dashed=dashed;
	}

	public Edge(Vertex begin, Vertex end) {
		this.begin = begin;
		this.end = end;
		this.color = Color.BLACK;
		this.direction = '=';
	}

	private void drawArrowHead(Graphics g2, int x1, int y1, int x2, int y2, Color color) {
		double phi = Math.toRadians(40);
		int x, y;
		int dy = y1 - y2;
		int dx = x1 - x2;
		double theta = Math.atan2(dy, dx);
		double rho = theta + phi;
		for (int j = 0; j < 2; j++) {
			x = (int) (x1 - arrowScale * Math.cos(rho));
			y = (int) (y1 - arrowScale * Math.sin(rho));
			g2.setColor(color);
			g2.drawLine(x1, y1, x, y);
			rho = theta - phi;
		}
	}
	
	public void drawLine(Graphics g) {
		Point point = new Point(end.getX()+end.getRadius()/2, end.getY()+end.getRadius()/2);
		float delka=(float)point.distance(begin.getX()+begin.getRadius()/2, begin.getY()+begin.getRadius()/2);
		
		
		float smernicex = (((float)end.getX() +(float) end.getRadius() / (float)2) - ((float)begin.getX() + (float)begin.getRadius() / (float)2)) /(float)delka;
		float smernicey = (((float)end.getY() + (float)end.getRadius() / (float)2) - ((float)begin.getY() + (float)begin.getRadius() / (float)2)) /(float)delka;
		int x1 = (int) (begin.getX() + (float)begin.getRadius() / (float)2 + (float)begin.getRadius()*(float)smernicex/(float)2);
		int y1 = (int) (begin.getY() + (float)begin.getRadius() / (float)2 + (float)begin.getRadius()*(float)smernicey/(float)2);
		int x2 = (int) (end.getX() + (float)end.getRadius() / (float)2 - (float)end.getRadius()*(float)smernicex/(float)2);
		int y2 = (int) (end.getY() + (float)end.getRadius() / (float)2 - (float)end.getRadius()*(float)smernicey/(float)2);

		if(dashed) {
			Graphics2D g2d = (Graphics2D) g.create();
	        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
	        g2d.setStroke(dashed);
	        g2d.setColor(color);
	        g2d.drawLine(x1, y1, x2, y2);

	        g2d.dispose();
		}else {

			g.setColor(color);
			g.drawLine(x1, y1, x2, y2);
		}
		
		if (direction == '=') {

		} else if (direction == '←') {
			drawArrowHead(g, x1, y1, x2, y2, color);
		} else if (direction == '→') {
			drawArrowHead(g, x2, y2, x1, y1, color);
		}else if (direction == '↔') {
			drawArrowHead(g, x1, y1, x2, y2, color);
			drawArrowHead(g, x2, y2, x1, y1, color);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((begin == null) ? 0 : begin.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (begin == null) {
			if (other.begin != null)
				return false;
		} 
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!(begin.equals(other.begin)&&end.equals(other.end)||begin.equals(other.end)&&end.equals(other.begin)))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Edge [begin=" + begin + ", end=" + end + ", direction=" + direction + ", color=" + color
				+ ", arrowScale=" + arrowScale + ", dashed=" + dashed + "]";
	}
	
}
