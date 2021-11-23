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

public class FileManager {
    private final File inputFile;

    public FileManager(String filePath) {
        this.inputFile = new File(filePath);
    }

    public List<NonLinearMetroLine> deserializeJSON() throws FileNotFoundException {
        Gson gson = new Gson();
        List<NonLinearMetroLine> metroLines = new ArrayList<>();
        Map<String, List<Station>> jsonDeserialized;

        try (Reader reader = Files.newBufferedReader(inputFile.toPath(), StandardCharsets.UTF_8)) {
            TypeToken<Map<String, List<Station>>> modelOfData = new TypeToken<>() { };
            jsonDeserialized = gson.fromJson(reader, modelOfData.getType());
        } catch (IOException | JsonIOException e) {
            throw new FileNotFoundException("Error! Such a file doesn't exist!");
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Incorrect file");
        }

        jsonDeserialized.forEach((lineName, stations) ->
                metroLines.add(addStationsToLine(new NonLinearMetroLine(lineName), stations))
        );

        return metroLines;
    }

    private NonLinearMetroLine addStationsToLine(NonLinearMetroLine metroLine,
                                                 List<Station> stations) {
        stations.forEach(station -> {
            station.setLineName(metroLine.getName());
            station.init();
        });
        metroLine.setStations(stations);
        metroLine.connectStations();
        return metroLine;
    }
}