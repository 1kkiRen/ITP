package ass5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.sound.midi.Receiver;

enum AnimalSound {
    BOAR("Oink"),
    LION("Roar"),
    ZEBRA("Ihoho");

    private String sound;

    AnimalSound(String sound) {
        this.sound = sound;
    }

    public String getSound() {
        return sound;
    }
}

abstract class Animal {
    private float weight;
    private float speed;
    private float energy;

    protected Animal(float weight, float speed, float energy) {
        this.weight = weight;
        this.speed = speed;
        this.energy = energy;
    }

    public void makeSound() {
        System.out.println("Animal sound");
    }

    public void decrementEnergy() {
        this.energy -= 1;
    }

    public abstract void eat(List<Animal> animals, Field field);

    public float getWeight() {
        return weight;
    }

    public float getSpeed() {
        return speed;
    }

    public float getEnergy() {
        return energy;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

}


class Field  {
    private float grassAmount;

    private float MAX_GRASS_AMOUNT = 100;

    public Field(float grassAmount) {
        this.grassAmount = grassAmount;
    }

    public void growGrass() {
        this.grassAmount *= 2;
        if (this.grassAmount > MAX_GRASS_AMOUNT) {
            this.grassAmount = MAX_GRASS_AMOUNT;
        }
    }

    public float getGrassAmount() {
        return grassAmount;
    }

    public void setGrassAmount(float grassAmount) {
        this.grassAmount = grassAmount;
    }
}


interface Carnivore {
    public <T> Animal choosePrey(List<Animal> animals, T prey);
    public void huntPrey(Animal prey, Animal predator);
}

interface Herbivore {
    public void grazeInTheField(Animal animal, Field field);
}

class Lion extends Animal implements Carnivore {
    public Lion(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }

    @Override
    public void makeSound() {
        System.out.println(AnimalSound.LION.getSound());
    }

    @Override
    public void eat(List<Animal> animals, Field field) {
        Animal prey = choosePrey(animals, this);

        if (prey != null) {
            huntPrey(prey, this);
        } else {
            makeSound();
        }
 
    }

    @Override
    public <T> Animal choosePrey(List<Animal> animals, T predator) {
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getSpeed() == this.getSpeed() && animals.get(i).getEnergy() == this.getEnergy()
             && animals.get(i).getWeight() == this.getWeight() && animals.get(i).getClass() == this.getClass()) {
                if (i + 1 >= animals.size()){
                    if (animals.get(0).getClass().equals(predator)) {
                        CanibalismExeption.getMessage("Canibalism is not allowed");
                        return null;
                    } else {
                        return animals.get(0);
                    }
                } else {
                    if (animals.get(i + 1).getClass().equals(predator)) {
                        CanibalismExeption.getMessage("Canibalism is not allowed");
                        return null;
                    } else {
                        animals.get(i + 1);
                    }
                }
            }
        }
        
        return null;
    }

    @Override
    public void huntPrey(Animal prey, Animal predator) {
        if (prey.getSpeed() < predator.getSpeed() || prey.getEnergy() < predator.getEnergy()
         || predator.getEnergy() == 100) {
            predator.setEnergy(predator.getEnergy() + prey.getWeight());
            prey.setEnergy(0);
        } else {
            TooStrongPreyExeption.getMessage("The prey is too strong or too fast to attack");
        }

    }
}

class Boar extends Animal implements Omnivore {
    public Boar(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }

    @Override
    public void makeSound() {
        System.out.println(AnimalSound.BOAR.getSound());
    }

    @Override
    public void eat(List<Animal> animals, Field field) {
        Animal prey = choosePrey(animals, this);
        if (field.getGrassAmount() >= getWeight() / 10) {
            grazeInTheField(this, field);
        } else {
            if (prey != null) {
                huntPrey(prey, this);
            } else {
                makeSound();
            }
        }
    }

    @Override
    public <T> Animal choosePrey(List<Animal> animals, T predator) {
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getSpeed() == this.getSpeed() && animals.get(i).getEnergy() == this.getEnergy()
             && animals.get(i).getWeight() == this.getWeight() && animals.get(i).getClass() == this.getClass()) {
                if (i + 1 >= animals.size()){
                    if (animals.get(0).getClass().equals(predator)) {
                        CanibalismExeption.getMessage("Canibalism is not allowed");
                        return null;
                    } else {
                        return animals.get(0);
                    }
                } else {
                    if (animals.get(i + 1).getClass().equals(predator)) {
                        CanibalismExeption.getMessage("Canibalism is not allowed");
                        return null;
                    } else {
                        animals.get(i + 1);
                    }
                }
            }
        }
        
        return null;
    }

    @Override
    public void huntPrey(Animal prey, Animal predator) {
        if (prey.getSpeed() < predator.getSpeed() || prey.getEnergy() < predator.getEnergy()
         || predator.getEnergy() == 100) {
            predator.setEnergy(predator.getEnergy() + prey.getWeight());
            prey.setEnergy(0);
        } else {
            TooStrongPreyExeption.getMessage("The prey is too strong or too fast to attack");
        }

    }

    @Override
    public void grazeInTheField(Animal animal, Field field) {

        if (field.getGrassAmount() >= getWeight() / 10) {
            field.setGrassAmount(field.getGrassAmount() - getWeight() / 10);
            animal.setEnergy(animal.getEnergy() + getWeight() / 10);
        }

        if (animal.getEnergy() > 100) {
            animal.setEnergy(100);
        }
    }
}

class Zebra extends Animal implements Herbivore {
    public Zebra(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }

    @Override
    public void makeSound() {
        System.out.println(AnimalSound.ZEBRA.getSound());
    }

    @Override
    public void eat(List<Animal> animals, Field field) {
        grazeInTheField(this, field);
    }

    @Override
    public void grazeInTheField(Animal animal, Field field) {

        if (field.getGrassAmount() >= getWeight() / 10) {
            field.setGrassAmount(field.getGrassAmount() - getWeight() / 10);
            animal.setEnergy(animal.getEnergy() + getWeight() / 10);
        } else {
            makeSound();
        }

        if (animal.getEnergy() > 100) {
            animal.setEnergy(100);
        }
    }
}

interface Omnivore extends Carnivore, Herbivore { }

public class Main {
    private static final String inputFile = "C://Users//dmitr//Documents//Inno//ass5//input.txt";
    private static byte[] buffer;
    public static void main(String[] args) {
        Main main = new Main();
        int days = 0;
        float grassAmount = 0;


        try(FileInputStream in = new FileInputStream(inputFile);) {
            buffer = new byte[in.available()];
            in.read(buffer, 0, buffer.length);
            String[] input = new String(buffer).split("\r\n| ");
            days = Integer.parseInt(input[0]);
            grassAmount = Float.parseFloat(input[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Animal> animals = main.readAnimals();

        main.printAnimals(animals);

        main.runSimulation(days, grassAmount, animals);

        /*
        Звуки, скорее всего, надо воспроизводить только в конце программы.         
        */

    }

    private List<Animal> readAnimals(){
        List<Animal> animals = new ArrayList<>();
        try {
            String[] animal = new String(buffer).split("\r\n| ");
            int animalCount = Integer.parseInt(animal[2]);
            for (int i = 3; i < animalCount * 4; i += 4) {
                if (animal[i].equals("Lion")) {
                    animals.add(new Lion(Float.parseFloat(animal[i + 1]), Float.parseFloat(animal[i + 2]), Float.parseFloat(animal[i + 3])));
                } else if (animal[i].equals("Boar")) {
                    animals.add(new Boar(Float.parseFloat(animal[i + 1]), Float.parseFloat(animal[i + 2]), Float.parseFloat(animal[i + 3])));
                } else if (animal[i].equals("Zebra")) {
                    animals.add(new Zebra(Float.parseFloat(animal[i + 1]), Float.parseFloat(animal[i + 2]), Float.parseFloat(animal[i + 3])));
                }
            }
        } catch (Exception e) {
            InvalidInputsException.getMessage("Invalid inputs");
        }
        return animals;
    }

    private void runSimulation(int days, float grassAmount, List<Animal> animals) {
        Field field = new Field(grassAmount);
        removeDeadAnimals(animals);
        for (int i = 0; i < days; i++) {
            for (int j = 0; j < animals.size(); j++) {
                animals.get(j).eat(animals, field);
                animals.get(j).decrementEnergy();
                removeDeadAnimals(animals);
            }
            field.growGrass();
        }
    }

    private void printAnimals(List<Animal> animals) {
        for (Animal animal : animals) {
            System.out.println(animal.getClass().getSimpleName() + " " + animal.getWeight() + " " + animal.getSpeed() + " " + animal.getEnergy());
        }
    }

    private void removeDeadAnimals(List<Animal> animals) {
        animals.removeIf(animal -> animal.getEnergy() <= 0);
    }

    private Animal receiveAnimal(String[] animal){
        if (animal[0].equals("Lion")) {
            return new Lion(Float.parseFloat(animal[1]), Float.parseFloat(animal[2]), Float.parseFloat(animal[3]));
        } else if (animal[0].equals("Boar")) {
            return new Boar(Float.parseFloat(animal[1]), Float.parseFloat(animal[2]), Float.parseFloat(animal[3]));
        } else if (animal[0].equals("Zebra")) {
            return new Zebra(Float.parseFloat(animal[1]), Float.parseFloat(animal[2]), Float.parseFloat(animal[3]));
        }
        return null;
    }
}

class CanibalismExeption extends Exception {
    CanibalismExeption() {
        super();
    }
    public static void getMessage(String message) {
        System.out.println(message);
    }
}

class TooStrongPreyExeption extends Exception {
    TooStrongPreyExeption() {
        super();
    }
    public static void getMessage(String message) {
        System.out.println(message);
    }
}

class SelfHuntingExeption extends Exception {
    SelfHuntingExeption() {
        super();
    }
    public static void getMessage(String message) {
        System.out.println(message);
    }
}

class WeightOutOfBoundsException extends Exception {
    WeightOutOfBoundsException() {
        super();
    }
    public static void getMessage(String message) {
        System.out.println(message);
    }
}

class SpeedOutOfBoundsException extends Exception {
    SpeedOutOfBoundsException() {
        super();
    }
    public static void getMessage(String message) {
        System.out.println(message);
    }
}

class EnergyOutOfBoundsException extends Exception {
    EnergyOutOfBoundsException() {
        super();
    }
    public static void getMessage(String message) {
        System.out.println(message);
    }
}

class GrassOutOfBoundsException extends Exception {
    GrassOutOfBoundsException() {
        super();
    }
    public static void getMessage(String message) {
        System.out.println(message);
    }
}

class InvalidNumberOfAnimalParameters extends Exception {
    InvalidNumberOfAnimalParameters() {
        super();
    }
    public static void getMessage(String message) {
        System.out.println(message);
    }
}

class InvalidInputsException extends Exception {
    InvalidInputsException() {
        super();
    }
    public static void getMessage(String message) {
        System.out.println(message);
    }
}