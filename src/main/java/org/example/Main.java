package org.example;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static DatabaseManager dbm = new DatabaseManager();

    public static void main(String[] args) {

        dbm.createTables();

        System.out.println("1 - Add new contact");
        System.out.println("2 - Show all contacts");

        int choise = scanner.nextInt();
        scanner.nextLine();

        if(choise == 1) AddContact();
        else if(choise == 2) ShowAllContacts();
    }

    public static void AddContact(){
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        System.out.println("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        dbm.addContact(firstName, lastName, email, phoneNumber);
    }

    public static void ShowAllContacts(){
        dbm.printContacts();
    }
}