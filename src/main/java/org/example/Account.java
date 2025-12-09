package org.example;


import java.math.BigDecimal;

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

public class Account {

    private final String accountNumber;
    private BigDecimal balance;
    private Client client;

    public Account(String accountNumber, BigDecimal balance, Client client) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.client = client;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    Client getClient() {
        return client;
    }
    void setClient(Client client) {
        this.client = client;
    }

    void  deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    boolean isBroke() {
        return balance.compareTo(BigDecimal.ZERO) <= 0;
    }

    BigDecimal withdraw(BigDecimal amount) {

        if (balance.subtract(amount).compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        balance = balance.subtract(amount);
        return amount;
    }
}
