package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.LinkedList;


public class Graph {
	private LinkedList<Edge> edges = new LinkedList<Edge>();
	private LinkedList<Vertex> vertexes = new LinkedList<Vertex>();
	private final Parzer parzer=new Parzer();
	private int distance = 50;
	private Point windowSize = new Point(500,500);
	private boolean antialiasing = false;

 	private void _organize() {
		if (vertexes.size() == 0) {
			return;
		}


		for (Vertex vertex : vertexes) {
			vertex.setNetForce(new Point2D.Float(0, 0));

			for (Vertex vertexTemp : vertexes) {
				if (vertex.equals(vertexTemp)) {
					// neprovádíme pro totožné vertexy
				} else {
					// výpočet síly pro vrcholy;
					vertex.setNetForce(new Point2D.Float((float)(vertex.getNetForce().x + distance
							* (vertex.getX()+vertex.getRadius()/2 - vertexTemp.getX()-vertexTemp.getRadius()/2)
							/ Point.distanceSq(vertex.getX()+vertex.getRadius()/2, vertex.getY()+vertex.getRadius()/2, vertexTemp.getX()+vertexTemp.getRadius()/2, vertexTemp.getY()+vertexTemp.getRadius()/2)),
							(float) (vertex.getNetForce().y +  distance * (vertex.getY()+vertex.getRadius()/2 - vertexTemp.getY()-vertexTemp.getRadius()/2)
									/ Point.distanceSq(vertex.getX()+vertex.getRadius()/2, vertex.getY()+vertex.getRadius()/2, vertexTemp.getX()+vertexTemp.getRadius()/2, vertexTemp.getY()+vertexTemp.getRadius()/2))));
				}
			}
			for (Vertex vertexTemp : vertexes) {
				if (edges.contains(new Edge(vertex, vertexTemp)) || edges.contains(new Edge(vertexTemp, vertex))) {
					vertex.setNetForce(new Point2D.Float(
							vertex.getNetForce().x + (float) (0.06* (vertexTemp.getX()+vertexTemp.getRadius()/2 - vertex.getX()-vertex.getRadius()/2)),
							vertex.getNetForce().y + (float) (0.06* (vertexTemp.getY()+vertexTemp.getRadius()/2 - vertex.getY()-vertex.getRadius()/2))));
				}
			}

			// spočítá rychlost (tlumení 0.85)
			vertex.setVelocity(
					new Point2D.Float((float) ((vertex.getVelocity().getX() + vertex.getNetForce().x) * 0.85),
							(float) ((vertex.getVelocity().getY() + vertex.getNetForce().y) * 0.85)));
		}
		for (Vertex vertex : vertexes) {

				vertex.setX((int) (vertex.getX() + (int) vertex.getVelocity().getX()));
				vertex.setY((int) (vertex.getY() + (int) vertex.getVelocity().getY()));

		}

	}
	
	public LinkedList<Edge> getEdges() {
		return edges;
	}
	public LinkedList<Vertex> getVertexes() {
		return vertexes;
	}

	public Point getWindowSize() {
		return windowSize;
	}
	public void setWindowSize(Point windowSize) {
		this.windowSize = windowSize;
	}
	

	public void setAntialiasing(boolean smoothing) {
		this.antialiasing = smoothing;
	}

	public void addEdges(String edge) {
			parzer.edgesFromString(this, edge);
		}
	public void addVertexes(String vertex) {
		parzer.vertexesFromString(this, vertex);
	}
	public void reset() {
		edges = new LinkedList<Edge>();
		vertexes = new LinkedList<Vertex>();
	}
	public void _addEdge(String edge) {
		String parts[];
		parts=edge.split("=", 2);
		if(parts.length==2) {
			_addEdge(parts[0], parts[1], '=');		
			}
		parts=edge.split("←", 2);
		if(parts.length==2) {
			_addEdge(parts[0], parts[1], '←');
		}
		parts=edge.split("→", 2);
		if(parts.length==2) {
			_addEdge(parts[0], parts[1], '→');
		}
		parts=edge.split("↔", 2);
		if(parts.length==2) {
			_addEdge(parts[0], parts[1], '↔');
		}
		
		}

	public Point _generateVertexCoords() {
		Vertex testing=null;
		Point point=new Point((int) (Math.random()*windowSize.getX()),(int)  (Math.random()*windowSize.getY()));
		for (Vertex vertex : vertexes) {
			if(vertex.Isposition(point)!=null)
				testing=vertex.Isposition(point);
		}
		if(testing==null) {
			return point;
		}else {
			while (testing!=null){
				testing=null;
				point=new Point((int) (Math.random()*windowSize.getX()),(int)  (Math.random()*windowSize.getY()));
				for (Vertex vertex : vertexes) {
					if(vertex.Isposition(point)!=null)
						testing=vertex.Isposition(point);
				}
			}
		}
		return point;
		
	}
	public void _addEdge(String begin, String end, char direction) {
		
		addVertex(addVertex(begin));
		
		addVertex(addVertex(end));
		if(!edges.contains(new Edge(getVertexByID(begin), getVertexByID(end), direction, Color.BLACK)))
			edges.add(new Edge(getVertexByID(begin),getVertexByID(end), direction, Color.BLACK));
	}
	public Vertex addVertex(String id) {
		Point newPoint=_generateVertexCoords();
		return addVertex(new Vertex(newPoint.x, newPoint.y, id));
	}
	
	
	public Vertex addVertex(Vertex vertex) {
		if (this.vertexes.contains(vertex)) {

		} else {
			this.vertexes.add(vertex);

		}
		return this.vertexes.get(this.vertexes.indexOf(vertex));
	}
	public Vertex getVertexByID(String ID) {
		if(this.vertexes.indexOf(new Vertex(0, 0, ID))!=-1)
			return this.vertexes.get(this.vertexes.indexOf(new Vertex(0, 0, ID)));
		else 
			return null;
	}
	public Edge getEdgeByID(String ID) {
		String parts[];
		parts=ID.split("=", 2);
		if(parts.length==2) {
			if (edges.contains(new Edge(new Vertex(0, 0, parts[0]), new Vertex(0, 0, parts[1])))) {
				return edges.get(edges.indexOf(new Edge(new Vertex(0, 0, parts[0]), new Vertex(0, 0, parts[1]))));
			}
		}
		parts=ID.split("�?", 2);
		if(parts.length==2) {
			if (edges.contains(new Edge(new Vertex(0, 0, parts[0]), new Vertex(0, 0, parts[1])))) {
				return edges.get(edges.indexOf(new Edge(new Vertex(0, 0, parts[0]), new Vertex(0, 0, parts[1]))));
			}
		}
		parts=ID.split("→", 2);
		if(parts.length==2) {
			if (edges.contains(new Edge(new Vertex(0, 0, parts[0]), new Vertex(0, 0, parts[1])))) {
				return edges.get(edges.indexOf(new Edge(new Vertex(0, 0, parts[0]), new Vertex(0, 0, parts[1]))));
			}
		}
		parts=ID.split("↔", 2);
		if(parts.length==2) {
			if (edges.contains(new Edge(new Vertex(0, 0, parts[0]), new Vertex(0, 0, parts[1])))) {
				return edges.get(edges.indexOf(new Edge(new Vertex(0, 0, parts[0]), new Vertex(0, 0, parts[1]))));
			}
		}
	return null;
		
	}
	
 	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void graphToCenter() {
		if(vertexes.size()>0) {
		int left, right, top, bottom;
		right = left = vertexes.getFirst().getX()+vertexes.getFirst().getRadius()/2;
		top = bottom = vertexes.getFirst().getY()+vertexes.getFirst().getRadius()/2;
		for (Vertex vertex : vertexes) {
			if (vertex.getX()+vertexes.getFirst().getRadius()/2 > right) {
				right = vertex.getX()+vertexes.getFirst().getRadius()/2;
			}
			if (vertex.getX() +vertexes.getFirst().getRadius()/2< left) {
				left = vertex.getX()+vertexes.getFirst().getRadius()/2;
			}
			if (vertex.getY()+vertexes.getFirst().getRadius()/2 > bottom) {
				bottom = vertex.getY()+vertexes.getFirst().getRadius()/2;
			}
			if (vertex.getY()+vertexes.getFirst().getRadius()/2 < top) {
				top = vertex.getY()+vertexes.getFirst().getRadius()/2;
			}
		}
		for (Vertex vertex : vertexes) {
			vertex.setX(vertex.getX() - (left + (right - left) / 2 - windowSize.x / 2));
			vertex.setY(vertex.getY() - (top + (bottom - top) / 2 - windowSize.y / 2));
		}
		}

	}
	public void organize() {
		organize(200);
	}
	public void organize(int iterations) {
		for (int i = 0; i < iterations; i++) {
			_organize();
		}
		graphToCenter();
	}
	public void circleNodes() {

		float x, y;
		int a = 0;
		float step =  (float)360.0 / vertexes.size();
		for (Vertex vertex : vertexes) {
			x = (int) (distance*4 * Math.cos(step*a * (Math.PI / 180)) );
			y = (int) (distance*4 * Math.sin(step*a * (Math.PI / 180)) );
			a ++;
			vertex.setX((int) x-vertex.getRadius()/2);
			vertex.setY((int) y-vertex.getRadius()/2);
		}
		graphToCenter();
	}

	public void paintGraph(Graphics g) {
		if (antialiasing) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			
		}
		for (Vertex vertex : vertexes) {
			vertex.drawVertex(g);
		}
		for (Edge line : edges) {
			line.drawLine(g);
		}
	}

	public void toXML(String path) {
		parzer.toXML(this, path);
	}
	public void importXML(String path) {
		parzer.importXML(this, path);
	}

	public void deleteVertex(Vertex vertex) {
		LinkedList<Edge> toRemove = new LinkedList<Edge>();
		if(vertexes.contains(vertex)) {
			for (Edge line : edges) {
				if(line.getBegin().equals(vertex)||line.getEnd().equals(vertex)) {
					toRemove.add(line);
				}
			}
			for(Edge line: toRemove) {
				edges.remove(line);
			}
			vertexes.remove(vertexes.indexOf(vertex));
		}
	}
	public void deleteEdge(Edge edge) {
		if(edges.contains(edge)) {
			edges.remove(edge);
		}
	}
	
	public Vertex isThereVertex(Point point) {
		Vertex testing=null;
		for (Vertex vertex : vertexes) {
			if(vertex.Isposition(point)!=null) {
				testing=vertex.Isposition(point);
			}
		}
		return testing;
	}

	@Override
	public String toString() {
		return "Graph [edges=" + edges + ", vertexes=" + vertexes + ", parzer=" + parzer + ", distance=" + distance
				+ ", windowSize=" + windowSize + ", antialiasing=" + antialiasing + "]";
	}
	
}
