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
            depot.setLine(line.name);
            line.insert(depot, 0);
            Station endDepot = new Station(newID + 1, "depot");
            endDepot.setLine(line.name);
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

    private CustomLinkedList<Station> getLineByName(String lineName) {
        return metroLines.stream()
                .filter(line -> line.name.equals(lineName))
                .findFirst()
                .orElse(null);
    }
}
