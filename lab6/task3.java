package lab6;

import java.util.Scanner;

public class task3 {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);

        String strn = scan.nextLine();

        for(int i = 0; i < strn.length(); i++){
            if(((int) strn.charAt(i) >= 65 && (int) strn.charAt(i) <= 90) || ((int) strn.charAt(i) >= 97 && (int) strn.charAt(i) <= 122)){
                System.out.print((int)strn.charAt(i) + " ");

            }

        }

        scan.close();
    }
}
