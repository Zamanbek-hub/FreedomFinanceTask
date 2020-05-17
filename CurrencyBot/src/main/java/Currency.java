import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Currency {
    public static Model getCurrency(String cur)  throws IOException {
        Model model = new Model(); // create Model
        URL url = new URL("https://nationalbank.kz/rss/rates_all.xml?switch=russian"); // Url Response

        Scanner in = new Scanner((InputStream) url.getContent());

        String result = "", reply = "";
        while(in.hasNext()){
//            count += 1;
            result += in.nextLine();
        }

        System.out.println(result);

        // Convert XML to JSON
        JSONObject xmlJSONObj = XML.toJSONObject(result);
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
//        System.out.println(jsonPrettyPrintString);

        JSONArray getArray = xmlJSONObj.getJSONObject("rss").getJSONObject("channel").getJSONArray("item");
        jsonPrettyPrintString = getArray.toString(4);
        System.out.println(jsonPrettyPrintString);

        // Selection currency and Convert Json to Model
        for(int i = 0; i < getArray.length(); i++){
            JSONObject obj = getArray.getJSONObject(i);
            if(obj.getString("title").equals(cur)){
                model.setChange(obj.getDouble("change"));
                model.setDescription(obj.getDouble("description"));
                model.setTitle(obj.getString("title"));

            }
        }

        System.out.println(reply);

        return model;

    }

    // to get current Currency
    public static double getDescription(String cur) throws IOException {
        return getCurrency(cur).getDescription();
    }

}
