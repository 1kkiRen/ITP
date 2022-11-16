// public class task2 {
//     public static void main(String[] args) {
//         Rectangle rectangle = new Rectangle(10, 20);
//         System.out.println(rectangle.getArea());
//         System.out.println(rectangle.getPerimeter());

//         Square square = new Square(10);
//         System.out.println(square.getArea());
//         System.out.println(square.getPerimeter());

//         Circle circle = new Circle(10);
//         System.out.println(circle.getArea());
//         System.out.println(circle.getPerimeter());

//         Triangle triangle = new Triangle(10, 20, 30);
//         System.out.println(triangle.getArea());
//         System.out.println(triangle.getPerimeter());


        
//     }
// }


// class Shape{
//     float perimeter;
//     float area;

//     public float getPerimeter() {
//         return this.perimeter;
//     }

//     public float getArea() {
//         return this.area;
//     }

// }

// class Rectangle extends Shape{
//     float length;
//     float width;

//     public Rectangle(float length, float width){
//         this.length = length;
//         this.width = width;
//     }

//     public void getPerimeterVal(){
//         super.perimeter = 2 * (this.length + this.width);
//     }

//     public void getAreaVal(){
//         super.area = this.length * this.width;
//     }
// }

// class Square extends Shape{
//     float side;

//     public Square(float side){
//         this.side = side;
//     }

//     public void getPerimeterVal(){
//         super.perimeter = 4 * this.side;
//     }

//     public void getAreaVal(){
//         super.area = this.side * this.side;
//     }
// }

// class Circle extends Shape{
//     float radius;

//     public Circle(float radius){
//         this.radius = radius;
//     }

//     public void getPerimeterVal(){
//         super.perimeter = 2 * 3.14f * this.radius;
//     }

//     public void getAreaVal(){
//         super.area = 3.14f * this.radius * this.radius;
//     }
// }

// class Triangle extends Shape{
//     float base;
//     float height;

//     public Triangle(float base, float height){
//         this.base = base;
//         this.height = height;
//     }

//     public void getPerimeterVal(){
//         super.perimeter = 3 * this.base;
//     }

//     public void getAreaVal(){
//         super.area = 0.5f * this.base * this.height;
//     }
// }

// class Ellipse extends Shape{
//     float majorAxis;
//     float minorAxis;

//     public Ellipse(float majorAxis, float minorAxis){
//         this.majorAxis = majorAxis;
//         this.minorAxis = minorAxis;
//     }

//     public void getPerimeterVal(){
//         super.perimeter = 2 * 3.14f * (float)Math.sqrt((this.majorAxis * this.majorAxis + this.minorAxis * this.minorAxis) / 2);
//     }

//     public void getAreaVal(){
//         super.area = 3.14f * this.majorAxis * this.minorAxis;
//     }
// }