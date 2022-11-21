package lab6;

import java.util.Scanner;

public class task5 {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);

        String strn = scan.nextLine();

        count_of_vowels(strn);

        scan.close();
    }

    public static void count_of_vowels(String strn){
        int count = 0;
        strn = strn.toLowerCase();
        for(int i = 0; i < strn.length(); i++){
            if(strn.charAt(i) == 'a' || strn.charAt(i) == 'e' || strn.charAt(i) == 'i' || strn.charAt(i) == 'o' || strn.charAt(i) == 'u' || strn.charAt(i) == 'y'){
                count++;
            }
        }

        System.out.println(count);

    }
}
