package lab6;

import java.util.Scanner;

public class task2 {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);

        char a = scan.next().charAt(0);
        
        System.out.println("a = " + (int) a);
        
        scan.close();
    }
}
