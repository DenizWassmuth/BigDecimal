package org.example;


import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

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
    private final String charsAllowed = "0123456789";

   // private List<Account> accounts = new ArrayList<>();
    private Map<String, Account> accounts = new HashMap<>();

    public Services(int accountNumberLength) {
        this.accountNumberLength = accountNumberLength;
    }

    public int getAccountNumberLength() {
        return accountNumberLength;
    }

    public String openNewAccount(String firstName, String lastName) {
        String newAccountNumber = createRandomAccountNumber();
        Account newAccount = new Account(newAccountNumber, new BigDecimal("1000.00"), new Client(firstName, lastName, newAccountNumber));
        accounts.put(newAccountNumber, newAccount);
        return newAccount.getAccountNumber();
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

        Account withdrawelAccount = accounts.get(withdrawalAccountNumber);
        if (withdrawelAccount.isBroke()) {
            return;
        }

        if (!accounts.containsKey(depositAccountNumber)) {
            return;
        }

        Account depositAccount = accounts.get(depositAccountNumber);

        BigDecimal withdrawalAmount = withdrawelAccount.withdraw(amount);

        depositAccount.deposit(withdrawalAmount);

        String withdrawalClient = withdrawelAccount.getClient().firstName() + " " + withdrawelAccount.getClient().lastName();
        String depositClient = depositAccount.getClient().firstName() +  " " + depositAccount.getClient().lastName();

        System.out.println(accounts.get(withdrawalAmount + "€ has been deposited from " + withdrawalClient + " to " + depositClient);
    }


    private String createRandomAccountNumber() {
        SecureRandom RANDOM = new SecureRandom();

        if (accountNumberLength <= 0) {
            throw new IllegalArgumentException("length must be > 0");
        }
        if (charsAllowed == null || charsAllowed.isEmpty()) {
            throw new IllegalArgumentException("allowedChars must not be empty");
        }

        StringBuilder sb = new StringBuilder(accountNumberLength);

        for (int i = 0; i < accountNumberLength; i++) {
            int index = RANDOM.nextInt(charsAllowed.length());
            sb.append(charsAllowed.charAt(index));
        }

        return sb.toString();
    }
}
