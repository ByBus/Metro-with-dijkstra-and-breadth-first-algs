package metro;

import java.util.List;
import java.util.Objects;

public class Station {
    private int id;
    private String name;
    private String transferLine;
    public List<Transfer> transfer;
    private Station connected = null;
    private Station nextStation = null;
    private Station previousStation = null;
    private int distance = 0;
    private boolean isVisited = false;


    public Station(int id, String name) {
        this.id = id;
        this.setName(name);
    }

    public Station(String name) {
        this(0, name);
    }

    public void setTransferLineName() {
        if (transfer != null && !transfer.isEmpty()) {
            setTransferLineName(transfer.get(0).getLine());
        }
    }

    public boolean isNear(Station station) {
        if (station == null) {
            return false;
        }
        return  station.getDist() == distance - 1 || station.getDist() == distance;
    }

    public void setTransferLineName(String line) {
        this.transferLine = line;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransferLineName() {
        return transferLine == null ? "" : transferLine;
    }

    @Override
    public String toString() {
        String connected = this.connected != null ? String.format(" - %s (%s)", getConnected().getName(), getTransferLineName()) : "";
        return getName() + connected;
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

    public Station getConnected() {
        return connected;
    }

    public void setConnected(Station connected) {
        this.connected = connected;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public int getDist() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Station getNextStation() {
        return nextStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }

    public Station getPreviousStation() {
        return previousStation;
    }

    public void setPreviousStation(Station previousStation) {
        this.previousStation = previousStation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static class Transfer {
        private String line;
        private String station;

        public String getLine() {
            return line;
        }
    }
}
