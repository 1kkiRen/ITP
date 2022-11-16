package lab6;

import java.util.Scanner;

public class task4 {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);
        
        String strn1 = scan.nextLine();
        String strn2 = scan.nextLine();


        if(strn1.equals(strn2)){
            System.out.println("Strings are equal");
        }else{
            System.out.println("Strings are not equal");
        }

        scan.close();
    }  
}
