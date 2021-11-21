package metro;

import java.util.*;

public class Metro {
    private final List<StationLinkedList> metroLines;
    public Metro(List<StationLinkedList> lines) {
        this.metroLines = lines;
    }

    public void addDepot() {
        for (StationLinkedList line : metroLines) {
            int newID = line.getLength() + 1;
            Station depot = new Station(newID, "depot");
            line.insert(depot, 0);
            Station endDepot = new Station(newID + 1, "depot");
            line.append(endDepot);
        }
    }

    public void addHeadStation(String lineName, String stationName) {
        StationLinkedList line = getLineByName(lineName);
        if (line == null) {
            return;
        }
        line.insert(new Station(line.getLength() + 1, stationName), 1);
    }

    public void appendStation(String lineName, String stationName) {
        StationLinkedList line = getLineByName(lineName);
        if (line == null) {
            return;
        }
        line.insert(new Station(line.getLength() + 1, stationName), line.getLength() - 1);
    }

    public void appendStation(String lineName, String stationName, String time) {
        StationLinkedList line = getLineByName(lineName);
        if (line == null) {
            return;
        }
        line.insert(new Station(line.getLength() + 1, stationName, Integer.parseInt(time)),
                line.getLength() - 1);
    }

    public void removeStation(String lineName, String stationName) {
        StationLinkedList line = getLineByName(lineName);
        if (line == null) {
            return;
        }
        line.remove(new Station(stationName));
    }

    public void outputLine(String lineName) {
        StationLinkedList line = getLineByName(lineName);
        if (line == null) {
            return;
        }
        System.out.println(line);
    }

    public void connectLines(String lineAName, String stationAName,
                             String lineBName, String stationBName) {
        StationLinkedList[] lines = {getLineByName(lineAName), getLineByName(lineBName)};
        if (lines[0] != null && lines[1] != null) {
            Station[] stations = {lines[0].find(stationAName), lines[1].find(stationBName)};
            if (stations[0] != null && stations[1] != null) {
                stations[0].setTransferLineName(lineBName);
                stations[1].setTransferLineName(lineAName);
                connectTwoStations(stations[0], stations[1]);
            }
        }
    }

    public void establishLinesConnections() {
        metroLines.forEach(line -> {
            Station currentStation;
            Station connectedStation;
            StationLinkedList connectedLine;
            int i = 0;
            while (i < line.getLength()) {
                currentStation = line.get(i++);
                String connectedLineName = currentStation.getTransferLineName();
                if (connectedLineName != null && !connectedLineName.isEmpty()) {
                    connectedLine = getLineByName(connectedLineName);
                    connectedStation = connectedLine.find(currentStation.getName());
                    connectTwoStations(currentStation, connectedStation);
                }
            }
        });
    }

    private void connectTwoStations(Station stationA, Station stationB) {
        stationB.setConnected(stationA);
        stationA.setConnected(stationB);
    }

    private StationLinkedList getLineByName(String lineName) {
        return metroLines.stream()
                .filter(line -> line.getName().equals(lineName))
                .findFirst()
                .orElse(null);
    }

    public void findRoute(String lineAName, String startStationName,
                          String lineBName, String endStationName, boolean isDijkstra) {
        Station startStation = getLineByName(lineAName).find(startStationName);
        Station endStation = getLineByName(lineBName).find(endStationName);
        PathFinder pathFinder = isDijkstra ? new DijkstraAlgorithm() : new BreadthFirstAlgorithm();
        pathFinder.createRoute(startStation, endStation, isDijkstra);
    }

    public void correctTypo() {
        StationLinkedList lineWithBuggyStation = getLineByName("Linka B");
        if (lineWithBuggyStation != null) {
            Station buggyStation = lineWithBuggyStation.find("Mustek");
            buggyStation.setTransferLineName("Linka A");
        }
    }
}
