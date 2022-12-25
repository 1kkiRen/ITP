package lab13;

import java.util.ArrayList;
import java.util.List;

class Library<T>{
    private List<T> books;
    private int count;
    public Library(int size){
        books = new ArrayList<T>(size);
        count = 0;
    }
    public void add(T book){
        books.add(book);
        count++;
    }
    public T get(int index){
        return books.get(index);
    }
}

class LibraryNonGen{
    private List<Media> books;
    // private int count;
    public LibraryNonGen(int size){
        books = new ArrayList<Media>(size);
        // this.count = 0;
    }
    public void add(Media book){
        books.add(book);
        // count++;
    }
    public Media get(int index){
        return books.get(index);
    }
}

class Media{

}

class Book extends Media{
    
}

class Video extends Media{

}

class Newspaper extends Media{

}




public class task1 {
    public static void main(String[] args) {
        Library<Book> bookLibrary = new Library<>(10);
        Library<Video> videoLibrary = new Library<>(10);
        Library<Newspaper> newspaperLibrary = new Library<>(10);
        bookLibrary.add(new Book());
        videoLibrary.add(new Video());
        newspaperLibrary.add(new Newspaper());
    }
}
