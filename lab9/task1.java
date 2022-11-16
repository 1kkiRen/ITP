public class task1 {
    public static void main(String [] args){
                Cow cow = new Cow("Moo", 100, 100, "White");
        cow.make_sound();
        cow.eat();
        cow.sleep();

        Dog dog = new Dog("Woof", 50, 50, "Black");
        dog.make_sound();
        dog.eat();
        dog.sleep();

        Cat cat = new Cat("Meow", 25, 25, "Grey");
        cat.make_sound();
        cat.eat();
        cat.sleep();

        Duck duck = new Duck("Quack", 10, 10, "Yellow");
        duck.make_sound();
        duck.eat();
        duck.sleep();
    }
}

class Animal{
    String name;
    int height;
    int weight;
    String color;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Animal(String name, int height, int weight, String color){
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.color = color;
    }

    public void eat(){
        System.out.println(this.name + " is eating");
    }

    public void sleep(){
        System.out.println(this.name + " is sleeping");
    }

    public void make_sound(){
        System.out.println(this.name + " is making sound");
    }
}


class Cow extends Animal{
    public Cow(String name, int height, int weight, String color){
        super(name, height, weight, color);
    }

    public void make_sound(){
        System.out.println(this.name + " is making sound: moo");
    }
}

class Cat extends Animal{
    public Cat(String name, int height, int weight, String color){
        super(name, height, weight, color);
    }

    public void make_sound(){
        System.out.println(this.name + " is making sound: meow");
    }
}


class Dog extends Animal{
    public Dog(String name, int height, int weight, String color){
        super(name, height, weight, color);
    }

    public void make_sound(){
        System.out.println(this.name + " is making sound: woof");
    }
}

class Duck extends Animal{
    public Duck(String name, int height, int weight, String color){
        super(name, height, weight, color);
    }

    public void make_sound(){
        System.out.println(this.name + " is making sound: honk");
    }
}