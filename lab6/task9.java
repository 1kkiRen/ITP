package lab6;

import java.util.Scanner;

public class task9 {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);

        int size = scan.nextInt();

        int [] arr = new int[size];
        int sum = 0;

        for (int i = 0; i < size; i++){
            arr[i] = scan.nextInt();
            sum += arr[i];
        }

        System.out.println((float)(sum / size));




        scan.close();
    }
}
