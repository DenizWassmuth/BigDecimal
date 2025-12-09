package org.example;


import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * PasswordValidator – kurze Beschreibung
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
 */

public class Services {

    private final int accountNumberLength;
    private final String charsAllawed = "0123456789";

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

    public void transfereToOtherAccount(String withdrawalAccountNumber, String depositAccountNumber, BigDecimal amount) {
        if (accounts.isEmpty()){
            System.out.println("no accounts found");
            return;
        }

        if (!accounts.containsKey(withdrawalAccountNumber)){

            return;
        }

        Account withdrawelAccount = accounts.get(withdrawalAccountNumber);
        if (withdrawelAccount.isBroke()){
            return;
        }

        if (!accounts.containsKey(depositAccountNumber)){
            return;
        }

        Account depositAccount = accounts.get(depositAccountNumber);

        BigDecimal withdrawalAmount = withdrawelAccount.withdraw(amount) ;

        depositAccount.deposit(withdrawalAmount);
    }


    private String createRandomAccountNumber() {
        SecureRandom RANDOM = new SecureRandom();

        if (accountNumberLength <= 0) {
            throw new IllegalArgumentException("length must be > 0");
        }
        if (charsAllawed == null || charsAllawed.isEmpty()) {
            throw new IllegalArgumentException("allowedChars must not be empty");
        }

        StringBuilder sb = new StringBuilder(accountNumberLength);

        for (int i = 0; i < accountNumberLength; i++) {
            int index = RANDOM.nextInt(charsAllawed.length());
            sb.append(charsAllawed.charAt(index));
        }

        return sb.toString();
    }
}
