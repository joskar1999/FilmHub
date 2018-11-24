package main.java.model;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Utils {

    public static JSONArray readJSONArray(String path) {
        JSONParser parser = new JSONParser();
        JSONArray array = new JSONArray();
        try {
            String filePath = new File("").getAbsolutePath();
            filePath = filePath + path;
            array = (JSONArray) parser.parse(new FileReader(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return array;
    }
}
