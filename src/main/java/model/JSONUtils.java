package main.java.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class JSONUtils {

    private static int currentId = 0;

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

    /**
     * Reading movie from JSON file
     *
     * @param id movie identifier
     * @return movie as JSONObject
     * @throws NoMoviesException will be thrown when file does not contain movie with specific id
     */
    public static JSONObject getSingleMovieFromFile(int id) throws NoMoviesException {
        JSONArray array = JSONUtils.readJSONArray("\\src\\main\\resources\\json\\movieList.json");
        if (array.size() <= id) {
            throw new NoMoviesException();
        }
        JSONObject movie = (JSONObject) array.get(id);
        return movie;
    }

    /**
     * Retrieving products data from secondary JSON file
     *
     * @return product data as JSONObject
     */
    public static JSONObject getSecondaryProductData() {
        JSONArray array = JSONUtils.readJSONArray("\\src\\main\\resources\\json\\secondaryMovies.json");
        synchronized (JSONUtils.class) {
            JSONObject item = (JSONObject) array.get(currentId++);
            return item;
        }
    }

    /**
     * This method extracts actors from JSON describing Movie
     *
     * @param video JSONObject from which actors will be extracted
     * @return List with maximum 3 actors as String
     */
    public static ArrayList<String> readActorsFromJSON(JSONObject video) {
        ArrayList<String> result = new ArrayList<>();
        JSONArray actors = (JSONArray) video.get("cast");
        for (int i = 0; i < 3; i++) {
            try {
                result.add((String) actors.get(i));
            } catch (Exception e) {
                result.add("");
            }
        }
        return result;
    }
}
