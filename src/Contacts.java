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

    public static void main(String[] args) {
        throws Exception {
        String welcome = "Welcome! to Your Contact Manager";
        System.out.println(welcome);
        runApp();
    }

    String directory = "data";
    String fileName = "contacts.txt";
    Path file = Paths.get(directory, fileName);

    Scanner sc = new Scanner(System.in);


    private static void add() throws InterruptedException {
        System.out.println("Please enter the name that you would like to add.");
        String newName = sc.nextLine();
        if (newName.length() > 13) {
            System.out.println("Name too large consider abbreviation");
            return;
        }
        System.out.println("Please enter their phone number");
        String newNumber = sc.nextLine();
        String formattedNumber = format(newNumber);
    }
}

private static void runApp() throws InterruptedException {
    System.out.println("Input here: ");
    int userOption = Interger.valueOf(sc.nextLine());
    if (userOption == 1) {
        read();
        runApp();
    } else if (userOption == 2) {
        add();
        runApp();
    } else if (userOption == 5) {
        System.out.println("ADIOS!");
    } else {
        System.out.println("Wrong Input.");
        runApp();
    }
}

//Prints out contacts
private static void read() {
    try {
        List<String> contacts = Files.readAllLines(file);
        for (String line : contacts) {
            String name = line.split("\\|")[0];
            String number = line.split("\\|")[1];
            System.out.format(name, number);
        }
    }catch (IOException | InterruptedException e){
        e.printStackTrace();
        }
}
}