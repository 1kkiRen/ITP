package lab12;

import java.io.*;

public class task2 {
    public static void main(String[] args) {
        String inputFile = "C://Users//1kkiren//Documents//Inno//ITP//lab12//inpt.txt";
        String outputFile = "C://Users//1kkiren//Documents//Inno//ITP//lab12//output.txt";

        try(FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out = new FileOutputStream(outputFile))
        {
            byte[] buffer = new byte[in.available()];
            in.read(buffer, 0, buffer.length);

            String [] input = new String(buffer).split(" ");

            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);
            int c = a / b;

            out.write(String.valueOf(c).getBytes());
            System.out.println("Done");
            
        }
        catch(FileNotFoundException ex){
            System.out.println("File not found");
            ex.addSuppressed(ex);
        }
        catch(NumberFormatException ex){
            System.out.println("Wrong input");
            ex.addSuppressed(ex);
        }
        catch(ArithmeticException ex){
            System.out.println("Division by zero");
            ex.addSuppressed(ex);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            ex.addSuppressed(ex);
        }
    }
}
