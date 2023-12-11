package com.example.demo;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
public class HelloApplication extends Application {
    private static final Map<String, BankAccount> accountDetails = new HashMap<>();
//    BankAccount account =new BankAccount(2000,3000,);
    static {

        accountDetails.put("Ateeq", new BankAccount(1234, "1000"));
        accountDetails.put("Abdullah", new BankAccount(5678, "500"));
        accountDetails.put("Asad", new BankAccount(4321, "1500"));
    }

    private String currentAccountNumber;
    private Label balanceLabel;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Allied Bank Limited");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        Label titleLabel = new Label("Enter Account Title and PIN");
        titleLabel.setStyle("-fx-font-size: 25; -fx-text-fill: white;-fx-font-family: 'Times New Roman'");

        Label accountLabel = new Label("Account Title:");
        Label pinLabel = new Label("PIN:");
        accountLabel.setStyle("-fx-text-fill: white;-fx-font-family: 'Times New Roman'");
        pinLabel.setStyle("-fx-text-fill: white;-fx-font-family: 'Times New Roman'");


        TextField accountTextField = new TextField();
        PasswordField pinField = new PasswordField();

        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(accountLabel, 0, 1);
        gridPane.add(accountTextField, 1, 1);
        gridPane.add(pinLabel, 0, 2);
        gridPane.add(pinField, 1, 2);

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-family: 'Times New Roman'");

        loginButton.setOnAction(e -> {
            String accountNumber = accountTextField.getText();
            String pin = pinField.getText();

            if (validateLogin(accountNumber, pin)) {
                showATMInterface(primaryStage);
            } else {

                titleLabel.setText("Invalid Account Number or PIN");
                titleLabel.setStyle("-fx-font-size: 20; -fx-text-fill: red");
            }
        });

        gridPane.add(loginButton, 1, 3);

        Scene loginScene = new Scene(gridPane, 500, 350);
        primaryStage.setScene(loginScene);
        primaryStage.show();
        Image transferBackground = new Image("C:\\Users\\TOSHIBA\\IdeaProjects\\demo\\src\\main\\java\\com\\example\\demo\\images\\withdraw-background.jpg"); // Placeholder URL, replace it with your image URL or local file path
        BackgroundImage backgroundImage = new BackgroundImage(transferBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        gridPane.setBackground(background);
    }


    private void showDepositDialog() {
        TextField depositField = new TextField();
        depositField.setPromptText("Enter deposit amount");
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            try {
                double depositAmount = Double.parseDouble(depositField.getText());
                performDeposit(depositAmount);
                updateBalanceLabel();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input for deposit amount");
            }
        });

        GridPane depositDialog = new GridPane();
        depositDialog.setVgap(10);
        depositDialog.setHgap(10);
        depositDialog.add(new Label("Deposit Amount:"), 0, 0);
        depositDialog.add(depositField, 1, 0);
        depositDialog.add(confirmButton, 2, 0);
        depositDialog.setPadding(new Insets(30));
        depositDialog.setStyle("-fx-background-color: lightblue;");

        Stage depositStage = new Stage();
        depositStage.setTitle("Deposit The Amount");
        depositStage.setScene(new Scene(depositDialog, 400, 100));
        depositStage.show();


    }

    private void showWithdrawDialog() {
        TextField withdrawField = new TextField();
        withdrawField.setPromptText("Enter withdrawal amount");
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            try {
                double withdrawAmount = Double.parseDouble(withdrawField.getText());
                performWithdraw(withdrawAmount);
                updateBalanceLabel();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input for withdrawal amount");
            }
        });

        GridPane withdrawDialog = new GridPane();
        withdrawDialog.setVgap(10);
        withdrawDialog.setHgap(10);
        withdrawDialog.add(new Label("Withdrawal Amount:"), 0, 0);
        withdrawDialog.add(withdrawField, 1, 0);
        withdrawDialog.add(confirmButton, 2, 0);
        withdrawDialog.setPadding(new Insets(30));
        withdrawDialog.setStyle("-fx-background-color: light-yellow ;");

        Stage withdrawStage = new Stage();
        withdrawStage.setTitle("Withdraw");
        withdrawStage.setScene(new Scene(withdrawDialog, 400, 100));
        withdrawStage.show();
    }

    private void performDeposit(double amount) {
        BankAccount account = accountDetails.get(currentAccountNumber);
        account.deposit(amount);
    }

    private void performWithdraw(double amount) {
        BankAccount account = accountDetails.get(currentAccountNumber);
        if (account.withdraw(amount)) {
            System.out.println("Withdraw action: " + amount + " $ withdrawn");
        } else {
            System.out.println("Withdraw action: Insufficient funds");
        }
    }

    private void updateBalanceLabel() {
        BankAccount account = accountDetails.get(currentAccountNumber);
        balanceLabel.setText("Current Balance: " + account.getBalance() + " $\nTransaction History:\n" +
                String.join("\n", account.getTransactionHistory()));
    }


    // ...

    private boolean validateLogin(String accountNumber, String pin) {
        if (accountDetails.containsKey(accountNumber)) {
            BankAccount account = accountDetails.get(accountNumber);
            if (account.getPin().equals(pin)) {
                currentAccountNumber = accountNumber;
                return true;
            }
        }
        return false;
    }



    private void showATMInterface(Stage primaryStage) {
        balanceLabel = new Label("Current Balance: " + accountDetails.get(currentAccountNumber).getBalance() + " $");

        Button depositButton = new Button("Deposit Funds");
        depositButton.setOnAction(e -> showDepositDialog());

        Button withdrawButton = new Button("Withdraw Funds");
        withdrawButton.setOnAction(e -> showWithdrawDialog());

        Button checkBalanceButton = new Button("Update Balance");
        checkBalanceButton.setOnAction(e -> updateBalanceLabel());

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> primaryStage.setScene(new Scene(new GridPane(), 400, 300)));
        Image logoutImage = new Image("C:\\Users\\TOSHIBA\\IdeaProjects\\demo\\src\\main\\java\\com\\example\\demo\\images\\istockphoto-1397892955-612x612.jpg");


        ImageView imageView = new ImageView(logoutImage);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        logoutButton.setGraphic(imageView);


        Button transactionHistoryButton = new Button("Transaction History");

        transactionHistoryButton.setOnAction(e -> showTransactionHistory());

        Button transferButton = new Button("Transfer Funds");
        transferButton.setOnAction(e -> showTransferDialog());


        GridPane atmGridPane = new GridPane();
        atmGridPane.setVgap(15);
        atmGridPane.setHgap(15);
        atmGridPane.setPadding(new Insets(10, 20, 20, 20));

        Label welcomeLabel = new Label("Welcome! " + currentAccountNumber);
        welcomeLabel.setStyle("-fx-font-size: 25; -fx-font-family: 'Times New Roman'");

        atmGridPane.add(welcomeLabel, 0, 0, 2, 1);
        atmGridPane.add(balanceLabel, 0, 1, 2, 1);
        atmGridPane.add(depositButton, 0, 2);
        atmGridPane.add(withdrawButton, 0, 3);
        atmGridPane.add(checkBalanceButton, 2, 2);
        atmGridPane.add(logoutButton, 2, 4);
        atmGridPane.add(transactionHistoryButton, 0, 4);
        atmGridPane.add(transferButton, 2, 3);

        Scene atmScene = new Scene(atmGridPane, 430, 250);
        primaryStage.setScene(atmScene);

        Image atmBackground = new Image("C:\\Users\\TOSHIBA\\IdeaProjects\\demo\\src\\main\\java\\com\\example\\demo\\images\\transaction-background.jpg"); // Update the file path accordingly
        BackgroundImage backgroundImage = new BackgroundImage(atmBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        atmGridPane.setBackground(new Background(backgroundImage));
    }


    public static void main(String[] args) {
        launch();
    }



    private void showTransactionHistory() {
        BankAccount account = accountDetails.get(currentAccountNumber);

        TextArea transactionHistoryTextArea = new TextArea();
        transactionHistoryTextArea.setEditable(false);
        transactionHistoryTextArea.setWrapText(true);
        transactionHistoryTextArea.setText("Transaction History:\n\n" +
                String.join("\n", account.getTransactionHistory()));

        Stage historyStage = new Stage();
        historyStage.setTitle("Transaction History");
        historyStage.setScene(new Scene(new VBox(transactionHistoryTextArea), 400, 300));
        historyStage.show();
        VBox historyVBox = new VBox(transactionHistoryTextArea);
        historyVBox.setStyle("-fx-background-color: gray;");

    }


    private void showTransferDialog() {
        TextField recipientAccountField = new TextField();
        recipientAccountField.setPromptText("Enter recipient's account number");

        TextField transferAmountField = new TextField();
        transferAmountField.setPromptText("Enter transfer amount");

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            try {
                String recipientAccountNumber = recipientAccountField.getText();
                double transferAmount = Double.parseDouble(transferAmountField.getText());

                if (validateRecipientAccount(recipientAccountNumber)) {
                    BankAccount recipientAccount = accountDetails.get(recipientAccountNumber);
                    BankAccount currentAccount = accountDetails.get(currentAccountNumber);

                    currentAccount.transfer(transferAmount, recipientAccount);
                    updateBalanceLabel();
                } else {
                    System.out.println("Invalid recipient account number");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input for transfer amount");
            }
        });

        GridPane transferDialog = new GridPane();
        transferDialog.setVgap(10);
        transferDialog.setHgap(10);
        transferDialog.add(new Label("Recipient Account Number:"), 0, 0);
        transferDialog.add(recipientAccountField, 1, 0);
        transferDialog.add(new Label("Transfer Amount:"), 0, 1);
        transferDialog.add(transferAmountField, 1, 1);
        transferDialog.add(confirmButton, 1, 2);

        Stage transferStage = new Stage();
        transferStage.setTitle("Transfer Funds");
        transferStage.setScene(new Scene(transferDialog, 400,200 ));
        transferStage.show();
        transferDialog.setPadding(new Insets(30));
        transferDialog.setStyle("-fx-background-color: light-pink ;");

    }

    private boolean validateRecipientAccount(String recipientAccountNumber) {
        return accountDetails.containsKey(recipientAccountNumber) && !recipientAccountNumber.equals(currentAccountNumber);
    }


}
