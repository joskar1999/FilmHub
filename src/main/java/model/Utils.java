package main.java.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Utils {

    /**
     * Reading JSON file which contains JSONArray as main entity
     *
     * @param path path to JSON file
     * @return JSONArray read from file
     */
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

    /**
     * Randomizing country
     *
     * @return String country
     */
    public static String randCountry() {
        Random random = new Random();
        int id = random.nextInt(12);
        JSONArray array = readJSONArray("\\src\\main\\resources\\json\\country.json");
        JSONObject object = (JSONObject) array.get(id);
        String country = (String) object.get("country");
        return country;
    }

    /**
     * Randomizing genre
     *
     * @return String genre
     */
    public static String randGenre() {
        Random random = new Random();
        int id = random.nextInt(6);
        JSONArray array = readJSONArray("\\src\\main\\resources\\json\\genre.json");
        JSONObject object = (JSONObject) array.get(id);
        String genre = (String) object.get("genre");
        return genre;
    }
}
