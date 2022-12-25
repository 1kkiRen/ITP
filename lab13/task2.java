package lab13;

import java.util.ArrayList;

interface Printable {
    void print();
}

interface Expirable {
    boolean hasExpired();

    void reissue();
}

enum DocType {
    PASSPORT(true), SNILS(false), OTHER(false);
    boolean isFixedTerm;

    DocType(boolean b) {
        isFixedTerm = b;
    }
}

abstract class Document implements Printable {
    private DocType type;
    protected int number;
    protected String ownerName;

    public Document(DocType type, int number, String ownerName) {
        this.type = type;
        this.number = number;
        this.ownerName = ownerName;
    }

    public DocType type() {
        return this.type;
    }
}

class Passport extends Document implements Expirable {
    private int age;
    private boolean reissued;

    public Passport(String name, int age, int number) {
        super(DocType.PASSPORT, number, name);
        this.age = age; 
    }

    @Override
    public void print() {
        System.out.println(type() + " #" + number + " of " + ownerName);
        System.out.println(" Status: " + (hasExpired()?"expired":"valid"));
    }

    @Override
    public boolean hasExpired() {
        if (age > 44 && !reissued) {
            return false;
        } else {
            return true;
        }
    }   

    @Override
    public void reissue() {
        reissued = true;
    }
}

class Snils extends Document {
    public Snils(String name, int number) {
        super(DocType.SNILS, number, name);
    }

    @Override
    public void print() {
        System.out.println(type() + " #" + number + " of " + ownerName);
    }
}

public class task2 {
    public static void main(String[] args) {
        Document document = new Passport("John", 45, 123456);
        Document passport = new Passport("John", 45, 123456);
        Document snils = new Snils("John", 123456789);

        ArrayList<Document> documents = new ArrayList<>();
        documents.add(document);
        documents.add(passport);
        documents.add(snils);

        for (Document item : documents) {
            if(item.type() == DocType.PASSPORT) {
                if(((Passport) item).hasExpired() == true) {
                    System.out.println("Passport has expired");
                } else {
                    item.print();
                }
            } else {
                item.print();
            }
        }
    }
}
