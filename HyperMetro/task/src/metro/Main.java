package metro;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            String fileName = args[0];
            FileManager fileManager = new FileManager(fileName);
            List<NonLinearMetroLine> metroLines = fileManager.deserializeJSON();
            Metro metro = new Metro(metroLines);
            metro.connectTransferLines();
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
            String lineNameOrTime = parameters[2];
            String station2Name = parameters[3];
            switch (command) {
                case "/route":
                    metro.findRoute(lineName, stationName, lineNameOrTime, station2Name, false);
                    break;
                case "/fastest-route":
                    metro.findRoute(lineName, stationName, lineNameOrTime, station2Name, true);
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