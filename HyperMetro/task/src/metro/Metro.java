package metro;

import java.util.*;

public class Metro {
    private final List<NonLinearMetroLine> metroLinesNonLinear;

    public Metro(List<NonLinearMetroLine> lines) {
        this.metroLinesNonLinear = lines;
    }

    public void connectTransferLines() {
        for (NonLinearMetroLine currentLine : metroLinesNonLinear) {
            for (Station currentStation : currentLine.getStations()) {
                if (currentStation.transfer == null) {
                    continue;
                }
                for (Transfer transfer : currentStation.transfer) {
                    String lineToName = transfer.getLine();
                    String stationToName  = transfer.getStation();
                    NonLinearMetroLine lineToGo = findLineByName(lineToName);
                    if (lineToGo != null) {
                        Station stationToGo = lineToGo.findByName(stationToName);
                        stationToGo.addTransferStation(currentStation);
                        currentStation.addTransferStation(stationToGo);
                    }
                }
            }
        }
    }

    private NonLinearMetroLine findLineByName(String name) {
        return metroLinesNonLinear
                .stream()
                .filter(it -> Objects.equals(it.getName(), name))
                .findFirst()
                .orElse(null);
    }

    public void findRoute(String lineAName, String startStationName,
                          String lineBName, String endStationName, boolean isDijkstra) {
        Station startStation = findLineByName(lineAName).findByName(startStationName);
        Station endStation = findLineByName(lineBName).findByName(endStationName);
        PathFinder pathFinder = isDijkstra ? new DijkstraAlgorithm() : new BreadthFirstAlgorithm();
        pathFinder.createRoute(startStation, endStation, isDijkstra);
    }
}