package lab7;

import java.util.Scanner;

class Calculator{
    private int number1;
    private int number2;
    private char sign;

    public void setFirstNumber(int num){
        if(num < 0){
            System.out.println("Invalid input");
        }
        else{
            this.number1 = num;
        }
    }

    public void setSecondNumber(int num){
        if(num < 0){
            System.out.println("Invalid input");
        }
        else{
            this.number2 = num;
        }
    }

    public void setSign(char sign){
        if(sign == '+' || sign == '-' || sign == '*' || sign == '/'){
            this.sign = sign;
        }
        else{
            System.out.println("Invalid input");
        }        
    }

    private void add(){
        System.out.println(number1 + number2);
    }

    private void subtract(){
        System.out.println(number1 - number2);
    }

    private void multiply(){
        System.out.println(number1 * number2);
    }

    private void divide(){
        if (number2 == 0){
            System.out.println(-1);
        }
        System.out.println((double)number1 / (double)number2);
    }

    private boolean checker(){
        if (number1 < 0 || number1 > 1000){
            System.out.println("Number 1 is out of bounds");
            return false;
        }

        if (number2 < 0 || number2 > 1000){
            System.out.println("Number 2 is out of bounds");
            return false;
        }

        if (number2 == 0 && sign == '/'){
            System.out.println("You cannot divide by zero");
            return false;
        }

        return true;
    }

    public void calculate(){ 
        if (checker()){

            switch(this.sign){
                case '+':
                    this.add();
                    break;
                case '-':
                    this.subtract();
                    break;
                case '*':
                    this.multiply();
                    break;
                case '/':
                    this.divide();
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }

    }


}


public class task1 {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);

        Calculator calc = new Calculator();

        calc.setFirstNumber(scan.nextInt());
        calc.setSign(scan.next().charAt(0));
        calc.setSecondNumber(scan.nextInt());

        calc.calculate();

        scan.close();
    }
}
