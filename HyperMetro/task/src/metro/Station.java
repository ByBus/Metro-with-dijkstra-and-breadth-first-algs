package metro;

import java.util.List;
import java.util.Objects;

public class Station implements Comparable<Station> {
    public static final int INFINITY = Integer.MAX_VALUE;
    private int id;
    private String name;
    private String transferLine;
    public List<Transfer> transfer;
    private Station connected = null;
    private Station nextStation = null;
    private Station previousStation = null;
    private int time = 100;
    private int calculatedTime = INFINITY;
    private boolean isVisited = false;

    public Station(int id, String name) {
        this.name = name;
        this.id = id;
    }
    public Station(int id, String name, int time) {
        this(id, name);
        this.time = time;
    }

    public Station(String name) {
        this(0, name);
    }

    public void setTransferLineName() {
        if (transfer != null && !transfer.isEmpty()) {
            setTransferLineName(transfer.get(0).getLine());
        }
    }

    public void setTransferLineName(String line) {
        this.transferLine = line;
    }

    public String getTransferLineName() {
        return transferLine == null ? "" : transferLine;
    }

    public boolean isHasConnected() {
        return !(transfer == null || transfer.isEmpty());
    }

    @Override
    public String toString() {
        String connected = this.connected != null ?
                String.format(" - %s (%s)", getConnected().getName(), getTransferLineName()) : "";
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

    public int getTimeToNextStation() {
        return time;
    }

    public int getTimeToPreviousStation() {
        if (previousStation == null) {
            return 100;
        }
        return previousStation.getTimeToNextStation();
    }

    public int getCalculatedTime() {
        return calculatedTime;
    }

    public void setCalculatedTime(int calculatedTime) {
        this.calculatedTime = calculatedTime;
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

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private static class Transfer {
        private String line;
        private String station;

        public String getLine() {
            return line;
        }
    }
}
