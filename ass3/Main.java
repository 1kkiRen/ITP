package ass3;

import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        
        CalculatorType calc_type = readCalculator();
        
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
                return;
        }
            
            
            
        int n = readCommandsNumber();

        if(n < 1 || n > 50){
            reportFatalError("Amount of commands is Not a Number");
            return;
        }
         
        for(int i = 0; i < n; i++){ 
            OperationType operation = parseOperation(scanner.next());
            String a = scanner.next();
            String b = scanner.next();

            String result = null;
            
            switch(operation){
                case ADDITION:
                    result = calculator.add(a, b);
                    break;
                
                case SUBTRACTION:
                    result = calculator.substract(a, b);
                    break;
                
                case MULTIPLICATION:
                    result = calculator.multiply(a, b);
                    break;
                    
                case DIVISION:
                    result = calculator.divide(a, b);
                    break;
                    
                case INCORRECT:
                    result = "Wrong operation type";
                    break;
                }
            System.out.println(result);
                
        }

        
        scanner.close();
    }
    
    public static CalculatorType readCalculator(){

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
 
        try{
            return Integer.parseInt(scanner.nextLine());
        }
        catch(NumberFormatException e){
            return -1;
        }
        
        
    }

    public static OperationType parseOperation(String operation){
        if(operation.equals("+")){
            return OperationType.ADDITION;
        }
        else if(operation.equals("-")){
            return OperationType.SUBTRACTION;
        }
        else if(operation.equals("*")){
            return OperationType.MULTIPLICATION;
        }
        else if(operation.equals("/")){
            return OperationType.DIVISION;
        }
        else{
            return OperationType.INCORRECT;
        }
    }
    
    public static void reportFatalError(String err){
        System.out.println(err);
    }
}



enum CalculatorType{
    INTEGER,
    DOUBLE,
    STRING,
    INCORRECT
}

enum OperationType{
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    INCORRECT
}

abstract class Calculator{
    abstract String add(String a, String b);
    abstract String substract(String a, String b);
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
    String substract(String a, String b) {
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
            return Integer.toString(Integer.parseInt(a) / Integer.parseInt(b));
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
    String substract(String a, String b) {
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
            if(Math.ceil(Double.parseDouble(b)) == 0){
                return "Division by zero";
            }
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
    String substract(String a, String b) {
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

        if(num <= 0){
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

