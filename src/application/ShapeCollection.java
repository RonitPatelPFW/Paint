package application;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ShapeCollection implements Serializable {
	
//Creating an ArrayList for each Shape	
	private ArrayList<Line> lines;
	private ArrayList<Rectangle> rectangles;
	private ArrayList<Ellipse> ellipses;
//Instantiating each arrayList
	public ShapeCollection() {
		
		lines = new ArrayList<>();
		rectangles = new ArrayList<>();
		ellipses = new ArrayList<>();
	}
//Method for adding shape to ArrayList
	public void addShape(Shape shape) {
		if (shape instanceof Line) {
			lines.add((Line) shape);
		} else if (shape instanceof Rectangle) {
			rectangles.add((Rectangle) shape);
		} else if (shape instanceof Ellipse) {
			ellipses.add((Ellipse) shape);
		}
	}
//Getter for lines ArrayList
	public ArrayList<Line> getLines() {
		return lines;
	}
//Getter for Rectangle ArrayList
	public ArrayList<Rectangle> getRectangles() {
		return rectangles;
	}
//Getter for Ellipse ArrayList
	public ArrayList<Ellipse> getEllipses() {
		return ellipses;
	}
//Getter to return all ArrayList combined into one
	public ArrayList<Shape> getAllShapes() {
		ArrayList<Shape> shape = new ArrayList<Shape>();
		shape.addAll(lines);
		shape.addAll(rectangles);
		shape.addAll(ellipses);

		return shape;
		
	}
}