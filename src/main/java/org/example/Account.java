package org.example;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * Account
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

public class Account {

    private final String accountNumber;
    private BigDecimal balance;
    private Map<String, Client> clients;

    public Account(String accountNumber, BigDecimal balance, Client client) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.clients = new HashMap<>();
        clients.put(client.customerId(),client);
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

    public Map<String, Client> getClients(){
        return clients;
    }



    public void addClient(Client client){

        if (client == null)
        {
            return;
        }

        if(clients.containsKey(client.customerId())){
            return;
        }

        clients.put(client.customerId(),client);

        System.out.println("New client: " + client + " has been added to the account:" + this);
    }

    Client getClientByKey(String customerId) {

        if (clients.isEmpty()){
            return null;
        }

        if (clients.containsKey(customerId)) {
            return clients.get(customerId);
        }

        return null;
    }

    void setClientByKey(String customerId, Client client) {
        this.clients.put(customerId, client);
    }

    void  deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be 0 or negative");
        }

        balance = balance.add(amount);
    }

    boolean isBroke() {
        return balance.compareTo(BigDecimal.ZERO) <= 0;
    }

    BigDecimal withdraw(BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount cannot be 0 or negative");
        }

        balance = balance.subtract(amount);
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) && Objects.equals(balance, account.balance) && Objects.equals(clients, account.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance, clients);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", client=" + clients +
                '}';
    }
}
