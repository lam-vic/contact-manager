import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
        BufferedImage image = new BufferedImage(144, 32, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("Dialog", Font.PLAIN, 24));
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString("Hola!", 6, 24);
        ImageIO.write(image, "png", new File("text.png"));

        for (int y = 0; y < 32; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < 144; x++)
                sb.append(image.getRGB(x, y) == -16777216 ? " " : image.getRGB(x, y) == -1 ? "#" : "*");
            if (sb.toString().trim().isEmpty()) continue;
            System.out.println(sb);
        }
        System.out.println(menu);
        runApp();
    }

    private static String directory = "data";
    private static String fileName = "contacts.txt";
    private static Path file = Paths.get(directory, fileName);

    private static Scanner sc = new Scanner(System.in);
    private static String leftAlignFormat = "   | | %-14s | %-14s | | %n";
    private static String menu =
            "   🐧    1. View Contacts      🐧 \n" +
            "   🐧    2. Add Contact        🐧 \n" +
            "   🐧    3. Search Contact     🐧 \n" +
            "   🐧    4. Delete Contact     🐧 \n" +
            "   🐧    5. Exit               🐧 \n";

    private static void add() throws InterruptedException {
        System.out.println("Enter name.");
        String newName = sc.nextLine();
        if (newName.length() > 13) {
            System.out.println("Name is too long");
            return;
        }
        System.out.println("Enter the number");
        String newNumber = sc.nextLine();
        String formattedNumber = format(newNumber);
        if (formattedNumber.length() > 14) {
            System.out.println("Not a  phone number");
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
                    System.out.printf("There's already someone with this name %s. Do you want to overwrite it? (Yes/No)\n", newName);
                    if (sc.nextLine().equals("yes")) {
                        updatedList.add(newContact);
                        continue;
                    } else {
                        add();
                    }
                } else if (number.trim().equalsIgnoreCase(formattedNumber.trim())) {
                    System.out.printf("There's already this number %s. Do you want to overwrite it? " +
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
            String number = line.split("\\|" )[1];
            System.out.format(leftAlignFormat, name, number);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    //Method that scans a user input and outputs contacts that contain the input
    private static void search() {
        System.out.println("Who do you want to look up?");
        String searchedName = sc.nextLine();
        boolean foundContact = false;

        try {
            List<String> contacts = Files.readAllLines(file);
            for (String line : contacts) {
                String name = line.split("\\|")[0];
                String number = line.split("\\|")[1];
                if (name.trim().toLowerCase().contains(searchedName.toLowerCase())) {
                    System.out.format(leftAlignFormat, name, number);
                    foundContact = true;
                } else if (number.trim().toLowerCase().contains(searchedName.toLowerCase())) {
                    System.out.format(leftAlignFormat, name, number);
                    foundContact = true;
                }
            }
            if (!foundContact) {
                String name = "no match";
                String number = "no match";
                System.out.format(leftAlignFormat, "| " + name, number);
                return;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Method that deletes contacts, also allows the user to update existing contacts
    private static void delete() {
        System.out.println("Who do you want to delete?");
        String contactToDelete = sc.nextLine();
        List<String> updatedList = new ArrayList<>();
        try {
            List<String> contacts = Files.readAllLines(file);
            for (String line : contacts) {
                String name = line.split("\\|")[0];
                if ((name.trim().equalsIgnoreCase(contactToDelete)) || (name.trim().equalsIgnoreCase(contactToDelete +
                        " *"))) {
                    continue;
                }
                updatedList.add(line);
            }
            Files.write(file, updatedList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void runApp() throws InterruptedException {
        System.out.println("Command here: ");
        int userOption = Integer.valueOf(sc.nextLine());
        if (userOption == 1) {
            read();
            runApp();
        } else if (userOption == 2) {
            add();
            runApp();
        } else if (userOption == 3) {
            search();
        } else if (userOption == 4){
            delete();
        } else if (userOption == 5){
            System.out.println("ADIOS");
        }
    }
}