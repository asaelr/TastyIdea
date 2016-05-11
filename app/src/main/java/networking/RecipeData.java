package networking;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/**
 * Created by asael on 11/05/16.
 */
public class RecipeData extends GenericJson {
    @Key
    public String name;
    @Key
    public String[] directions;

    public static class Ing extends GenericJson {
        @Key
        public String name;
        @Key
        public String amount;
    }
    @Key
    public Ing[] ingredients;
    @Key
    public String category;
    @Key
    public int prepTimeMinutes;
    @Key
    public int rate;
    @Key
    public int difficulty;
    @Key
    public boolean vegeterian;
    @Key
    public boolean vegan;
    @Key
    public boolean kosher;
    @Key("uploader")
    public String username;
    @Key
    public String id;
}
