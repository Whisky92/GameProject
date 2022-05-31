package jsondatas;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.tinylog.Logger;

public class GsonRepository<T> extends Repository<T> {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * A constructor for the GsonRepository class
     * @param elementType the type of element being used
     */
    public GsonRepository(Class<T> elementType) {
        super(elementType);
    }

    /**
     * A method for reading the data from the file given in parameter
     * @param file the file from which the data is being read
     * @throws IOException
     */
    public void loadFromFile(File file) throws IOException {
        Logger.info("Entering the loadFromFile method of GsonRepository class");
        try (var reader = new FileReader(file)) {
            var listType = TypeToken.getParameterized(List.class, elementType).getType();
            elements = GSON.fromJson(reader, listType);
        }

    }

    /**
     * A method for saving the data for the file given in parameter
     * @param file the file in which the data will be stored
     * @throws IOException
     */
    public void saveToFile(File file) throws IOException {
        Logger.info("Entering the saveToFile method of GsonRepository class");
        try (var writer = new FileWriter(file)) {
            GSON.toJson(elements, writer);
        }
    }

}
