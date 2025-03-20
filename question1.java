
import java.util.ArrayList;
import java.util.List;

class question1 {

    static interface Shape {

        enum ShapeType {
            SQUARE, RECTANGLE, CIRCLE, TRIANGLE, REGULAR_POLYGON
        }

        abstract int getArea();

        abstract int getPerimeter();

        abstract Point getOriginOfShape();

        abstract boolean isPointEnclosed(Point point);

    }

    static class Rectangle implements Shape {

        int length;
        int breadth;
        Point point;

        public Rectangle(int length, int breadth, Point point) {
            this.point = point;
            this.length = length;
            this.breadth = breadth;
        }

        public int getArea() {
            return length * breadth;
        }

        public int getPerimeter() {
            return 2 * (length + breadth);
        }

        public Point getOriginOfShape() {
            return point;
        }

        public boolean isPointEnclosed(Point check) {

            // in the range hona chaoye uss shape ke
            return check.getX() >= point.getX()
                    && check.getX() <= point.getX() + length
                    && check.getY() >= point.getY()
                    && check.getY() <= point.getY() + breadth;
        }
    }

    static class Square implements Shape {

        int side;
        Point point;

        public Square(int side, Point point) {
            this.side = side;
            this.point = point;
        }

        public int getArea() {
            return side * side;
        }

        public int getPerimeter() {
            return 4 * side;
        }

        public Point getOriginOfShape() {
            return point;
        }

        public boolean isPointEnclosed(Point check) {

            return check.getX() >= point.getX()
                    && check.getX() <= point.getX() + side
                    && check.getY() >= point.getY()
                    && check.getY() <= point.getY() + side;
        }
    }

    static class Circle implements Shape {

        int radius;
        Point point;

        public Circle(int radius, Point point) {
            this.radius = radius;
            this.point = point;
        }

        public int getArea() {
            return (int) 3.14 * radius * radius;
        }

        public int getPerimeter() {
            return (int) 12.52 * radius;
        }

        public Point getOriginOfShape() {
            return point;
        }

        public boolean isPointEnclosed(Point check) {
            return check.distanceFromCenter(point) <= radius;
        }
    }

    static class Triangle implements Shape {

        int height;
        int base;
        Point point;

        public Triangle(int height, int base, Point point) {
            this.height = height;
            this.base = base;
            this.point = point;
        }

        public int getArea() {
            return  base * height/2;
        }

        public int getPerimeter() {

            Point peak = new Point(point.x + base / 2, point.y + height);
            Point nonPeak = new Point(point.x + base, point.y);

            double sideA = Math.sqrt((nonPeak.x - point.x) * (nonPeak.x - point.x) + (nonPeak.y - point.y) * (nonPeak.y - point.y));
            double sideB = Math.sqrt((peak.x - point.x) * (peak.x - point.x) + (peak.y - point.y) * (peak.y - point.y));
            double sideC = Math.sqrt((nonPeak.x - peak.x) * (nonPeak.x - peak.x) + (nonPeak.y - peak.y) * (nonPeak.y - peak.y));

            return (int)(sideA + sideB + sideC);
        }

        public Point getOriginOfShape() {
            return point;
        }

        public boolean isPointEnclosed(Point check) {
            return check.getX() >= point.getX()
                    && check.getX() <= point.getX() + base
                    && check.getY() >= point.getY()
                    && check.getY() <= point.getY() + height;
        }

    }

    static class Point {

        private int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public double distanceFromCenter(Point p) {
            return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
        }
    }

    static class ShapeFactory {

        public static Shape createShape(Shape.ShapeType type, Point point, List<Integer> listOfParameters) {

            if (type == Shape.ShapeType.RECTANGLE) {
                return new Rectangle(listOfParameters.get(0), listOfParameters.get(1), point);
            } else if (type == Shape.ShapeType.CIRCLE) {
                return new Circle(listOfParameters.get(0), point);
            } else if (type == Shape.ShapeType.SQUARE) {
                return new Square(listOfParameters.get(0), point);
            } else {
                return new Triangle(listOfParameters.get(0), listOfParameters.get(1), point);
            }
        }
    }

    static class Screen {

        int XMax;
        int YMax;
        List<Shape> listOfShapes = new ArrayList<>();

        public Screen(int XMax, int YMax) {
            this.XMax = XMax;
            this.YMax = YMax;
        }

        // add the specific shape
        public void addShape(Shape shape1) {
            listOfShapes.add(shape1);
        }

        // delete the specific shape
        public void deleteShape(Shape shape1) {
            listOfShapes.remove(shape1);
        }

        // delete the same type of Shapes
        public void deleteAllShapes(Shape.ShapeType type) {    // <- we will send the enum
            for (Shape sh : listOfShapes) {
                if (sh.getClass().getSimpleName().toUpperCase().equals(type.name())) {
                    listOfShapes.remove(sh);
                }
            }
        }
    }

    public static void main(String[] args) {

        Screen newScreen = new Screen(100, 100);

        Point rectPoint = new Point(10, 10);
        List<Integer> rectangleParimeters = List.of(20, 30);
        Shape rectangle = ShapeFactory.createShape(Shape.ShapeType.RECTANGLE, rectPoint, rectangleParimeters);

        Point squarePoint = new Point(30, 30);
        List<Integer> squareParimeters = List.of(4);
        Shape square = ShapeFactory.createShape(Shape.ShapeType.SQUARE, squarePoint, squareParimeters);

        Point circlePoint = new Point(55, 55);
        List<Integer> circleParimeters = List.of(10);
        Shape circle = ShapeFactory.createShape(Shape.ShapeType.CIRCLE, circlePoint, circleParimeters);

        Point trianglePoint = new Point(80, 80);
        List<Integer> triangleParimeters = List.of(10, 10);
        Shape triangle = ShapeFactory.createShape(Shape.ShapeType.TRIANGLE, trianglePoint, triangleParimeters);

        newScreen.addShape(square);
        newScreen.addShape(rectangle);
        newScreen.addShape(circle);

        System.out.println("********************Square************************");
        System.out.println("Program checking starts");

        System.out.println("The origin of the square is: " + square.getOriginOfShape().x + "," + square.getOriginOfShape().y);

        System.out.println("Checking the point pt(35,35) enclosing...........");
        Point pt = new Point(35, 35);

        boolean checking = square.isPointEnclosed(pt);
        System.out.println("So is point pt is enclosed ? : " + checking);

        System.out.println("Area of the square of side " + squareParimeters.get(0) + " is: " + square.getArea());
        System.out.println("Perimeter of the square of side " + squareParimeters.get(0) + " is: " + square.getPerimeter());

        System.out.println("********************Rectangle********************");

        System.out.println("Rectangle analysis: ");
        System.out.println("The origin of the Rectangle is: " + rectangle.getOriginOfShape().x + "," + rectangle.getOriginOfShape().y);

        System.out.println("Checking the point pt(35,35) enclosing................");
        Point p = new Point(15, 15);
        boolean checkin = rectangle.isPointEnclosed(p);
        System.out.println("So is point pt is enclosed ? : " + checkin);

        System.out.print("Area of the square of length: " + rectangleParimeters.get(0) + " & breadth: " + rectangleParimeters.get(1) + " is: ");
        System.out.println(rectangle.getArea());

        System.out.println("Perimeter of the rectangle is: " + rectangle.getPerimeter());

        System.out.println("****************Circle************************");

        System.out.println("Circle analysis: ");
        System.out.println("The origin of the Circle is: " + circle.getOriginOfShape().x + "," + circle.getOriginOfShape().y);

        System.out.println("Checking the point pt(60,60) enclosing................");
        Point origin = new Point(60, 60);
        boolean circleChecking = circle.isPointEnclosed(origin);
        System.out.println("So is point pt is enclosed ? : " + circleChecking);

        System.out.println("Area of the Circle of radius: " + circleParimeters.get(0) + " is: " + circle.getArea());

        System.out.println("Perimeter of the Circle is: " + circle.getPerimeter());

        System.out.println("******************Triangle**********************");

        System.out.println("Triangle analysis: ");
        System.out.println("The origin of the TRIANGLE is: " + triangle.getOriginOfShape().x + "," + triangle.getOriginOfShape().y);

        System.out.println("Checking the point pt(85,85) enclosing................");
        Point tempPoint = new Point(85, 85);
        boolean triangleChecking = triangle.isPointEnclosed(tempPoint);
        System.out.println("So is point pt is enclosed ? : " + triangleChecking);

        System.out.println("Area of the Triangle of radius is: " + triangle.getArea());

        System.out.println("Perimeter of the Triangle is: " + triangle.getPerimeter());

    }
}
