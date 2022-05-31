package jsondatas;


import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonMaker {

    /**
     * Saves the winner of the game with name and the number of steps they made
     * @param jsonFile the file where the data will be saved
     * @param name String, name of the player
     * @param step the number of steps the player made
     * @throws IOException an error in case data can't be saved to the file for some reasons
     */
    public void saveToFile(String jsonFile, String name, int step) throws IOException {
        Logger.trace("Entering to saveToFile method of JsonBuilder class");
        var repository = new PlayersRep();
        var data = PlayerDatas.builder().name(name).steps(step).build();
        File dataFile = new File(jsonFile);
        if (dataFile.exists() == true)
        {
            Logger.info("The file exists");
            repository.loadFromFile(dataFile);
        }
        repository.add(data);
        repository.saveToFile(dataFile);
    }

    /**
     * A method for reading data from the file given in parameter
     * @param jsonFile the file from which the data is being read
     * {@return list of player name-color pairs}
     * @throws IOException an error in case data can't be read from the file for some reasons
     */

    public List<PlayerDatas> readFromFile(String jsonFile) throws IOException {
        Logger.trace("Entering the readFromFile method of JsonMaker class");
        var repository = new PlayersRep();
        File dataFile = new File(jsonFile);
        repository.loadFromFile(dataFile);

        return repository.findAll();
    }




}