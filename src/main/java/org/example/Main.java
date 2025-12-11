/**
 *
 * Programmierung: BankService Setup
 * Erstelle ein neues Java-Projekt in IntelliJ und implementiere die folgende Aufgabe, um einen einfachen BankService und ein Kundenkonto zu modellieren.
 *
 * Erstelle einen Record ‘Client’ mit den Eigenschaften Vorname, Nachname und Kundennummer (wähle passende englische Feldnamen).
 * Erstelle eine Klasse ‘Account’ (kein Record, sollte anfangs veränderbar sein) mit den Eigenschaften
 *      Kontonummer (String), Kontostand (BigDecimal) und dem zugehörigen Kunden.
 *
 * Programmierung: Kontostand
 * Jetzt werden wir Komfortmethoden implementieren, um den Kontostand abzurufen und zu ändern.
 * Implementiere eine Methode, um Geld auf das Konto einzuzahlen.
 * Implementiere eine Methode, um Geld vom Konto abzuheben.
 *
 * Programmierung: Verwaltung
 * Jetzt werden wir eine Klasse implementieren, die eine Liste von Kunden und ihren Konten verwaltet.
 * Erstelle eine Klasse ‘BankService’, die eine Menge von Konten verwaltet.
 * Implementiere eine Methode in BankService, um ein Konto zu eröffnen. Sie sollte nur einen Kunden als Argument benötigen und die neue Kontonummer zurückgeben.
 * Implementiere eine Methode in BankService, um einen bestimmten Betrag (als BigDecimal)
 *      von einer Kontonummer (als String) auf eine andere Kontonummer (als String) zu überweisen.
 *
 * Programmierung: Gemeinschaftskonten und Trennungen
 * Was passiert, wenn 3 Cent zwischen zwei Personen aufgeteilt werden?
 * Modifiziere deine Records und Klassen so, dass ein Konto mehrere Kontoinhaber haben kann (zwei oder mehr Kontoinhaber).
 * Schreibe eine Methode public List<String> split(String accountNumber) im Service, die ein Konto gleichmäßig aufteilt.
 *      Aus einem Gemeinschaftskonto sollen einzelne Konten für jeden Kontoinhaber erstellt werden. Sie soll die neu erstellten Kontonummern zurückgeben. Jedes Konto soll nach der Aufteilung den gleichen Geldbetrag erhalten (+- 1 Cent). Stelle sicher, dass die Bank während des Prozesses keine Cent-Gewinne oder -Verluste erleidet.
 * PS: Wie üblich handelt unsere Bank nicht mit halben Cent ;)
 * Tipp: Testgetriebene Entwicklung ist auch sehr nützlich, um diese Aufgabe zu lösen! (gilt auch für die folgenden Aufgaben)
 * Bonus: With-Methoden
 *
 * Wenn du die Hauptaufgabe bereits abgeschlossen hast, kannst du diese Bonusaufgabe versuchen.
 * Schreibe With-Methoden (‘Wither’) für alle Eigenschaften des Animal Records.
 *
 * Bonus: Zinsrechner
 * Eine Bank ohne Zinsen? Das geht gar nicht!
 * Erweitere den ‘BankService’ um eine Methode, die Zinsen für alle Kundenkonten basierend auf einem Zinssatz berechnet und gutschreibt.
 *      (Zinsen = Kontostand * (Zinssatz / 100)).
 * Bonus: Transaktionen
 * Kann ich bitte einen Kontoauszug haben?
 *
 * Modifiziere deine Klassen so, dass es keinen festen Kontostand mehr gibt, sondern eine Liste von Transaktionen.
 *      Jede Transaktion hat einen Betrag, einen Saldo (neuer Kontostand nach der Transaktion), eine Beschreibung (optional) und ein Datum.
 *      Die Transaktionen sollen als Records implementiert werden. Um den aktuellen Kontostand zu ermitteln,
 *      soll der BankService den Saldo der letzten Transaktion zurückgeben.
 *
 * Bonus: Tägliche Berechnung von Zinsen
 * Unterjährige Berechnung von Zinsen
 * Für jede Abhebungs- oder Einzahlungsaktion sollen die seit der letzten Transaktion angefallenen Zinsen berechnet werden.
 *      Der im BankService gespeicherte Zinssatz soll den jährlichen Zinssatz darstellen. Achte auf den Zinseszinseffekt
 *      (bei 4% Zinsen sind nach einem halben Jahr nicht ganz 2% angefallen).
 *      Erstelle für jede Transaktion eine zweite Transaktion für Zinsgutschriften (oder Zinsbelastungen).
 *
 *      */


package org.example;

import java.math.BigDecimal;

public class Main {
    static void main() {

       Services bankService = new Services(12);

      String account1 = bankService.openNewAccount("Tobias", "Neuer");
      String account2 = bankService.openNewAccount("Laura", "Hermann");
      String account3 = bankService.openNewAccount("Simon", "Jung");
      String account4 = bankService.openNewAccount("Tobias", "Ling");

       bankService.printAllAccounts();

       bankService.transferToOtherAccount(account1, account2, new BigDecimal("500"));

       bankService.printSingleAccount(account1);
       bankService.printSingleAccount(account2);
    }
}
