import java.util.Random;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class SuperCircle {
	private Random gen = new Random();
	private Circle circle;
	private int radius;
	private double centerX = MainDriver.WINDOW_CENTER_X;
	private double centerY = MainDriver.WINDOW_CENTER_Y;
	private Point center = new Point();

	private circlePoint[] points = new circlePoint[3];
	private Line[] lines = new Line[3];

	public SuperCircle() {
		radius = 50;
		init();
	}

	public SuperCircle(int radius) {
		this.radius = radius;
		init();
	}

	public DoubleProperty getRadiusProperty() {
		return circle.radiusProperty();
	}

	private void init() {
		circle = new Circle(centerX, centerY, radius);
		circle.setFill(Color.TRANSPARENT);
		circle.setStroke(Color.BLACK);
		circle.setStrokeWidth(2);
	}

	public Group getCircle() {
		Group tmp = new Group(circle);

		for (int i = 0; i < 3; i++)
			points[i] = genPoint();

		for (int i = 0; i < 3; i++) {
			lines[i] = new Line();
			lines[i].startXProperty().bind(points[i].getXProperty());
			lines[i].startYProperty().bind(points[i].getYProperty());
			
			if (i == 2) {
				lines[i].endXProperty().bind(points[0].getXProperty());
				lines[i].endYProperty().bind(points[0].getYProperty());
			} 
			else {
				lines[i].endXProperty().bind(points[i + 1].getXProperty());
				lines[i].endYProperty().bind(points[i + 1].getYProperty());
			}

		}
		tmp.getChildren().addAll(lines);
		for (int i = 0; i < 3; i++) 
			tmp.getChildren().add(points[i].getCircle());
		
		return tmp;
	}

	private circlePoint genPoint() {
		double tmpX = gen.nextInt(radius * 2) - radius;
		double tmpY = circlePoint.genYCoord(tmpX, radius);
		if (tmpX % 2 != 0)
			tmpY = tmpY * -1;
		return new circlePoint(tmpX, tmpY, radius);
	}

	public void updatePos(double newCenterX, double newCenterY) {
		centerX = MainDriver.WINDOW_CENTER_X;
		centerY = MainDriver.WINDOW_CENTER_Y;

		if (MainDriver.WINDOW_SIZE_X < MainDriver.WINDOW_SIZE_Y)
			radius = (int) (MainDriver.WINDOW_SIZE_X * MainDriver.CIRCLE_WINDOW_RATIO / 2);
		else
			radius = (int) (MainDriver.WINDOW_SIZE_Y * MainDriver.CIRCLE_WINDOW_RATIO / 2);

		circle.setCenterX(centerX);
		circle.setCenterY(centerY);
		circle.setRadius(radius);
		
		if(radius < 50)
			circlePoint.setRadius(4);
		else
			circlePoint.setRadius(6);


		for (circlePoint p : points)
			p.updatePos(radius);
	}
	
	
	
	public void showDebug() {
		
	}

}
