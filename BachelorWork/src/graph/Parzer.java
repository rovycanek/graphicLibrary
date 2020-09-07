package graph;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parzer {
	public void toXML(Graph graf, String path) {
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
		try {
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			Element graph = doc.createElement("Graph");
			doc.appendChild(graph);

			graph.appendChild(processEdges(doc, graf));
			graph.appendChild(processNodes(doc, graf));
			// output DOM XML to console
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult console = new StreamResult(new File(path));
			transformer.transform(source, console);

			//System.out.println("\nXML DOM Created Successfully..");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Node processEdges(Document doc, Graph graph) {
		Element edges = doc.createElement("Edges");
		// append child elements to root element
		for (Edge line : graph.getEdges()) {
			edges.appendChild(getEdge(doc, line));
		}
		return edges;
	}

	private static Node getEdge(Document doc, Edge edge) {
		Element line = doc.createElement("Edge");
		line.setAttribute("ID", edge.getBegin().getName() + edge.direction + edge.getEnd().getName());
		line.setAttribute("RGB",
				edge.getColor().getRed() + ", " + edge.getColor().getGreen() + ", " + edge.getColor().getBlue());
		line.setAttribute("scale", "" + edge.getArrowScale());
		line.setAttribute("dashed", "" + edge.isDashed());
		return line;
	}

	private static Node processNodes(Document doc, Graph graph) {
		Element lines = doc.createElement("Vertexes");
		// append child elements to root element
		for (Vertex node : graph.getVertexes()) {
			lines.appendChild(getNode(doc, node));
		}
		return lines;
	}

	private static Node getNode(Document doc, Vertex node) {
		Element vertex = doc.createElement("Vertex");
		vertex.setAttribute("X", "" + node.getX());
		vertex.setAttribute("Y", "" + node.getY());
		vertex.setAttribute("ID", node.getName());
		vertex.setAttribute("RGB", node.getBackgroundColour().getRed() + ", " + node.getBackgroundColour().getGreen()
				+ ", " + node.getBackgroundColour().getBlue());
		vertex.setAttribute("radius", "" + node.getRadius());
		return vertex;
	}

	public void edgesFromString(Graph graf, String string) {
		String array[];
		string.replaceAll("\\s+", "");
		array = string.split(",");
		for (String a : array) {
			graf._addEdge(a);
		}
	}
	public void vertexesFromString(Graph graf, String string) {
		String array[];
		string.replaceAll("\\s+", "");
		array = string.split(",");
		for (String a : array) {
			graf.addVertex(a);
		}
	}

	public void importXML(Graph graph, String adresa) {
		graph.reset();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			DocumentBuilder builder;

			builder = factory.newDocumentBuilder();

			Document doc;
			doc = builder.parse(new File(adresa));

			NodeList nodeList = doc.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				if (nodeList.item(i).getNodeName() == "Edges") {
					for (int j = 0; j < nodeList.item(i).getChildNodes().getLength(); j++) {
						if (nodeList.item(i).getChildNodes().item(j).getNodeName() == "Edge") {
							Element element = (Element) nodeList.item(i).getChildNodes().item(j);
							if (element.hasAttribute("ID")) {
								graph.addEdges(element.getAttribute("ID"));
								if (element.hasAttribute("scale")) {
									graph.getEdges().getLast()
											.setArrowScale(Integer.parseInt(element.getAttribute("scale")));
								}
								if (element.hasAttribute("RGB")) {
									String RGB = element.getAttribute("RGB");
									RGB = RGB.replaceAll("\\s+", "");
									String array[] = RGB.split(",");
									Color color = new Color(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
											Integer.parseInt(array[2]));
									graph.getEdges().getLast().setColor(color);
								}
								if (element.hasAttribute("dashed")) {
									if(element.getAttribute("dashed").equals("true"))
										graph.getEdges().getLast().setDashed(true);
									if(element.getAttribute("dashed").equals("false"))
										graph.getEdges().getLast().setDashed(false);
								}
							}

						}
					}

				}
				if (nodeList.item(i).getNodeName() == "Vertexes") {
					for (int j = 0; j < nodeList.item(i).getChildNodes().getLength(); j++) {
						if (nodeList.item(i).getChildNodes().item(j).getNodeName() == "Vertex") {
							Element element = (Element) nodeList.item(i).getChildNodes().item(j);
							if (element.hasAttribute("ID")) {
								String ID = element.getAttribute("ID");
								if (graph.getVertexes().contains(new Vertex(0, 0, ID))) {
									if (element.hasAttribute("RGB")) {
										String RGB = element.getAttribute("RGB");
										RGB = RGB.replaceAll("\\s+", "");
										String array[] = RGB.split(",");
										Color color = new Color(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
												Integer.parseInt(array[2]));
										graph.getVertexByID(ID).setBackgroundColour(color);
												
									}
									if (element.hasAttribute("X")) {
										graph.getVertexByID(ID).setX(Integer.parseInt(element.getAttribute("X")));
									}
									if (element.hasAttribute("Y")) {
										graph.getVertexByID(ID).setY(Integer.parseInt(element.getAttribute("Y")));

									}
									if (element.hasAttribute("radius")) {
										graph.getVertexByID(ID).setRadius(Integer.parseInt(element.getAttribute("radius")));
									}
								}
								Point coordinates=graph._generateVertexCoords();
								int x;
								int y;
								Color color;
								if (element.hasAttribute("RGB")) {
									String RGB = element.getAttribute("RGB");
									RGB = RGB.replaceAll("\\s+", "");
									String array[] = RGB.split(",");
									color = new Color(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
											Integer.parseInt(array[2]));
								} else {
									color = Color.BLACK;
								}
								if (element.hasAttribute("X")) {
									x = Integer.parseInt(element.getAttribute("X"));
								} else {
									x = (int) coordinates.getX();
								}
								if (element.hasAttribute("Y")) {
									y = Integer.parseInt(element.getAttribute("Y"));

								} else {
									y = (int) coordinates.getY();
								}

								graph.addVertex(new Vertex(x, y, ID, color));
								if (element.hasAttribute("radius")) {
									graph.getVertexByID(ID).setRadius(Integer.parseInt(element.getAttribute("radius")));
								}
							}

						}
					}

				}
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
