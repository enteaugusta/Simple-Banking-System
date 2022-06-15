package banking;

import java.util.Scanner;


public class UserActions {
    private final int EXIT = 0;
    private final int LOG_OUT = 5;
    private final int CLOSE_ACCOUNT = 4;
    private final int DO_TRANSFER = 3;
    private final int ADD_INCOME = 2;
    private final int BALANCE = 1;
    private Account account;
    private final Database cardDB;
    private final LuhnAlgorithm luhnAlgorithm = new LuhnAlgorithm();
    
    
    public UserActions(Database cardDB) {
        this.cardDB = cardDB;
    }
	
	
	public void logInActions() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number:");
        String userCard = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String userPin = scanner.nextLine();
    
        account = cardDB.getAccountInformation(userCard);
    
            if (account != null && account.getAccountNumber().equals(userCard)
                    && account.getPin().equals(userPin)) {
                System.out.println("You have successfully logged in!");
                getLoggedInUserAction(userCard);
            } else {
                System.out.println("Wrong card number or PIN!");
        }
    }

    void getLoggedInUserAction(String number) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
        int userAction2 = scanner.nextInt();
        scanner.nextLine();
        
        switch (userAction2) {
            case BALANCE:
                double balance = cardDB.getBalance(number);
                System.out.println("Balance:" + balance);
                getLoggedInUserAction(number);
                break;
            case ADD_INCOME:
                System.out.println("Add income:");
                double income = scanner.nextDouble();
                scanner.nextLine();
                cardDB.updateBalanceAddingMoney(income, number);
                System.out.println("Income was added!");
                getLoggedInUserAction(number);
                break;
            case DO_TRANSFER:
                doTransfer(number);
                getLoggedInUserAction(number);
                break;
            case CLOSE_ACCOUNT:
                cardDB.deleteAccount(number);
                System.out.println("Account is closed!");
                break;
            case LOG_OUT:
                System.out.println("You have successfully logged out!");
                break;
            case EXIT:
                System.out.println("Bye");
                System.exit(0);
                break;
    
            default:
                throw new IllegalStateException("Unexpected value: " + userAction2);
        }
    }
    
    
    private void doTransfer(String number) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card number to which you want to transfer the money:");
        String cardNumberReciever = scanner.nextLine();
        boolean cardNumberExists = compareCardNumberWithDB(cardNumberReciever);
        if (checkLuhnAlgorithm(cardNumberReciever) && cardNumberExists) {
            System.out.println("Enter how much money you want to transfer:");
            double amountOfTransfer = scanner.nextDouble();
            double balance = cardDB.getBalance(number);
            if (amountOfTransfer <= balance) {
                cardDB.updateBalanceTransfer(amountOfTransfer, number);
                cardDB.updateBalanceAddingMoney(amountOfTransfer, cardNumberReciever);
                System.out.println("Money has been successfully transferred!");
            } else {
                System.out.println("Not enough money!");
            }
        } else if (cardNumberReciever.equals(number)) {
            System.out.println("You can't transfer money to the same account!");
        } else {
            System.out.println("Such card number does not exist!");
        }
    }
    
    
    
    private boolean compareCardNumberWithDB (String cardNumberReciever){
        account = cardDB.getAccountInformation(cardNumberReciever);
        if (account != null && account.getAccountNumber().equals(cardNumberReciever)) {
            return true;
        } else {
            System.out.println("Such card number does not exist!");
            return false;
        }
    }
      
    
    
    private boolean checkLuhnAlgorithm(String numberReciever) {
        String cardnumberWithoutCheckdigit = numberReciever.substring(0, 15);
        int checkDigit = luhnAlgorithm.luhnAlgorithm(cardnumberWithoutCheckdigit);
        String cardNumber = cardnumberWithoutCheckdigit + checkDigit;
    
        if (cardNumber.equals(numberReciever)) {
            return true;
        } else {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return false;
            }
    }
    
    static int getUserAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
        int userInput = scanner.nextInt();
        scanner.nextLine();
        return userInput;
    }
}
