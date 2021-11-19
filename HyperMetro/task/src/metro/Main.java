package metro;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            String fileName = args[0];
            FileManager fileManager = new FileManager(fileName);
            List<CustomLinkedList<Station>> metroLines = fileManager.deserializeJSON();
            Metro metro = new Metro(metroLines);
            metro.addDepot();
            menu(metro);
        } catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void menu(Metro metro) {
        Scanner sc = new Scanner(System.in);
        boolean isWorking = true;
        while (isWorking) {
            String input = sc.nextLine();
            if (!Validator.isInputCorrect(input)) {
                throw new IllegalArgumentException("Invalid command");
            }
            String command = Validator.getCommandFromInput(input);
            String[] parameters = Validator.splitParameters(input);
            String lineName = parameters[0];
            String stationName = parameters[1];
            switch (command) {
                case "/add-head":
                    metro.addHeadStation(lineName, stationName);
                    break;
                case "/append":
                    metro.appendStation(lineName, stationName);
                    break;
                case "/remove":
                    metro.removeStation(lineName, stationName);
                    break;
                case "/output":
                    metro.outputLine(lineName);
                    break;
                case "/exit":
                    isWorking = false;
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }
}
