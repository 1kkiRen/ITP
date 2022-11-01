package ass3;

import java.util.Scanner;


enum CalculatorType{
    INTEGER,
    DOUBLE,
    STRING,
    INCORRECT
}

enum OperationType{
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    INCORRECT
}

abstract class Calculator{
    abstract String add(String a, String b);
    abstract String substraction(String a, String b);
    abstract String multiply(String a, String b);
    abstract String divide(String a, String b);
}


class IntegerCalculator extends Calculator{
    @Override
    String add(String a, String b) {
        try{
            return Integer.toString(Integer.parseInt(a) + Integer.parseInt(b));
        }
        catch(NumberFormatException e){
            return "Wrong argument type";
        }
    }

    @Override
    String substraction(String a, String b) {
        try{
            return Integer.toString(Integer.parseInt(a) - Integer.parseInt(b));
        }
        catch(NumberFormatException e){
            return "Wrong argument type";
        }
    }

    @Override
    String multiply(String a, String b) {
        try{
            return Integer.toString(Integer.parseInt(a) * Integer.parseInt(b));
        }
        catch(NumberFormatException e){
            return "Wrong argument type";
        }
    }

    @Override
    String divide(String a, String b) {
        try{
            return Integer.toString(Integer.parseInt(a) * Integer.parseInt(b));
        }
        catch(NumberFormatException e){
            return "Wrong argument type";
        }
        catch(Exception e){
            return "Division by zero";
        }
    }
}

class DoubleCalculator extends Calculator{
    @Override
    String add(String a, String b) {
        try{
            return Double.toString(Double.parseDouble(a) + Double.parseDouble(b));
        }
        catch(NumberFormatException e){
            return "Wrong argument type";
        }
    }

    @Override
    String substraction(String a, String b) {
        try{
            return Double.toString(Double.parseDouble(a) - Double.parseDouble(b));
        }
        catch(NumberFormatException e){
            return "Wrong argument type";
        }
    }

    @Override
    String multiply(String a, String b) {
        try{
            return Double.toString(Double.parseDouble(a) * Double.parseDouble(b));
        }
        catch(NumberFormatException e){
            return "Wrong argument type";
        }
    }

    @Override
    String divide(String a, String b) {
        try{
            return Double.toString(Double.parseDouble(a) / Double.parseDouble(b));
        }
        catch(NumberFormatException e){
            return "Wrong argument type";
        }
        catch(Exception e){
            return "Division by zero";
        }
    }
}


class StringCalculator extends Calculator{
    @Override
    String add(String a, String b) {
        return a+b;
    }

    @Override
    String substraction(String a, String b) {
        return "Unsupported operation for strings";
    }

    @Override
    String multiply(String a, String b) {
        String result = "";
        int num = 0;
        try{
            num = Integer.parseInt(b);
        }
        catch (NumberFormatException e){
            return "Wrong argument type";
        }

        for(int i=0; i < num; i++){
            result += a;
        }
        return result;
    }

    @Override
    String divide(String a, String b) {
        return "Unsupported operation for strings";
    }
}



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CalculatorType calc_type = readCalculator();

        System.out.println(calc_type);

        Calculator calculator = null;

        switch(calc_type){
            case INTEGER:
                calculator = new IntegerCalculator();
                break;

            case DOUBLE:
                calculator = new DoubleCalculator();
                break;

            case STRING:
                calculator = new StringCalculator();
                break;

            case INCORRECT:
                reportFatalError("Wrong calculator type");
                scanner.close();
                return;
        }



        int n = readCommandsNumber();
        if(n < 1 || n > 50){
            reportFatalError("Amount of commands is Not a Number");
            scanner.close();
            return;
        }

        for(int i=0; i < n; i++){
            OperationType operation = parseOperation(scanner.next());
            String a = scanner.next();
            String b = scanner.next();

            String result = null;

            switch(operation){
                case ADD:
                    result = calculator.add(a, b);
                    break;

                case SUBTRACT:
                    result = calculator.substraction(a, b);
                    break;

                case MULTIPLY:
                    result = calculator.multiply(a, b);
                    break;

                case DIVIDE:
                    result = calculator.divide(a, b);
                    break;

                case INCORRECT:
                    reportFatalError("Wrong operation type");
                    scanner.close();
                    return;
            }

            
            System.out.println(result);
        }


        scanner.close();
    }

    public static CalculatorType readCalculator(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter calculator type: ");
        String type = scanner.nextLine();

        if(type.equals("INTEGER")){
            return CalculatorType.INTEGER;
        }
        else if(type.equals("DOUBLE")){
            return CalculatorType.DOUBLE;
        }
        else if(type.equals("STRING")){
            return CalculatorType.STRING;
        }
        else{
            return CalculatorType.INCORRECT;
        }
    }

    public static int readCommandsNumber(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of commands: ");
        try{
            String num = scanner.nextLine();
            return Integer.parseInt(num);
        }
        catch(NumberFormatException e){
            reportFatalError("Amount of commands is Not a Number");
            return 0;
        }
       
    }

    public static OperationType parseOperation(String operation){
        if(operation.equals("+")){
            return OperationType.ADD;
        }
        else if(operation.equals("-")){
            return OperationType.SUBTRACT;
        }
        else if(operation.equals("*")){
            return OperationType.MULTIPLY;
        }
        else if(operation.equals("/")){
            return OperationType.DIVIDE;
        }
        else{
            return OperationType.INCORRECT;
        }
    }

    public static void reportFatalError(String err){
        System.out.println(err);
    }
}
