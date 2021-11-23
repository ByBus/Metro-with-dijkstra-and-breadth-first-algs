package metro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NonLinearMetroLine {
    private String name;
    private List<Station> stations = new ArrayList<>();

    public NonLinearMetroLine(String name) {
        this.setName(name);
    }

    public Station findByName(String name) {
        return getStations().stream()
                .filter(station -> Objects.equals(station.getName(), name))
                .findFirst()
                .orElse(null);
    }

    public void connectStations() {
        for (Station current : getStations()) {
            List<String> nextStations = current.next;
            List<String> prevStations = current.prev;
            for (String nextName : nextStations) {
                Station nextStation = findByName(nextName);
                if (nextStation != null) {
                    nextStation.addPreviousStation(current);
                }
            }
            for (String prevName : prevStations) {
                Station prevStation = findByName(prevName);
                if (prevStation != null) {
                    prevStation.addNextStation(current);
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}