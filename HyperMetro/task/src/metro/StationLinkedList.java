package metro;

import java.util.Objects;

public class StationLinkedList {
    private String name = "";
    private int length = 0;
    private Station head = null;

    public StationLinkedList(String name) {
        this.setName(name);
    }

    public void append(Station newStation) {
        if (head == null) {
            head = newStation;
        } else {
            Station current = head;
            while (current.getNextStation() != null) {
                current = current.getNextStation();
            }
            current.setNextStation(newStation);
            newStation.setPreviousStation(current);
        }
        length++;
    }

    public void insert(Station newStation, int index) {
        int currentIndex = 0;
        Station current = head;
        while (currentIndex != index) {
            current = current.getNextStation();
            currentIndex++;
        }
        Station previous = current.getPreviousStation();
        Station next = current;
        newStation.setNextStation(next);
        newStation.setPreviousStation(previous);
        next.setPreviousStation(newStation);
        if (previous != null) {
            previous.setNextStation(newStation);
        }
        if (index == 0) {
            head = newStation;
        }
        length++;
    }

    public void remove (Station station) {
        if (head.equals(station)) {
            Station next = head.getNextStation();
            next.setPreviousStation(null);
            head = next;
            length--;
            return;
        }
        Station current = head;
        while (!current.equals(station) && current.getNextStation() != null) {
            current = current.getNextStation();
        }
        if (current.getNextStation() == null && current.equals(station)) {
            Station previous = current.getPreviousStation();
            previous.setNextStation(null);
            length--;
            return;
        }
        if (current.equals(station)) {
            Station next = current.getNextStation();
            Station previous = current.getPreviousStation();
            previous.setNextStation(next);
            assert next != null;
            next.setPreviousStation(previous);
            length--;
        }
    }

    public Station find(String name) {
        Station station = new Station(name);
        Station current = head;
        while (!current.equals(station) && current.getNextStation() != null) {
            current = current.getNextStation();
        }
        if (Objects.equals(current, station)) {
            return current;
        }
        return null;
    }

    public Station get(int i) {
        int iteration = 0;
        Station current = head;
        while (i != iteration && iteration < length) {
            current = current.getNextStation();
            iteration++;
        }
        return current;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        Station current = head;
        boolean isEndOfTheList = false;
        while (!isEndOfTheList) {
            String str = current + "\n";
            buffer.append(str);
            isEndOfTheList = current.getNextStation() == null;
            current = current.getNextStation();
        }
        return buffer.toString();
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

