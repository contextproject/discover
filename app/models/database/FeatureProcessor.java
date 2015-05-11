package models.database;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Iterator;


public class FeatureProcessor {

    public void parser() {
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(
                    "/Users/daan/Desktop/47788174.txt"));

            System.out.println("jsonObject.size() = " + jsonObject.size());

            for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext(); ) {
                String key = (String) iterator.next();
                System.out.println(jsonObject.get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}