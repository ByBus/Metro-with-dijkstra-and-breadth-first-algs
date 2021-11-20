package metro;

import java.util.List;

public class Metro {
    private final List<CustomLinkedList<Station>> metroLines;

    public Metro(List<CustomLinkedList<Station>> lines) {
        this.metroLines = lines;
    }

    public void addDepot() {
        for (CustomLinkedList<Station> line : metroLines) {
            int newID = line.getLength() + 1;
            Station depot = new Station(newID, "depot");
            line.insert(depot, 0);
            Station endDepot = new Station(newID + 1, "depot");
            line.append(endDepot);
        }
    }

    public void addHeadStation(String lineName, String stationName) {
        CustomLinkedList<Station> line = getLineByName(lineName);
        if (line == null) {
            return;
        }
        line.insert(new Station(line.getLength() + 1, stationName), 1);
    }

    public void appendStation(String lineName, String stationName) {
        CustomLinkedList<Station> line = getLineByName(lineName);
        if (line == null) {
            return;
        }
        line.insert(new Station(line.getLength() + 1, stationName), line.getLength() - 1);
    }

    public void removeStation(String lineName, String stationName) {
        CustomLinkedList<Station> line = getLineByName(lineName);
        if (line == null) {
            return;
        }
        line.remove(new Station(stationName));
    }

    public void outputLine(String lineName) {
        CustomLinkedList<Station> line = getLineByName(lineName);
        if (line == null) {
            return;
        }
        System.out.println(line);
    }

    public void connectLines(String lineAName, String stationAName,
                             String lineBName, String stationBName) {
        CustomLinkedList<Station>[] lines = new CustomLinkedList[]
                {getLineByName(lineAName), getLineByName(lineBName)};
        if (lines[0] != null && lines[1] != null) {
            Station[] stations = {lines[0].find(new Station(stationAName)), lines[1].find(new Station(stationBName))};
            if (stations[0] != null && stations[1] != null) {
                stations[0].setLine(lineBName);
                stations[1].setLine(lineAName);
                connectTwoStations(stations[0], stations[1]);
            }
        }
    }

    public void establishLinesConnections() {
        metroLines.forEach(line -> {
            Station currentStation;
            Station connectedStation;
            CustomLinkedList<Station> connectedLine;
            int i = 0;
            while (i < line.getLength()) {
                currentStation = line.getByIndex(i++);
                String connectedLineName = currentStation.getLine();
                if (connectedLineName != null && !connectedLineName.isEmpty()) {
                    connectedLine = getLineByName(connectedLineName);
                    connectedStation = connectedLine.find(currentStation);
                    connectTwoStations(currentStation, connectedStation);
                }
            }
        });
    }

    private void connectTwoStations(Station stationA, Station stationB) {
        stationB.setConnected(stationA);
        stationA.setConnected(stationB);
    }

    private CustomLinkedList<Station> getLineByName(String lineName) {
        return metroLines.stream()
                .filter(line -> line.name.equals(lineName))
                .findFirst()
                .orElse(null);
    }
}
