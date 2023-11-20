package idea_project;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
class ATM {
    private Map<String, Account> accounts;
    private Account currentAccount;

    public ATM() {
        accounts = new HashMap<>();
        // Example accounts
        accounts.put("123456", new Account("123456", "1234", 1000));
        accounts.put("789012", new Account("789012", "5678", 500));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();

        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        if (authenticate(accountNumber, pin)) {
            System.out.println("Authentication successful.");
            displayMenu();
            int choice = scanner.nextInt();

            while (choice != 4) {
                performTransaction(choice);
                displayMenu();
                choice = scanner.nextInt();
            }

            System.out.println("Thank you for using the ATM. Goodbye!");
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }

    private boolean authenticate(String accountNumber, String pin) {
        if (accounts.containsKey(accountNumber)) {
            currentAccount = accounts.get(accountNumber);
            return currentAccount.validatePin(pin);
        }
        return false;
    }

    private void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        System.out.print("Enter choice: ");
    }

    private void performTransaction(int choice) {
        Scanner scanner = new Scanner(System.in);

        switch (choice) {
            case 1:
                System.out.println("Current Balance: $" + currentAccount.getBalance());
                break;
            case 2:
                System.out.print("Enter deposit amount: $");
                double depositAmount = scanner.nextDouble();
                currentAccount.deposit(depositAmount);
                System.out.println("Deposit successful. New Balance: $" + currentAccount.getBalance());
                break;
            case 3:
                System.out.print("Enter withdrawal amount: $");
                double withdrawalAmount = scanner.nextDouble();
                currentAccount.withdraw(withdrawalAmount);
                System.out.println("Withdrawal successful. New Balance: $" + currentAccount.getBalance());
                break;
            case 4:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
