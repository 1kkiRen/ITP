import java.util.Scanner;

/**
 * @author Dmitrii Kuzmin.
 */
public final class Main {
    /**
     * Variable for Scanner class.
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Variable for the max number of commands.
     */
    private final int maxCommandsNumber = 50;

    /**
     * Variable for the min number of commands.
     */
    private final int minCommandsNumber = 1;

    private Main() { };
    /**
     * This is the main method which makes central logic of the program.
     * @param args
     */
    public static void main(String[] args) {
        Main main = new Main();
        CalculatorType calcType = main.readCalculator();
        Calculator calculator = null;
        switch (calcType) {
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
                main.reportFatalError("Wrong calculator type");
                return;
            default:
                break;
        }

        int n = main.readCommandsNumber();

        // check if the number of commands is correct
        if (n < main.minCommandsNumber || n > main.maxCommandsNumber) {
            main.reportFatalError("Amount of commands is Not a Number");
            return;
        }

        for (int i = 0; i < n; i++) {
            OperationType operation = main.parseOperation(main.scanner.next());
            String a = main.scanner.next();
            String b = main.scanner.next();

            String result = null;

            switch (operation) {
                case ADDITION:
                    result = calculator.add(a, b);
                    break;

                case SUBTRACTION:
                    result = calculator.subtract(a, b);
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
                default:
                    break;
                }
            System.out.println(result);
        }

        main.scanner.close();
    }
    /**
     * This method reads the calculator type.
     * @return calculator type.
     */
    private CalculatorType readCalculator() {

        String type = scanner.nextLine();
        if (type.equals("INTEGER")) {
            return CalculatorType.INTEGER;
        } else
        if (type.equals("DOUBLE")) {
            return CalculatorType.DOUBLE;
        } else
        if (type.equals("STRING")) {
            return CalculatorType.STRING;
        } else {
            return CalculatorType.INCORRECT;
        }
    }
    /**
     * This method reads the number of commands.
     * @return number of commands.
     */
    private int readCommandsNumber() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    /**
     * This method parses the operation type.
     * @param operation
     * @return operation type.
     */
    private OperationType parseOperation(String operation) {
        if (operation.equals("+")) {
            return OperationType.ADDITION;
        } else
        if (operation.equals("-")) {
            return OperationType.SUBTRACTION;
        } else
        if (operation.equals("*")) {
            return OperationType.MULTIPLICATION;
        } else
        if (operation.equals("/")) {
            return OperationType.DIVISION;
        } else {
            return OperationType.INCORRECT;
        }
    }
    /**
     * This method reports the fatal error.
     * @param err
     */
    private void reportFatalError(String err) {
        System.out.println(err);
    }
}


/**
 * This is the enum class for the calculator type.
 */
enum CalculatorType {
    /**
     *Enum for integer calculator type.
     */
    INTEGER,
    /**
     *Enum for double calculator type.
     */
    DOUBLE,
    /**
     *Enum for string calculator type.
     */
    STRING,
    /**
     *Enum for incorrect calculator type.
     */
    INCORRECT
}

/**
 * This is the enum class for the operation type.
 */
enum OperationType {
    /**
     *Enum for addition operation type.
     */
    ADDITION,
    /**
     *Enum for subtraction operation type.
     */
    SUBTRACTION,
    /**
     *Enum for multiplication operation type.
     */
    MULTIPLICATION,
    /**
     *Enum for division operation type.
     */
    DIVISION,
    /**
     *Enum for incorrect operation type.
     */
    INCORRECT
}

/**
 * This is the interface for the calculator.
 */
abstract class Calculator {
    abstract String add(String a, String b);
    abstract String subtract(String a, String b);
    abstract String multiply(String a, String b);
    abstract String divide(String a, String b);
}

/**
 * This is the class for the integer calculator.
 */
class IntegerCalculator extends Calculator {
    /**
     * This method adds two integers.
     * @param a
     * @param b
     * @return result of addition.
     */
    @Override
    String add(String a, String b) {
        try {
            return Integer.toString(Integer.parseInt(a) + Integer.parseInt(b));
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
    /**
     * This method subtracts two integers.
     * @param a
     * @param b
     * @return result of subtraction.
     */
    @Override
    String subtract(String a, String b) {
        try {
            return Integer.toString(Integer.parseInt(a) - Integer.parseInt(b));
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
    /**
     * This method multiplies two integers.
     * @param a
     * @param b
     * @return result of multiplication.
     */
    @Override
    String multiply(String a, String b) {
        try {
            return Integer.toString(Integer.parseInt(a) * Integer.parseInt(b));
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
    /**
     * This method divides two integers.
     * @param a
     * @param b
     * @return result of division.
     */
    @Override
    String divide(String a, String b) {
        try {
            return Integer.toString(Integer.parseInt(a) / Integer.parseInt(b));
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        } catch (Exception e) {
            return "Division by zero";
        }
    }
}


/**
 * This is the class for the double calculator.
 */
class DoubleCalculator extends Calculator {
    /**
     * This method adds two doubles.
     * @param a
     * @param b
     * @return result of addition.
     */
    @Override
    String add(String a, String b) {
        try {
            return Double.toString(Double.parseDouble(a) + Double.parseDouble(b));
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
    /**
     * This method subtracts two doubles.
     * @param a
     * @param b
     * @return result of subtraction.
     */
    @Override
    String subtract(String a, String b) {
        try {
            return Double.toString(Double.parseDouble(a) - Double.parseDouble(b));
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
    /**
     * This method multiplies two doubles.
     * @param a
     * @param b
     * @return result of multiplication.
     */
    @Override
    String multiply(String a, String b) {
        try {
            return Double.toString(Double.parseDouble(a) * Double.parseDouble(b));
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }
    }
    /**
     * This method divides two doubles.
     * @param a
     * @param b
     * @return result of division.
     */
    @Override
    String divide(String a, String b) {
        try {
            if (Math.ceil(Double.parseDouble(b)) == 0) {
                return "Division by zero";
            }
            return Double.toString(Double.parseDouble(a) / Double.parseDouble(b));
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        } catch (Exception e) {
            return "Division by zero";
        }
    }
}


/**
 * This is the class for the string calculator.
 */
class StringCalculator extends Calculator {
    /**
     * This method adds two strings.
     * @param a
     * @param b
     * @return result of addition.
     */
    @Override
    String add(String a, String b) {
        return a + b;
    }
    /**
     * This method subtracts two strings.
     * @param a
     * @param b
     * @return result of subtraction.
     */
    @Override
    String subtract(String a, String b) {
        return "Unsupported operation for strings";
    }
    /**
     * This method multiplies two strings.
     * @param a
     * @param b
     * @return result of multiplication.
     */
    @Override
    String multiply(String a, String b) {
        String result = "";
        int num = 0;
        try {
            num = Integer.parseInt(b);
        } catch (NumberFormatException e) {
            return "Wrong argument type";
        }

        if (num <= 0) {
            return "Wrong argument type";
        }

        for (int i = 0; i < num; i++) {
            result += a;
        }
        return result;
    }
    /**
     * This method divides two strings.
     * @param a
     * @param b
     * @return result of division.
     */
    @Override
    String divide(String a, String b) {
        return "Unsupported operation for strings";
    }
}
