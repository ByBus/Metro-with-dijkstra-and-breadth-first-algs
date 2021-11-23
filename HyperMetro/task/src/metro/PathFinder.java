package metro;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

abstract class PathFinder {
    public final static int CONNECTED_STATION = 0;
    public void createRoute(Station startStation, Station endStation, boolean isAddTravelTime) {
        Queue<Station> queue = new Queue<>();
        startStation.setVisited(true);
        startStation.setCalculatedTime(0);
        queue.push(startStation);
        runThrough(queue);
        prettyPrintRoute(endStation, isAddTravelTime);
    }
    public abstract void runThrough(Queue<Station> queue);

    private void prettyPrintRoute(Station endStation, boolean isAddTravelTime) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean isStartStation = false;
        Station current = endStation;
        current.setVisited(false);
        if (isAddTravelTime) {
            stringBuilder.append(String.format("Total: %d minutes in the way\n", endStation.getCalculatedTime()));
        }
        while (!isStartStation) {
            isStartStation = Objects.equals(current.getCalculatedTime(), 0);
            stringBuilder.insert(0, current.getName() + "\n");
            Station[] neighbors = {current.getPreviousStation(), current.getNextStation(), current.getConnected()};
            Station neighbor = getWithLeastTime(neighbors);
            if (neighbor.equals(current) && !isStartStation) {
                stringBuilder.insert(0, "Transition to line " + neighbor.getTransferLineName() + "\n");
            }
            current = neighbor;
            current.setVisited(false);
        }
        System.out.println(stringBuilder);
    }

    private Station getWithLeastTime(Station[] stations) {
        return Arrays.stream(stations)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .stream()
                .findFirst()
                .orElse(null);
    }

    Station[] getNeighbors(Station station) {
        return new Station[]{station.getConnected(), station.getPreviousStation(), station.getNextStation()};
    }
}

class DijkstraAlgorithm extends PathFinder {
    @Override
    public void runThrough(Queue<Station> queue) {
        while (queue.peek() != null) {
            Station current = queue.priorityPop();
            Station[] neighbors = getNeighbors(current);
            for (int i = 0; i < neighbors.length; i++) {
                Station neighbor = neighbors[i];
                if (neighbor != null && !neighbor.isVisited()) {
                    int time = i == CONNECTED_STATION ? 5 : current.getTimeToNextStation();
                    if (Objects.equals(neighbor, current.getPreviousStation())) {
                        time = current.getTimeToPreviousStation();
                    }
                    int newTime = time + current.getCalculatedTime();
                    if (newTime < neighbor.getCalculatedTime()) {
                        neighbor.setCalculatedTime(newTime);
                    }
                    neighbor.setVisited(true);
                    queue.push(neighbor);
                }
            }
        }
    }
}

class BreadthFirstAlgorithm extends PathFinder {
    @Override
    public void runThrough(Queue<Station> queue) {
        while (queue.peek() != null) {
            Station current = queue.pop();
            Station[] neighbors = getNeighbors(current);
            for (int i = 0; i < neighbors.length; i++) {
                Station neighbor = neighbors[i];
                if (neighbor != null && !neighbor.isVisited()) {
                    int time = i == CONNECTED_STATION ? 0 : 1;
                    neighbor.setCalculatedTime(current.getCalculatedTime() + time);
                    neighbor.setVisited(true);
                    queue.push(neighbor);
                }
            }
        }
    }
}
