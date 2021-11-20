package metro;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileManager {
    private final File inputFile;

    public FileManager(String filePath) {
        this.inputFile = new File(filePath);
    }

    public List<StationLinkedList> deserializeJSON() throws FileNotFoundException {
        Gson gson = new Gson();
        List<StationLinkedList> metroLines = new ArrayList<>();
        Map<String, Map<Integer, Station>> jsonDeserialized;

        try (Reader reader = Files.newBufferedReader(inputFile.toPath(), StandardCharsets.UTF_8)) {
            TypeToken<Map<String, Map<Integer, Station>>> modelOfData = new TypeToken<>() { };
            jsonDeserialized = gson.fromJson(reader, modelOfData.getType());
        } catch (IOException | JsonIOException e) {
            throw new FileNotFoundException("Error! Such a file doesn't exist!");
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Incorrect file");
        }

        jsonDeserialized.forEach((lineName, stationData) ->
                metroLines.add(addStationToLine(new StationLinkedList(lineName), stationData))
        );

        return metroLines;
    }

    private StationLinkedList addStationToLine(StationLinkedList line,
                                               Map<Integer, Station> stationData) {
        List<Integer> sortedKeys = stationData.keySet()
                .stream()
                .sorted()
                .collect(Collectors.toList());
        sortedKeys.forEach(stationNumber -> {
            Station station = stationData.get(stationNumber);
            station.setId(stationNumber);
            station.setTransferLineName();
            line.append(station);
        });
        return line;
    }
}