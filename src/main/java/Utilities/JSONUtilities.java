package Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;

/**
 * Created by RXC8414 on 3/2/2017.
 */
public class JSONUtilities {

    public void parseJSONIntoHashMap(String jsonBody) {
        //1. Convert jsonBody into JSONObject
        JSONObject jsonObject = new JSONObject(jsonBody);

        //2. Print content of JSONObject as this is a Map
        Set<String> keys = jsonObject.keySet();
        for (String key : keys) {
            System.out.println(key + ":" + jsonObject.get(key));
        }

        //3. Get specific data
        //System.out.println("\n *** Specific Data ***" + jsonObject.getString("metadata").toString());
        JSONArray astroData = jsonObject.getJSONArray("astroData");
        JSONObject innerAstroDataObjs = null;
        for(int i = 0; i < astroData.length(); i++){
            innerAstroDataObjs = astroData.getJSONObject(i);

            //4. Print all object from innerAstroDataObjs
            System.out.println("\n *** All objects inside Astro Data "+(i+1)+" ***");
            Set<String> innerAstroDataObjsKeys = innerAstroDataObjs.keySet();
            for (String key:innerAstroDataObjsKeys) {
                System.out.println(key + ":" + innerAstroDataObjs.get(key));
            }
        }

        System.out.println("\n *** Specific Data ***\n dateLocal: "+innerAstroDataObjs.getString("dateLocal"));




    }
}
