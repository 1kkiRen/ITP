package lab11;

interface Swimmable {
    void swim();
    void stopSwimming();
}

interface Flyable {
    void fly();
    void stopFlying();
}

interface Living {
    default void live(String entity) {
        System.out.println(entity + " lives");
    }
}

class Submarine implements Swimmable {
    @Override
    public void swim() {
        System.out.println("Submarine swims");
    }

    @Override
    public void stopSwimming() {
        System.out.println("Submarine stops swimming");
    }
}

class Duck implements Swimmable, Flyable, Living {
    @Override
    public void swim() {
        System.out.println("Duck swims");
    }

    @Override
    public void stopSwimming() {
        System.out.println("Duck stops swimming");
    }

    @Override
    public void fly() {
        System.out.println("Duck flies");
    }

    @Override
    public void stopFlying() {
        System.out.println("Duck stops flying");
    }

}

class Penguin implements Swimmable, Living {
    @Override
    public void swim() {
        System.out.println("Penguin swims");
    }

    @Override
    public void stopSwimming() {
        System.out.println("Penguin stops swimming");
    }
}


class InterfaceDemonstration {
    public static void main(String[] args) {
        Submarine submarine = new Submarine();
        submarine.swim();
        submarine.stopSwimming();

        

        Duck duck = new Duck();
        duck.swim();
        duck.stopSwimming();
        duck.fly();
        duck.stopFlying();
        duck.live(duck.getClass().getSimpleName());

        Penguin penguin = new Penguin();
        penguin.swim();
        penguin.stopSwimming();
        penguin.live(penguin.getClass().getSimpleName());
    }
}

// public class task1 {
    
// }
