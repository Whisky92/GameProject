package jsondatas;

import model.Table;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonMakerTest {

    @Test
    void testFileReadandWrite()
    {
        Table testTable = new Table();
        int s = testTable.getStepCounter();
        String n = "testPlayer";

        JsonMaker jsonMakerTest = new JsonMaker();
        try {
            jsonMakerTest.saveToFile("testF.json",n, s);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        List<PlayerDatas> players;
        try {
            players = jsonMakerTest.readFromFile("testF.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int fileStep = players.get(players.size()-1).steps;
        String fileName = players.get(players.size()-1).name;
        assertEquals(n, fileName);
        assertEquals(s, fileStep);




    }

}