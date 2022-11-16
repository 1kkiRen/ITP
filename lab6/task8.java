package lab6;

import java.util.Scanner;

public class task8 {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);

        int a = scan.nextInt();

        System.out.println(Integer.toBinaryString(a) + " " + Integer.toHexString(a) + " " + a);

        scan.close();
    }
}
