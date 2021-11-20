package metro;

import java.util.List;
import java.util.Objects;

public class Metro {
    private final List<StationLinkedList> metroLines;
    private final static int CONNECTED_STATION = 0;
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
                currentStation = line.getByIndex(i++);
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
                             String lineBName, String endStationName) {
        Station startStation = getLineByName(lineAName).find(startStationName);
        Station endStation = getLineByName(lineBName).find(endStationName);
        breadthFirstThroughStations(startStation);
        prettyPrintRoute(endStation);
    }

    private void prettyPrintRoute(Station endStation) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean isStartStation = false;
        Station current = endStation;
        current.setVisited(false);
        while (!isStartStation) {
            isStartStation = Objects.equals(current.getDist(), 0);
            stringBuilder.insert(0, current.getName() + "\n");
            Station[] neighbors = {current.getPreviousStation(), current.getNextStation(), current.getConnected()};
            for (Station neighbor : neighbors) {
                if (current.isNear(neighbor) && neighbor.isVisited()) {
                    if (neighbor.equals(current)) {
                        stringBuilder.insert(0, "Transition to line " + neighbor.getTransferLineName() + "\n");
                    }
                    current = neighbor;
                    current.setVisited(false);
                }
            }
        }
        System.out.println(stringBuilder);
    }

    private void breadthFirstThroughStations(Station station) {
        Queue<Station> queue = new Queue<>();
        station.setVisited(true);
        station.setDistance(0);
        queue.push(station);
        while (queue.peek() != null) {
            Station current = queue.pop();
            Station[] neighbors = {current.getConnected(), current.getPreviousStation(), current.getNextStation()};
            for (int i = 0; i < neighbors.length; i++) {
                Station neighbor = neighbors[i];
                if (neighbor != null && !neighbor.isVisited()) {
                    int distance = i == CONNECTED_STATION ? 0 : 1;
                    neighbor.setDistance(current.getDist() + distance);
                    neighbor.setVisited(true);
                    queue.push(neighbor);
                }
            }
        }
    }
}
