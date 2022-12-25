package lab13;

import java.util.*;

public class Main {
    static Set<Animal> animals = new HashSet<>();

    public static void main(String[] args) {
        Dog dog = new Dog("Dog");
        Cat cat = new Cat("Cat");
        Animal anim = new Animal("Safy");

        addAnimals(dog);
        addAnimals(cat);
        addAnimals(anim);

        for (Animal animal:
             animals) {
            makeTalk(animal);
        }
    }

    private static void addAnimals(Animal animal){
        animals.add(animal);
    }

    private static void makeTalk(Animal animal){
        animal.voice();
    }
}

class Animal{
    String nickname;

    public Animal(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){
        return nickname;
    }

    public void voice(){
        System.out.println("Animal " + nickname + "  take a voice");
    }
}

class Cat extends Animal{

    float profoundness;
    float barking;

    public Cat(String nickname){
        super(nickname);
    }

    @Override
    public void voice(){
        System.out.println("Cat " + nickname + " take a voice");
    }
}

class Dog extends Animal{

    float profoundness;
    float barking;

    public Dog(String nickname){
        super(nickname);
    }

    @Override
    public void voice(){
        System.out.println("Dog " + nickname + "  take a voice");
    }
}