package org.example;


import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 *
 * Services – class which contains a map of accounts and methods to handle them;
 * Author: Deniz Wassmuth
 * Date: 05.12.2025
 * <p>
 * KEEP IN MIND:
 * - check and/or change user and email through GitBash
 * - add /target, *.iml and .idea to gitignore
 * - exclude previously added files from git changes through GitBash (git rm -r) and readd the files needed (git add .)
 * -> maven mostly handles this automatically after changing gitignore
 * <p>
 * add dependency: junit-jupiter
 * <p>
 * add plugin: maven-surefire
 * <p>
 * in GITHUB add Action: ... with maven
 *
 */

public class Services {

    private final int accountNumberLength;
    private final int idLength;
    private final String accountCharsAllowed = "0123456789";
    private final String idCharsAllowed = "0123456789";

   // private List<Account> accounts = new ArrayList<>();
    private Map<String, Account> accounts = new HashMap<>();;

    public Services(int accountNumberLength, int idLength) {
        this.accountNumberLength = accountNumberLength;
        this.idLength = idLength;
    }

    public int getAccountNumberLength() {
        return accountNumberLength;
    }

    public Account getAccount(String accountNumber){

        if(accounts.isEmpty()){
            return null;
        }

        return accounts.get(accountNumber);
    }

    public String openNewAccount(String firstName, String lastName) {

        String newAccountNumber = createRandomAccountNumber();
        String newCostumerId = createRandomCustomerId();
        Account newAccount = new Account(newAccountNumber, new BigDecimal("1000.00"), new Client(firstName, lastName, newCostumerId));
        accounts.put(newAccountNumber, newAccount);
        return newAccount.getAccountNumber();
    }

    public String openSharedAccount(String firstName1, String lastName1, String firstName2, String lastName2) {

        String newAccountNumber = createRandomAccountNumber();
        String newCostumerId1 = createRandomCustomerId();
        String newCostumerId2 = createRandomCustomerId();

        Account newAccount = new Account(newAccountNumber, new BigDecimal("00.03"), new Client(firstName1, lastName1, newCostumerId1));
        Client newClient = new Client(firstName2, lastName2, newCostumerId2);
        newAccount.addClient(newClient);

        accounts.put(newAccountNumber, newAccount);
        return newAccountNumber;
    }

    public void splitSharedAccount(Account accountToSplit){

        if(accountToSplit == null){
            return;
        }

        if (accounts.isEmpty()){
            return;
        }

        if (accounts.containsKey(accountToSplit.getAccountNumber())) {

            int numClients = accounts.get(accountToSplit.getAccountNumber()).getClients().size();

            BigDecimal currentBalance = accounts.get(accountToSplit.getAccountNumber()).getBalance();
            BigDecimal splitBalance = currentBalance.divide(new BigDecimal(numClients),2 , ROUND_HALF_UP);

            Account account = accounts.get(accountToSplit.getAccountNumber());
            for (String clientId : account.getClients().keySet()) {

                Client client = account.getClientByKey(clientId);
                String newAccount = openNewAccount(client.firstName(), client.lastName());

                accounts.get(newAccount).setBalance(splitBalance);
            }
        }

        accounts.remove(accountToSplit.getAccountNumber());

    }

    public void addCustomerToExistingAccount(String accountNumber, Client client) {

        if(client == null) {
            return;
        }

        //TODO: maybe create new account?
        if (accounts.isEmpty()) {
            return;
        }

        if (accounts.containsKey(accountNumber)) {
            Account account = accounts.get(accountNumber);
            account.addClient(client);
        }
    }

    void printSingleAccount(String accountNumber) {

        if (accounts.isEmpty()) {
            System.out.println("There are no accounts");
            return;
        }

        if (accounts.containsKey(accountNumber)) {
            System.out.println(accounts.get(accountNumber));
        }
        else {
            System.out.println("No entry for account number " + accountNumber + ".");
        }
    }

    public void printAllAccounts()
    {
        if (accounts.isEmpty()){
            System.out.println("There are no accounts");
            return;
        }

        for (Account account : accounts.values())
        {
            System.out.println(account);
        }
    }

    public void transferToOtherAccount(String withdrawalAccountNumber, String depositAccountNumber, BigDecimal amount) {

        if (accounts.isEmpty()) {
            System.out.println("no accounts found");
            return;
        }

        if (!accounts.containsKey(withdrawalAccountNumber)) {
            return;
        }


        if (!accounts.containsKey(depositAccountNumber)) {
            return;
        }

        Account withdrawelAccount = accounts.get(withdrawalAccountNumber);
        Account depositAccount = accounts.get(depositAccountNumber);

        BigDecimal withdrawalAmount = withdrawelAccount.withdraw(amount);
        depositAccount.deposit(withdrawalAmount);


        System.out.println(withdrawalAmount + "€ withdrawn from " + withdrawelAccount + " and deposited to " + depositAccount);
    }

    private String createRandomString(int length, String charsAllowed){
        SecureRandom RANDOM = new SecureRandom();

        if (length <= 0) {
            throw new IllegalArgumentException("length must be > 0");
        }
        if (charsAllowed == null || charsAllowed.isEmpty()) {
            throw new IllegalArgumentException("allowedChars must not be empty");
        }

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(charsAllowed.length());
            sb.append(charsAllowed.charAt(index));
        }

        return sb.toString();
    }

    private String createRandomAccountNumber() {
        return createRandomString(accountNumberLength, accountCharsAllowed);
    }

    private String createRandomCustomerId() {
        return createRandomString(idLength, idCharsAllowed);
    }
}
