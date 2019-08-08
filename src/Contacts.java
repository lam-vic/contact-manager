import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class Contacts {

    public static void main(String[] args) {

        Path dataFile = Paths.get("./data");
        System.out.println(welcome);

    }

    private static String directory = "data";
    private static String fileName = "contacts.txt";
    private static Path file = Paths.get(directory, fileName);

    private static Scanner sc = new Scanner(System.in);


    private static String welcome = "Welcome! to Your Contact Manager";

    private static void add() {
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