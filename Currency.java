import java.util.Scanner;

public class Currency {

    public static double convertCurrency(double amount, double exchangeRate){
        return amount * exchangeRate;
    }


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("1. USD to INR");
        System.out.println("2. INR to USD");
        System.out.println("3. EUR to INR");
        System.out.println("4. INR to EUR");
        System.out.println("Enter your Choice");

        int Choice = s.nextInt();
        double amount, convertAmount, exchangeRate = 0;

        System.out.println("Enter the converted ammount: ");

        amount = s.nextDouble();

        switch (Choice) {
            case 1:
                exchangeRate = 82.5;
                break;
            case 2:
                exchangeRate = 0.012;
                break;
            case 3:
                exchangeRate = 90.0;
                break;
            case 4:
                exchangeRate = 0.011;
            default:
                System.out.println("Invalid  Choice ");
                System.exit(0);
        }

        convertAmount = convertCurrency(amount, exchangeRate);

        System.out.println("converted amount  "+ convertAmount);
    }
}