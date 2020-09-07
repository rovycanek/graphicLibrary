package graph;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
public class Vertex {

	private Point center;
    private Color backgroundColour;
    private int radius = 20;
    String name;
	private Point2D.Float Velocity, netForce;
	private boolean isLocked;
	
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public Point2D getVelocity() {
		return Velocity;
	}
	public void setVelocity(Point2D.Float velocity) {
		Velocity = velocity;
	}
	public Point2D.Float getNetForce() {
		return netForce;
	}
	public void setNetForce(Point2D.Float netForce) {
		this.netForce = netForce;
	}

	public Vertex Isposition(Point position) {
		Point vertexCenter = new Point(center.x+radius/2,center.y+radius/2);
		if(vertexCenter.distance(position)<radius) {
			return this;
		}
		return null;
	}
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public Vertex(int x, int y, Color backgroundColour) {
    	center = new Point(x,y);
        this.backgroundColour = backgroundColour;
        name="A1";
        Velocity=new Point2D.Float(0,0);
        netForce=new Point2D.Float(0,0);
        isLocked=false;
    }
    public Vertex(int x, int y,String name , Color backgroundColour) {
    	center = new Point(x,y);
        this.backgroundColour = backgroundColour;
        this.name=name;
        Velocity=new Point2D.Float(0,0);
        netForce=new Point2D.Float(0,0);
        isLocked=false;
    }
    
    public Vertex(int x, int y,String name) {
    	center = new Point(x,y);
        this.backgroundColour = Color.RED;
        this.name=name;
        Velocity=new Point2D.Float(0,0);
        netForce=new Point2D.Float(0,0);
        isLocked=false;
    }

    public void drawVertex(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(getX()-1, getY()-1, radius+2, radius+2);
        g.setColor(backgroundColour);
        g.fillOval(getX(), getY(), radius, radius);
        g.setColor(Color.black);
        g.drawString(name,getX()+radius,getY()+radius);

    }
	public int getX() {
		return (int) center.getX();

	}
	public void setX(int x) {
		this.center.setLocation(x, getY());
	}
	public int getY() {
		return (int) center.getY();
	}
	public void setY(int y) {
		this.center.setLocation(getX(), y);
	}
	public Color getBackgroundColour() {
		return backgroundColour;
	}
	public void setBackgroundColour(Color backgroundColour) {
		this.backgroundColour = backgroundColour;
	}
	public int getRadius() {
		return radius;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Vertex other = (Vertex) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Vertex [center=" + center + ", backgroundColour=" + backgroundColour + ", radius=" + radius + ", name="
				+ name + ", Velocity=" + Velocity + ", netForce=" + netForce + ", isLocked=" + isLocked + "]";
	}

}