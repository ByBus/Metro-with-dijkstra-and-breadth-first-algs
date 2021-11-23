package metro;

import java.util.*;
import java.util.stream.Stream;

abstract class PathFinder {
    public void createRoute(Station startStation, Station endStation, boolean isAddTravelTime) {
        Queue<Station> queue = new Queue<>();
        startStation.setVisited(true);
        startStation.setCalculatedTime(0);
        queue.push(startStation);
        computeNodesWeights(queue);
        List<Station> route = backTrackRoute(startStation, endStation);
        printRoute(route, isAddTravelTime);
    }
    public abstract void computeNodesWeights(Queue<Station> queue);

    private List<Station> backTrackRoute(Station startStation, Station endStation) {
        List<Station> route = new ArrayList<>();
        boolean isRouteFinished = false;
        Station current = endStation;
        current.setVisited(false);
        while (!isRouteFinished) {
            isRouteFinished = Objects.equals(current.getCalculatedTime(), 0)
                    && Objects.equals(current.getLineName(), startStation.getLineName());
            route.add(0, current);
            Station[] neighbors = getNeighbors(current);
            current = getWithLeastTime(neighbors);
            current.setVisited(false);
        }
        return removeLoopsAmongConnectedStation(route);
    }

    protected List<Station> removeLoopsAmongConnectedStation(List<Station> route) {
        List<Station> resultList = new ArrayList<>();
        List<Station> buffer = new ArrayList<>();
        for (Station station : route) {
            if (!buffer.isEmpty() && !Objects.equals(buffer.get(0), station)) {
                resultList.add(buffer.get(0));
                if (buffer.size() > 1) {
                    resultList.add(buffer.get(buffer.size() - 1));
                }
                buffer.clear();
            }
            buffer.add(station);
        }
        resultList.addAll(buffer);
        return resultList;
    }

    private void printRoute(List<Station> route, boolean isAddTravelTime) {
        String previousLine = route.get(0).getLineName();
        for (Station station : route) {
            if (!Objects.equals(previousLine, station.getLineName())) {
                previousLine = station.getLineName();
                print("Transition to line " + previousLine);
            }
            print(station.getName());
        }
        if (isAddTravelTime) {
            print(String.format("Total: %d minutes in the way", route.get(route.size() - 1).getCalculatedTime()));
        }
    }

    private Station getWithLeastTime(Station[] stations) {
        return Arrays.stream(stations)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .stream()
                .findFirst()
                .orElse(null);
    }

    Station[] getNeighbors(Station current) {
        List<Station> transfers = current.transferStations;
        List<Station> prevs = current.previousStations;
        List<Station> nexts = current.nextStations;
        List<Station> joinedList = new ArrayList<>();
        Stream.of(transfers, nexts, prevs).forEach(joinedList::addAll);
        return joinedList.toArray(new Station[0]);
    }

    void print(String s) {
        System.out.println(s);
    }
}


class DijkstraAlgorithm extends PathFinder {
    @Override
    public void computeNodesWeights(Queue<Station> queue) {
        while (queue.peek() != null) {
            Station current = queue.priorityPop();
            Station[] neighbors = getNeighbors(current);
            for (Station neighbor : neighbors) {
                if (neighbor != null && !neighbor.isVisited()) {
                    int time = current.getTimeToNextStation();
                    if (current.isPrevious(neighbor)) {
                        time = neighbor.getTimeToNextStation();
                    }
                    if (current.isTransfer(neighbor)) {
                        time = 5;
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
    public void computeNodesWeights(Queue<Station> queue) {
        while (queue.peek() != null) {
            Station current = queue.pop();
            Station[] neighbors = getNeighbors(current);
            for (Station neighbor : neighbors) {
                if (neighbor != null && !neighbor.isVisited()) {
                    int time = current.isTransfer(neighbor) ? 0 : 1;
                    neighbor.setCalculatedTime(current.getCalculatedTime() + time);
                    neighbor.setVisited(true);
                    queue.push(neighbor);
                }
            }
        }
    }
}