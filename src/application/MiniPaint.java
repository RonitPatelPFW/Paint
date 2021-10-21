package application;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class MiniPaint extends Application implements Serializable {
	int redValue;
	int greenValue;
	int blueValue;
	
	ShapeCollection shapeCollection = new ShapeCollection();

	@Override
	public void start(Stage primaryStage) {
// Controls and Layout nodes
		BorderPane bPane = new BorderPane();
		Pane pane = new Pane();

		Button save = new Button("Save");		
		Button open = new Button("Open");
		
		RadioButton drawBtn = new RadioButton("Draw");
		RadioButton moveBtn = new RadioButton("Move");
		RadioButton deleteBtn = new RadioButton("Delete");

		ToggleGroup toggle = new ToggleGroup();

		ComboBox<String> selectType = new ComboBox<String>();

		Slider red = new Slider(0.0, 255.0, 0.0);
		Slider green = new Slider(0.0, 255.0, 0.0);
		Slider blue = new Slider(0.0, 255.0, 0.0);

		Text redText = new Text("Red");
		Text greenText = new Text("Green");
		Text blueText = new Text("Blue");
		Text currentColor = new Text("Current: ");
		Text toolText = new Text("Tool");
		Text colorText = new Text("Color");

		Ellipse colorEllipse = new Ellipse();
//Color slider event listener
		red.valueProperty().addListener((observeable, oldValue, newValue) -> {

			redValue = (int) red.getValue();

			colorEllipse.setFill(Color.rgb(redValue, greenValue, blueValue));

		});
		green.valueProperty().addListener((observeable, oldValue, newValue) -> {

			greenValue = (int) green.getValue();

			colorEllipse.setFill(Color.rgb(redValue, greenValue, blueValue));

		});
		blue.valueProperty().addListener((observeable, oldValue, newValue) -> {

			blueValue = (int) blue.getValue();

			colorEllipse.setFill(Color.rgb(redValue, greenValue, blueValue));

		});
//Setting radio buttons to a toggleGroup
		drawBtn.setToggleGroup(toggle);
		moveBtn.setToggleGroup(toggle);
		deleteBtn.setToggleGroup(toggle);
//Adding items to comboBox
		selectType.getItems().addAll("Line", "Rectangle", "Ellipse");
//Setting the CSS id for the Text node
		toolText.setId("tool-Text");
		colorText.setId("tool-Text");
//Setting X and Y location of the colorEllipse
		colorEllipse.setRadiusX(15);
		colorEllipse.setRadiusY(10);
//Mouse Event Handler when creating shape
		EventHandler<MouseEvent> createShape = event -> {

			String selectedShape = selectType.getSelectionModel().getSelectedItem();
			
			if (selectedShape.equals("Line")) {

				Line line = new Line();
				line.setStrokeWidth(5);
				line.setStartX(event.getX());
				line.setStartY(event.getY());
							
				line.setStroke(colorEllipse.getFill());

				shapeCollection.addShape(line);
				pane.getChildren().add(line);


			}

			if (selectedShape.equals("Rectangle")) {

				Rectangle rect = new Rectangle();
				rect.setX(event.getX());
				rect.setY(event.getY());
				
				rect.setFill(colorEllipse.getFill());

				shapeCollection.addShape(rect);
				pane.getChildren().add(rect);

			}

			if (selectedShape.equals("Ellipse")) {

				Ellipse ell = new Ellipse();
				ell.setCenterX(event.getX());
				ell.setCenterY(event.getY());
			
				
				ell.setFill(colorEllipse.getFill());

				shapeCollection.addShape(ell);
				pane.getChildren().add(ell);

			}
			
			
		};
//Mouse Event Handler dragging the shape
		EventHandler<MouseEvent> dragShape = event -> {

			String selectedShape = selectType.getSelectionModel().getSelectedItem();

			if (selectedShape.equals("Line")) {

				Line line = shapeCollection.getLines().get(shapeCollection.getLines().size() - 1);
				line.setEndX(event.getX());
				line.setEndY(event.getY());
				
			}

			if (selectedShape.equals("Rectangle")) {

				Rectangle rect = shapeCollection.getRectangles().get(shapeCollection.getRectangles().size() - 1);
				rect.setWidth(event.getX() - rect.getX());
				rect.setHeight(event.getY() - rect.getY());

			}

			if (selectType.getValue().equals("Ellipse")) {

				Ellipse ell = shapeCollection.getEllipses().get(shapeCollection.getEllipses().size() - 1);
				ell.setRadiusX(event.getX() - ell.getCenterX());
				ell.setRadiusY(event.getY() - ell.getCenterY());

			}

		};
//Mouse Event Handler for moving a shape
		EventHandler<MouseEvent> moveShape = event -> {

			if (event.getTarget() instanceof Line) {

				int indexLine = shapeCollection.getLines().indexOf(event.getTarget());

				shapeCollection.getLines().get(indexLine).setEndX(event.getX());
				shapeCollection.getLines().get(indexLine).setEndY(event.getY());
				
			}

			if (event.getTarget() instanceof Rectangle) {

				int indexRect = shapeCollection.getRectangles().indexOf(event.getTarget());

				shapeCollection.getRectangles().get(indexRect).setX(event.getX());
				shapeCollection.getRectangles().get(indexRect).setY(event.getY());

			}

			if (event.getTarget() instanceof Ellipse) {

				int indexEll = shapeCollection.getEllipses().indexOf(event.getTarget());

				shapeCollection.getEllipses().get(indexEll).setCenterX(event.getX());
				shapeCollection.getEllipses().get(indexEll).setCenterY(event.getY());

			}
		};
//Mouse Event Handler for deleting a shape
		EventHandler<MouseEvent> deleteShape = event -> {

			if (event.getTarget() instanceof Line) {

				int indexLine = shapeCollection.getLines().indexOf(event.getTarget());

				pane.getChildren().remove(event.getTarget());
				shapeCollection.getLines().remove(indexLine);
			}

			if (event.getTarget() instanceof Rectangle) {

				int indexRect = shapeCollection.getRectangles().indexOf(event.getTarget());

				pane.getChildren().remove(event.getTarget());
				shapeCollection.getRectangles().remove(indexRect);

			}

			if (event.getTarget() instanceof Ellipse) {

				int indexEll = shapeCollection.getEllipses().indexOf(event.getTarget());

				pane.getChildren().remove(event.getTarget());
				shapeCollection.getEllipses().remove(indexEll);

			}

		};
//Action Event for enabling draw event
		EventHandler<ActionEvent> enableDrawEvent = event -> {

			selectType.setDisable(false);
			pane.addEventHandler(MouseEvent.MOUSE_PRESSED, createShape);
			pane.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragShape);

		};
//Action Event for disabling draw event
		EventHandler<ActionEvent> disableDrawEvent = event -> {

			selectType.setDisable(true);
			pane.removeEventHandler(MouseEvent.MOUSE_PRESSED, createShape);
			pane.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragShape);

		};
//Action event for enabling move event
		EventHandler<ActionEvent> enableMoveEvent = event -> {

			pane.addEventHandler(MouseEvent.MOUSE_DRAGGED, moveShape);

		};
//Action event for disabling move event
		EventHandler<ActionEvent> disableMoveEvent = event -> {

			pane.removeEventHandler(MouseEvent.MOUSE_DRAGGED, moveShape);

		};
//Action event for enabling delete event
		EventHandler<ActionEvent> enableDeleteEvent = event -> {

			pane.addEventHandler(MouseEvent.MOUSE_CLICKED, deleteShape);

		};
//Action event for disabling delete event
		EventHandler<ActionEvent> disableDeleteEvent = event -> {

			pane.removeEventHandler(MouseEvent.MOUSE_CLICKED, deleteShape);

		};
		
		save.setOnAction(event -> {
			
			try {
				
				FileOutputStream dataOutput = new FileOutputStream("Shape.dat");
				ObjectOutputStream objectOutput = new ObjectOutputStream(dataOutput);
				
				objectOutput.writeObject(pane.getChildren().size());
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		});
		
		open.setOnAction(event -> {
			
			try {
				FileInputStream fileInput = new FileInputStream("Shape.dat");
				ObjectInputStream objectInput = new ObjectInputStream(fileInput);
				
				shapeCollection = (ShapeCollection) objectInput.readObject();
				
				pane.getChildren().add(colorEllipse);
				
				
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		});
		
		
//Adding specific Event Handlers for each radio button
		drawBtn.addEventHandler(ActionEvent.ACTION, enableDrawEvent);
		drawBtn.addEventHandler(ActionEvent.ACTION, disableMoveEvent);
		drawBtn.addEventHandler(ActionEvent.ACTION, disableDeleteEvent);

		moveBtn.addEventHandler(ActionEvent.ACTION, enableMoveEvent);
		moveBtn.addEventHandler(ActionEvent.ACTION, disableDrawEvent);
		moveBtn.addEventHandler(ActionEvent.ACTION, disableDeleteEvent);

		deleteBtn.addEventHandler(ActionEvent.ACTION, enableDeleteEvent);
		deleteBtn.addEventHandler(ActionEvent.ACTION, disableMoveEvent);
		deleteBtn.addEventHandler(ActionEvent.ACTION, disableDrawEvent);
//Adding nodes to VBox or HBox
		HBox hBox = new HBox(10, currentColor, colorEllipse);
		VBox vBox = new VBox(10, toolText, drawBtn, moveBtn, deleteBtn, selectType, colorText, redText, red, greenText,
				green, blueText, blue, hBox, save, open);
//Setting layout container locations in window
		bPane.setLeft(vBox);
		bPane.setCenter(pane);
		vBox.setPadding(new Insets(10));
		vBox.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//Adding a new scene and showing the GUI
		Scene scene = new Scene(bPane, 600, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Definitly Not Paint");
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}