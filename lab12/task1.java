package lab12;

import java.io.*;

public class task1 {
    public static void main(String[] args) {
        String inputFile = "C://Users//1kkiren//Documents//Inno//ITP//lab12//inpt.txt";
        String outputFile = "C://Users//1kkiren//Documents//Inno//ITP//lab12//output.txt";

        try(FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out = new FileOutputStream(outputFile))
        {
            byte[] buffer = new byte[in.available()];
            in.read(buffer, 0, buffer.length);
            out.write(buffer, 0, buffer.length);
            System.out.println("File copied");
            
        } catch(IOException ex){
            if (ex instanceof FileNotFoundException) {
                System.out.println("File not found");
            } else {
                System.out.println(ex.getMessage());
            }
        }
    }
}
