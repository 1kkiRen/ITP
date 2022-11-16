package lab6;

import java.util.Scanner;

public class task6 {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);

        int fahrenheit = scan.nextInt();

        float celsius = ((float)fahrenheit - 32) * 5 / 9;

        System.out.println(celsius);

        scan.close();
    }    
}
