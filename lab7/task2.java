package lab7;

class Author{
    private String name;
    private String email;
    private char gender;

    public Author(String name, String email, char gender){
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    public String getAuthorName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public char getGender(){
        return gender;
    }

    public void setAuthorName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setGender(char gender){
        this.gender = gender;
    }

}

class Book{
    private String name;
    private Author author;
    private double price;
    private int qty;

    
    public Book(String name, Author author, double price){
        this.name = name;
        this.author = author;
        this.price = price;
        this.qty = 0;
    }

    public Book(String name, Author author, double price, int qty){
        this.name = name;
        this.author = author;
        this.price = price;
        this.qty = qty;
    }

    public String getName(){
        return name;
    }

    public Author getAuthor(){
        return author;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getQty(){
        return qty;
    }

    public void setQty(int qty){
        this.qty = qty;
    }

    @Override
    public String toString(){
        return "Book [ name = " + getName() + ", Author[name=" + author.getAuthorName() + ", email=" + author.getEmail() + ", gender=" + author.getGender() + "], price = " + getPrice() + ", qty = " + getQty() + " ]";
    }
}

class Library{
    Book [] books;

    public Library(Book [] books){
        this.books = books;
    }

    public void print_book(){
        for(Book book : books){
            System.out.println(book.toString());
        }
    }
 
}


public class task2 {
    public static void main(String [] args){
        Library lib = new Library(new Book[]{
            new Book("The Lord of the Rings", new Author("J.R.R. Tolkien", "efa@agsd.su", 'm'), 1000),
            new Book("The Lord of the Rings", new Author("J.R.R. Tolkien", "asd@saf.rt", 'm'), 1000, 10)
    });

    lib.print_book();

    }
}
