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

    public List<CustomLinkedList<Station>> deserializeJSON() throws FileNotFoundException {
        Gson gson = new Gson();
        List<CustomLinkedList<Station>> metroLines = new ArrayList<>();
        Map<String, Map<Integer, String>> jsonDeserialized;

        try (Reader reader = Files.newBufferedReader(inputFile.toPath(), StandardCharsets.UTF_8)) {
            TypeToken<Map<String, Map<Integer, String>>> modelOfData = new TypeToken<>() { };
            jsonDeserialized = gson.fromJson(reader, modelOfData.getType());
        } catch (IOException | JsonIOException e) {
            throw new FileNotFoundException("Error! Such a file doesn't exist!");
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Incorrect file");
        }

        jsonDeserialized.forEach((lineName, stationData) ->
                metroLines.add(addStationToLine(new CustomLinkedList<>(lineName), stationData))
        );
        return metroLines;
    }

    private CustomLinkedList<Station> addStationToLine(CustomLinkedList<Station> line,
                                                       Map<Integer, String> stationData) {
        List<Integer> sortedKeys = stationData.keySet()
                .stream()
                .sorted()
                .collect(Collectors.toList());
        sortedKeys.forEach(stationNumber -> {
            String stationName = stationData.get(stationNumber);
            line.append(new Station(stationNumber, stationName));
        });
        return line;
    }
}