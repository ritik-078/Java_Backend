package exceptionHandling;

public class Main {
    public static void main(String[] args) {
        int a = 5;
        int b = 0;
        try {
//            divide(a, b);
            // mimicing
            String name = "Ritik";
            if (name.equals("Ritik")) {
                throw new MyException("Name is Ritik");
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("normal exception");
        } finally {
            System.out.println("Finally block will always execute");
        }

        // Throwable -> Error and Exception
        // Exception -> Checked and Unchecked Exception
        // Checked Exception : ClassNotFoundException, IOException
        // Unchecked Exception : ArithmeticException, NullPointerException, ArrayIndexOutOfBoundsException
        // Exception is a condition that occurs during the execution of a program that disrupts the normal
        // flow of instructions
        // Error is a serious problem that a reasonable application should not try to catch
        // try-catch block is used to handle exceptions
        // throw keyword is used to throw an exception explicitly
        // throws keyword is used to declare an exception
        // custom exception can be created by extending Exception class
        // finally block is used to execute the code that must be executed whether an exception
        // occurs or not
        // multiple catch blocks can be used to handle different types of exceptions
        // try-with-resources is used to close the resources automatically
        // Java 7 introduced multi-catch block to catch multiple exceptions in a single catch block
        // checked exceptions are checked at compile time
        // unchecked exceptions are checked at runtime


    }

    static int divide(int a, int b) throws ArithmeticException{
        if (b == 0) {
            throw new ArithmeticException("please do no divide by zero");
        }

        return  a / b;
    }
}