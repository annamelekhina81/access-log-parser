import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Введите первое число: ");
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.print("Введите второе число: ");
        int secondNumber = new Scanner(System.in).nextInt();
        System.out.println("Сумма чисел: "+ addingNumbers(firstNumber, secondNumber));
        System.out.println("Разность чисел: "+ differenceNumbers(firstNumber, secondNumber));
        System.out.println("Произведение чисел: "+ multiplicationNumbers(firstNumber, secondNumber));
        System.out.println("Частное чисел: "+ quotientNumbers(firstNumber, secondNumber));
    }
    public static int addingNumbers(int x, int y) {
        return x+y;
    }

    public static int differenceNumbers(int x, int y) {
        return x-y;
    }

    public static int multiplicationNumbers(int x, int y) {
        return x*y;
    }
    public static double quotientNumbers(int x, int y) {
        return (double) x/y;
    }
}

