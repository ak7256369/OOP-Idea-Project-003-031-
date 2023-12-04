package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String pin;
    private double balance;
    private double accountNumber;
    private List<String> transactionHistory;

//    public BankAccount(double accountNumber, String pin, double initialBalance) {
//        this.pin = pin;
//        this.accountNumber = accountNumber;
//        this.balance = initialBalance;
//        this.transactionHistory = new ArrayList<>();
//    }


    public BankAccount(double accountNumber, String pin) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = 500;
        this.transactionHistory = new ArrayList<>();
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public double getAccountNumber() {
        return accountNumber;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }





    public void deposit(double amount) {
        balance += amount;
        String transaction = "Deposit: +" + amount + " $";
        transactionHistory.add(transaction);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            String transaction = "Withdrawal: -" + amount + "$";
            transactionHistory.add(transaction);
            return true;
        } else {
            return false;
        }
    }



    public void transfer(double amount, BankAccount recipientAccount) {
        if (amount <= balance) {
            balance -= amount;
            String withdrawalTransaction = "Transfer: -" + amount + " $ to Account " + recipientAccount.getAccountNumber();
            transactionHistory.add(withdrawalTransaction);

            recipientAccount.deposit(amount);
            String depositTransaction = "Transfer: +" + amount + " $ from Account " + getAccountNumber();
            recipientAccount.getTransactionHistory().add(depositTransaction);
        } else {
            System.out.println("Transfer action: Insufficient funds");
        }
    }
}
