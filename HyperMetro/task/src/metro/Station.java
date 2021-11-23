package metro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Station implements Comparable<Station> {
    public static final int INFINITY = Integer.MAX_VALUE;
    private int id;
    private String name;
    private String lineName;

    public List<Station> nextStations;
    public List<Station> previousStations;
    public List<Station> transferStations;

    private int time;
    private int calculatedTime;
    private boolean isVisited = false;

    //Json fields
    public List<String> prev;
    public List<String> next;
    public List<Transfer> transfer;

    public Station(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public void init() {
        calculatedTime = INFINITY;
        nextStations = new ArrayList<>();
        previousStations = new ArrayList<>();
        transferStations = new ArrayList<>();
    }

    public Station(String name) {
        this(0, name);
    }

    public void addNextStation(Station station) {
        if (!nextStations.contains(station)) {
            nextStations.add(station);
        }
    }

    public void addPreviousStation(Station station) {
        if (!previousStations.contains(station)) {
            previousStations.add(station);
        }
    }

    public void addTransferStation(Station station) {
        long foundSameStations = transferStations.stream()
                .filter(it -> Objects.equals(station.getName(), it.name)
                        && Objects.equals(station.lineName, it.lineName))
                .count();
        if (foundSameStations == 0) {
            transferStations.add(station);
        }
    }

    public boolean isPrevious(Station station) {
        return previousStations.contains(station);
    }

    public boolean isTransfer(Station station) {
        return transferStations.contains(station);
    }

    public void setLineName(String line) {
        this.lineName = line;
    }

    public String getLineName() {
        return lineName == null ? "" : lineName;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Station)) {
            return false;
        }

        Station other = (Station) obj;
        return Objects.equals(this.getName(), other.getName());
    }

    @Override
    public int compareTo(Station other) {
        int result = calculatedTime - other.calculatedTime;
        if (result == 0) {
            return isVisited ? -1 : 1;
        } else {
            return result;
        }
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public String getName() {
        return name;
    }

    public int getTimeToNextStation() {
        return time;
    }

    public int getCalculatedTime() {
        return calculatedTime;
    }

    public void setCalculatedTime(int calculatedTime) {
        this.calculatedTime = calculatedTime;
    }
}

class Transfer {
    private String line;
    private String station;

    public String getLine() {
        return line;
    }

    public String getStation() {
        return station;
    }
}