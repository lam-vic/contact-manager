import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class Contacts {

    public static void main(String[] args)
        throws Exception {
        String welcome = "Welcome! to Your Contact Manager";
        System.out.println(welcome);
        System.out.println(menu);
        runApp();
    }

    private static String directory = "data";
    private static String fileName = "contacts.txt";
    private static Path file = Paths.get(directory, fileName);

    private static Scanner sc = new Scanner(System.in);

    private static String menu =
            "   🐧    1. View Contacts       🐧 \n" +
            "   🐧    2. Add New Contact     🐧 \n" +
            "   🐧     3. Search Contact     🐧 \n" +
            "   🐧    4. Delete Contact      🐧 \n" +
            "   🐧     5. Exit               🐧 \n";

    private static void add() throws InterruptedException {
        System.out.println("Please enter the name that you would like add.");
        String newName = sc.nextLine();
        if (newName.length() > 13) {
            System.out.println("Name too large consider abbreviation");
            return;
        }
        System.out.println("Please enter their phone number number");
        String newNumber = sc.nextLine();
        String formattedNumber = format(newNumber);
        if (formattedNumber.length() > 14) {
            System.out.println("Invalid phone number");
            return;
        }
        String newContact = newName + "|" + formattedNumber;
        List<String> updatedList = new ArrayList<>();
        try {
            List<String> contacts = Files.readAllLines(file);
            for (String line : contacts) {
                String name = line.split("\\|")[0];
                String number = line.split("\\|")[1];
                if (name.trim().equalsIgnoreCase(newName) || name.trim().equalsIgnoreCase(newName + " *")) {
                    System.out.printf("There's already a contact named %s. Do you want to overwrite it? (Yes/No)\n", newName);
                    if (sc.nextLine().equals("yes")) {
                        updatedList.add(newContact);
                        continue;
                    } else {
                        add();
                    }
                } else if (number.trim().equalsIgnoreCase(formattedNumber.trim())) {
                    System.out.printf("There's already a contact with the number %s. Do you want to overwrite it? " +
                            "(Yes/No)\n", formattedNumber);
                    if (sc.nextLine().equals("yes")) {
                        updatedList.add(newContact);
                        continue;
                    } else {
                        add();
                    }
                }
                updatedList.add(line);
            }
            Files.write(file, updatedList);
            if (updatedList.contains(newContact)) {
                Files.write(file, updatedList);
            } else {
                Files.write(file, Arrays.asList(newContact), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}



//Prints out contacts
private static void read() {
    try {
        List<String> contacts = Files.readAllLines(file);
        System.out.println(menu);
        for (String line : contacts) {
            String name = line.split("\\|")[0];
            String number = line.split("\\|")[1];
            System.out.format(name, number);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    private static void runApp() throws InterruptedException {
        System.out.println("Input here: ");
        int userOption = Integer.valueOf(sc.nextLine());
        if (userOption == 1) {
            read();
            runApp();
        } else if (userOption == 2) {
            add();
            runApp();
        } else if (userOption == 3) {
           System.out.println("ADIOS!");
        } else {
            System.out.println("Error: Invalid input.");
            runApp();
        }
    }
}